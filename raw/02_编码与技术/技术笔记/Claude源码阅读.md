# 一、代码框架

## Claude code是什么

Claude Code 是 Anthropic 官方开发的 **AI 编程助手 CLI 工具**。它的核心理念是：

> **让 Claude 模型直接操作你的文件系统和终端，像一个真正的编程搭档一样工作。**

## 技术栈概览

| 层级     | 技术                         | 说明                           |
| -------- | ---------------------------- | ------------------------------ |
| 语言     | TypeScript                   | 全项目使用 TS，严格类型        |
| 运行时   | Bun                          | 替代 Node.js，更快的启动和执行 |
| 终端 UI  | React + Ink                  | 用 React 组件渲染终端界面！    |
| CLI 框架 | Commander.js                 | 命令行参数解析                 |
| LLM API  | Anthropic SDK                | 调用 Claude 模型               |
| 扩展协议 | MCP (Model Context Protocol) | 标准化的工具扩展协议           |
| 构建     | Bun bundler                  | 带 feature flag 的条件编译     |

## 项目规模

文件数量: 1,900+

代码行数: 512,000+

核心源码: src/ 目录

| 文件                                  | 大小  | 功能                            |
| ------------------------------------- | ----- | ------------------------------- |
| src/main.tsx                          | 785KB | CLI 入口，Commander.js 命令定义 |
| src/Tool.ts                           | 28KB  | 工具接口定义                    |
| src/tools.ts                          | 16KB  | 工具注册表                      |
| src/query.ts                          | 67KB  | 核心查询循环                    |
| src/QueryEngine.ts                    | 45KB  | 查询引擎封装                    |
| src/context.ts                        | 6KB   | 上下文管理                      |
| src/tools/BashTool/bashSecurity.ts    | 100KB | 命令安全分析                    |
| src/tools/BashTool/bashPermissions.ts | 96KB  | 命令权限检查                    |
| src/tools/AgentTool/AgentTool.tsx     | ~50KB | Agent 工具实现                  |
| src/tools/AgentTool/runAgent.ts       | 34KB  | Agent 生命周期                  |
| src/services/mcp/client.ts            | 116KB | MCP 客户端                      |
| src/services/mcp/config.ts            | ~20KB | MCP 配置管理                    |

## 框架总览

```Plain
┌─────────────────────────────────────────────────────────────────┐
│ 用户终端 (Terminal) │
└──────────────────────────────┬──────────────────────────────────┘
 │
 ▼
┌─────────────────────────────────────────────────────────────────┐
│ main.tsx (入口) │
│ ├── Commander.js 解析 CLI 参数 │
│ ├── 初始化: auth, config, MCP, plugins, skills │
│ └── 启动 React/Ink 渲染 → App.tsx │
└──────────────────────────────┬──────────────────────────────────┘
 │
 ▼
┌─────────────────────────────────────────────────────────────────┐
│ REPL 循环 (components/REPL.tsx) │
│ ├── 接收用户输入 │
│ ├── 处理 /slash 命令 │
│ └── 调用 QueryEngine │
└──────────────────────────────┬──────────────────────────────────┘
 │
 ▼
┌─────────────────────────────────────────────────────────────────┐
│ QueryEngine.ts (核心引擎) │
│ ├── 构建 System Prompt (context.ts + prompts.ts) │
│ ├── 调用 query() 循环 │
│ │ ├── 发送消息到 Claude API │
│ │ ├── 流式接收响应 │
│ │ ├── 解析 tool_use 块 │
│ │ ├── 权限检查 (permissions.ts) │
│ │ ├── 执行工具 (toolExecution.ts) │
│ │ ├── 将 tool_result 追加到消息 │
│ │ └── 循环直到 stop_reason = 'end_turn' │
│ └── 返回最终结果 │
└─────────────────────────────────────────────────────────────────┘
```

## 核心数据流：一条消息的完整旅程

```Bash
Step 1: 用户输入
 └── REPL.tsx 接收输入文本

Step 2: 消息构建
 └── processUserInput() 将文本转为 Message 对象
 ├── 检查是否是 /slash 命令
 ├── 处理附件 (图片、文件等)
 └── 构建 UserMessage { role: 'user', content: '帮我读取 package.json' }

Step 3: QueryEngine.submitMessage()
 ├── 获取 System Prompt
 │ ├── 默认系统提示词 (constants/prompts.ts)
 │ ├── 用户上下文: CLAUDE.md 内容 + 当前日期
 │ └── 系统上下文: git status + branch 信息
 ├── 注册工具列表 (tools.ts → getAllBaseTools())
 └── 调用 query() 进入查询循环

Step 4: query() 查询循环
 ├── 4a. 调用 Claude API
 │ ├── normalizeMessagesForAPI() 格式化消息
 │ ├── prependUserContext() 注入用户上下文
 │ ├── appendSystemContext() 注入系统上下文
 │ └── 发送 HTTP 请求到 Anthropic API
 │
 ├── 4b. 流式接收响应
 │ ├── message_start → 初始化
 │ ├── content_block_start → 开始接收内容块
 │ │ ├── type: 'text' → 文本响应
 │ │ └── type: 'tool_use' → 工具调用请求！
 │ │ { name: 'Read', input: { file_path: 'package.json' } }
 │ ├── content_block_delta → 增量内容
 │ └── message_stop → 消息结束
 │
 ├── 4c. 工具执行 (当 stop_reason = 'tool_use')
 │ ├── 权限检查: hasPermissionsToUseTool()
 │ │ ├── 检查 deny rules → 是否被禁止？
 │ │ ├── 检查 allow rules → 是否已授权？
 │ │ ├── 检查 permission mode → bypass/plan/default?
 │ │ └── 如果需要 → 弹出权限确认对话框
 │ ├── 输入验证: tool.validateInput()
 │ ├── 执行工具: tool.call()
 │ │ └── FileReadTool.call({ file_path: 'package.json' })
 │ │ → 读取文件内容并返回
 │ └── 构建 tool_result 消息
 │
 └── 4d. 继续循环
 ├── 将 tool_result 追加到消息列表
 ├── 再次调用 Claude API (带上工具结果)
 ├── Claude 生成最终文本响应
 └── stop_reason = 'end_turn' → 循环结束

Step 5: 结果返回
 ├── 渲染 Claude 的文本响应到终端
 ├── 记录会话到 transcript
 └── 等待下一次用户输入
┌──────────────────────────────────────────────┐
│                QueryEngine                   │
│  持有跨轮状态：                              │
│  - mutableMessages   会话消息历史            │
│  - totalUsage        累计 token 用量         │
│  - readFileState     文件缓存                │
│  - abortController   中断控制                │
└──────────────────────────────────────────────┘
                       │
                       │ submitMessage(prompt)
                       ▼
┌──────────────────────────────────────────────┐
│ 1. 读取配置，初始化本轮状态                  │
│ - 清空 discoveredSkillNames                  │
│ - 设置 cwd                                   │
│ - 记录 startTime                             │
│ - 包装 canUseTool，顺手记录 permission deny  │
└──────────────────────────────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 2. 计算本轮运行环境                          │
│ - 选 model                                   │
│ - 选 thinkingConfig                          │
│ - 拼 systemPrompt                            │
│   = default/custom/memory/append prompt      │
└──────────────────────────────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 3. 构建 processUserInputContext              │
│ 给后面这些逻辑共用：                         │
│ - 当前消息                                   │
│ - appState / setAppState                     │
│ - abortController                            │
│ - readFileState                              │
│ - skills / memory 追踪器                     │
└──────────────────────────────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 4. 预处理输入                                │
│ processUserInput(prompt)                     │
│ 输出：                                       │
│ - messagesFromUserInput                      │
│ - shouldQuery                                │
│ - allowedTools                               │
│ - modelFromUserInput                         │
│ - resultText                                 │
└──────────────────────────────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 5. 把用户输入先写进会话和 transcript         │
│ - this.mutableMessages.push(...)             │
│ - recordTranscript(messages)                 │
│ 目的：即使中途被杀进程，也能 resume          │
└──────────────────────────────────────────────┘
                       ▼
              ┌─────────────────┐
              │ shouldQuery ?   │
              └─────────────────┘
                 │ yes                 │ no
                 ▼                     ▼
┌─────────────────────────────┐   ┌─────────────────────────────┐
│ 6A. 进入 query(...) 主循环  │   │ 6B. 不调模型，直接返回       │
│                             │   │ - 本地 slash command 输出    │
│ for await (message of query)│   │ - local command stdout/stderr│
│                             │   │ - 最后 yield success result  │
└─────────────────────────────┘   └─────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────┐
│ 7. 循环消费 query 产生的消息                 │
│ - assistant: 存历史，yield 给 SDK            │
│ - user:      存历史，yield 给 SDK            │
│ - progress:  记录进度                        │
│ - stream_event: 更新 usage / stop_reason     │
│ - attachment: 结构化输出、maxTurns 等        │
│ - system: compact_boundary / api_retry       │
│ - tool_use_summary: 工具摘要                 │
└──────────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────┐
│ 8. 循环中随时检查提前终止条件                │
│ - 超 maxTurns -> error_max_turns             │
│ - 超 maxBudgetUsd -> error_max_budget_usd    │
│ - 结构化输出重试过多 -> error_max_structured │
└──────────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────┐
│ 9. query 结束后收尾                          │
│ - 找最后一个 assistant/user 消息             │
│ - flush transcript                           │
│ - 判断 isResultSuccessful(...)               │
└──────────────────────────────────────────────┘
                 │
        ┌────────┴────────┐
        │                 │
        ▼                 ▼
┌──────────────────┐  ┌─────────────────────────┐
│ 失败              │  │ 成功                     │
│ error_during_... │  │ 提取最后文本 textResult  │
│ + diagnostic     │  │ yield success result     │
└──────────────────┘  └─────────────────────────┘
```

# 二、工具系统

## 工具接口定义

“工具”是AI能执行的具体操作-读取文件、跑命令、搜索代码等，工具接口定义了：工具叫什么名字、接受什么输入、怎么执行、需要什么权限、怎么在终端上显示结果。

```TypeScript
export type Tool<Input, Output, Progress> = {
  // ========== 身份标识 ==========
  // Every tool has a unique name, like 'Glob', 'Bash', 'Read'
  readonly name: string;
  aliases?: string[];             // Backward-compatible aliases after renaming
  searchHint?: string;            // Keywords for ToolSearch matching
 
  // ========== Schema 定义 ==========
  // Defines what input the tool accepts and what output it produces
  readonly inputSchema: Input;    // Zod schema for input validation
  outputSchema?: z.ZodType;       // Output schema (optional)
  readonly inputJSONSchema?: object; // JSON Schema format for MCP tools
 
  // ========== 核心执行 ==========
  // The main method — this is where the tool actually does its work
  call(args, context, canUseTool, parentMessage, onProgress?)
  : Promise<ToolResult<Output>>;
 
  // ========== 权限与安全 ==========
  // These methods control whether the tool is allowed to run
  validateInput?(input, context): Promise<ValidationResult>;
  checkPermissions(input, context): Promise<PermissionResult>;
  isReadOnly(input): boolean;           // Read-only? (affects concurrency)
  isDestructive?(input): boolean;       // Irreversible? (e.g. rm -rf)
  isEnabled(): boolean;                 // Is this tool currently active?
  isConcurrencySafe(input): boolean;    // Can run in parallel with others?
 
  // ========== Prompt 生成 ==========
  // Tells the AI model what this tool does and how to use it
  description(input, options): Promise<string>;
  prompt(options): Promise<string>;
 
  // ========== UI 渲染 ==========
  // How the tool's actions and results appear in the terminal
  renderToolUseMessage(input, options): React.ReactNode;
  renderToolResultMessage?(output, progress, options): React.ReactNode;
  renderToolUseProgressMessage?(progress, options): React.ReactNode;
  renderToolUseRejectedMessage?(input, options): React.ReactNode;
  renderToolUseErrorMessage?(result, options): React.ReactNode;
 
  // ========== 序列化 ==========
  // Converts results to API-compatible format
  mapToolResultToToolResultBlockParam(output, toolUseID): ToolResultBlockParam;
  toAutoClassifierInput(input): unknown;
 
  // ========== 辅助方法 ==========
  userFacingName(input): string;        // Display name for users
  getPath?(input): string;              // File path being operated on
  getToolUseSummary?(input): string;    // One-line summary for mobile UI
  getActivityDescription?(input): string; // Spinner text while running
  maxResultSizeChars: number;           // Max chars in result (prevents huge outputs)
};
```

## buildTool()工具工厂创建Tool

```JavaScript
// Tool.ts 中的默认值
const TOOL_DEFAULTS = {
  isEnabled: () => true, // 默认启用
  isConcurrencySafe: () => false, // 默认不安全（保守）
  isReadOnly: () => false, // 默认假设会写入
  isDestructive: () => false, // 默认非破坏性
  checkPermissions: (input) => // 默认允许（交给通用权限系统）
  Promise.resolve({ behavior: 'allow', updatedInput: input }),
  toAutoClassifierInput: () => '', // 默认跳过分类器
  userFacingName: () => '', // 默认空
};

export function buildTool(def) {
  return {
  ...TOOL_DEFAULTS,
  userFacingName: () => def.name, // 默认用工具名
  ...def, // 用户定义覆盖默认值
  };
}
```

**设计哲学：Fail-Closed（安全优先）**

- `isConcurrencySafe` 默认 `false` → 不确定就不并发
- `isReadOnly` 默认 `false` → 不确定就假设会写入
- 这确保了新工具即使忘记设置这些属性，也不会造成安全问题

## GlobTool解析

### 文件架构

```Plain
src/tools/GlobTool/
├── GlobTool.ts # 核心逻辑 (199行)
├── prompt.ts # 工具描述 (8行)
└── UI.tsx # 终端渲染组件
```

其中prompt.ts作为工具描述，在实际操作时可以先看prompt，再看schema输入输出策略，最后看call函数处理逻辑。

## 工具处理流程

注册链路是 `getAllBaseTools()` → `getTools()` → `assembleToolPool()`：先收集基础工具和条件工具，再按 deny rules、REPL 模式、`isEnabled()` 做过滤，最后和 MCP 工具合并。合并时还会按名字排序，并让内置工具优先，目的之一是让工具列表顺序稳定，从而提高 prompt cache 命中率。

## 工具执行周期

当 Claude 返回一个 `tool_use` 块时，执行流程如下：

```Python
Claude API 返回: { type: 'tool_use', name: 'Glob', input: { pattern: '**/*.ts' } }
 │
 ▼
1. 查找工具: findToolByName(tools, 'Glob') → GlobTool
 │
 ▼
2. 输入解析: tool.inputSchema.parse(input)
 │ └── Zod 验证输入格式
 │
 ▼
3. 输入验证: tool.validateInput(parsedInput, context)
 │ └── 检查路径是否存在、是否是目录等
 │
 ▼
4. 权限检查: hasPermissionsToUseTool(tool, input, context)
 │ ├── 4a. 检查 deny rules → 被禁止？
 │ ├── 4b. 检查 ask rules → 需要询问？
 │ ├── 4c. tool.checkPermissions() → 工具自定义检查
 │ ├── 4d. 检查 permission mode → bypass/plan/default?
 │ ├── 4e. 检查 allow rules → 已授权？
 │ └── 4f. 默认 → 弹出权限确认对话框
 │
 ▼
5. PreToolUse Hooks: 执行前置钩子
 │ └── 用户自定义的 hook 脚本
 │
 ▼
6. 执行工具: tool.call(input, context, canUseTool, parentMessage, onProgress)
 │ └── 实际执行 glob 搜索
 │
 ▼
7. PostToolUse Hooks: 执行后置钩子
 │
 ▼
8. 结果序列化: tool.mapToolResultToToolResultBlockParam(output, toolUseID)
 │ └── 转换为 API 格式的 tool_result
 │
 ▼
9. 追加到消息列表，继续查询循环
```

## ToolUseContext

每个工具的 `call()` 方法都会收到一个 `ToolUseContext`，它包含了工具执行所需的一切：

```TypeScript
type ToolUseContext = {
  // ========== 配置 ==========
  options: {
  commands: Command[]; // 可用的 slash 命令
  tools: Tools; // 可用的工具列表
  mainLoopModel: string; // 当前使用的模型
  mcpClients: MCPServerConnection[]; // MCP 连接
  isNonInteractiveSession: boolean; // 是否非交互模式
  agentDefinitions: AgentDefinitionsResult; // Agent 定义
  };
 
  // ========== 状态管理 ==========
  getAppState(): AppState; // 获取全局状态
  setAppState(f): void; // 更新全局状态
  abortController: AbortController; // 中断控制器
  messages: Message[]; // 当前消息历史
 
  // ========== 文件缓存 ==========
  readFileState: FileStateCache; // 文件读取缓存（LRU）
 
  // ========== UI 回调 ==========
  setToolJSX?: SetToolJSXFn; // 设置工具 UI
  setInProgressToolUseIDs: (f) => void; // 标记进行中的工具
  setResponseLength: (f) => void; // 更新响应长度
 
  // ========== Agent 相关 ==========
  agentId?: AgentId; // 子 Agent ID
  agentType?: string; // Agent 类型名
};
```

**为什么用** **`getAppState()`** **而不是直接传 state？** 因为状态可能在工具执行过程中被其他并发操作修改。 每次调用 `getAppState()` 都能获取最新状态。

## 注意事项

1. `contextModifier` 很重要，但有边界。它能让工具在执行后更新上下文，比如编辑文件后刷新文件缓存；但文档明确强调，它只对**非并发安全工具**生效，因为并发工具一起跑时无法保证 modifier 的应用顺序，这个限制是为了避免竞态。
2. 工具描述本身就是 prompt engineering。在工具描述里引导模型选对工具；在 Zod `describe` 里嵌指令；在结果文本里加截断引导；用 `searchHint` 帮 `ToolSearch` 找到正确工具；以及通过 `isSearchOrReadCommand` 控制 UI 折叠。
3. 大结果处理。当工具输出超过 `maxResultSizeChars` 时，结果会被持久化到磁盘。

```Plain
工具输出 (例如 200KB 的 grep 结果)
 │
 ▼
resultText.length > tool.maxResultSizeChars ?
 │
 ├── YES → 保存到 ~/.claude/projects/{cwd}/tool-results/{uuid}.txt
 │ 返回预览 + 文件路径给 Claude
 │ "[Full output saved to: /path/to/file]\n"
 │ "Use Read tool to view the full output if needed."
 │
 └── NO → 直接返回完整结果
```

# 三、查询循环与API交互

## Claude 问答本身是一个状态机

### while(true)+State对象

Claude code用while(true)无限循环（而不是递归调用）实现问答，State对象记录当前对话进行到哪了、之前出过什么错、尝试过哪些恢复手段，每次循环开始，代码从这个记事本读取状态；每次循环结束时，把新状态写回去。

```TypeScript
type State = {
  // ===== 第一组：核心对话状态 =====
  messages: Message[]           // The conversation history (all messages so far)
  toolUseContext: ToolUseContext // Shared context for tool execution (file cache, permissions, etc.)
  turnCount: number             // How many loop iterations have run

  // ===== 第二组：压缩与恢复追踪 =====
  // These fields track error recovery attempts to prevent infinite retry loops
  autoCompactTracking: AutoCompactTrackingState | undefined  // Tracks auto-compression state (undefined = not started)
  maxOutputTokensRecoveryCount: number    // How many times we've retried after output truncation
  hasAttemptedReactiveCompact: boolean    // Whether we've already tried emergency compression
  maxOutputTokensOverride: number | undefined  // Upgraded output limit (undefined = use default)
  pendingToolUseSummary: Promise<ToolUseSummaryMessage | null> | undefined  // Async summary being generated in background

  // ===== 第三组：流程控制 =====
  stopHookActive: boolean | undefined  // Whether stop hooks are currently running
  transition: Continue | undefined     // WHY the last iteration continued (undefined on first iteration)
  // The transition field is not just for debugging — it's a guard against recovery loops (see below)
}
```

**transition 字段 — 防止恢复死循环**

State 中最巧妙的是 `transition` 字段。它记录了"上一次迭代为什么选择了 continue"。这不只是调试用的，它还用于**防止恢复循环**——如果上次已经尝试过某种恢复手段但失败了，这次就不再重复尝试：

```Plain
// 如果上一次已经做了 collapse_drain_retry，这次还是 413，
// 就不再尝试 drain，而是 fall through 到 reactive compact
if (state.transition?.reason !== 'collapse_drain_retry') {
  const drained = contextCollapse.recoverFromOverflow(...)
  // ...
}
```

**所有可能的 transition 原因**：

| transition.reason          | 含义               | 触发条件                        |
| -------------------------- | ------------------ | ------------------------------- |
| next_turn                  | 正常的下一轮       | 工具执行完毕                    |
| max_output_tokens_recovery | 输出被截断恢复     | stop_reason = max_output_tokens |
| max_output_tokens_escalate | 升级输出限制       | 从 8k 升级到 64k                |
| reactive_compact_retry     | 响应式压缩后重试   | prompt_too_long 错误            |
| collapse_drain_retry       | 上下文折叠后重试   | prompt_too_long 错误            |
| stop_hook_blocking         | Stop Hook 阻止停止 | Hook 返回 blocking error        |
| token_budget_continuation  | Token 预算未用完   | 还有预算继续                    |

### Claude为啥不用递归

递归的问题：

1. 每次递归创建新的闭包 → 内存泄漏（长对话可能有 100+ 轮）
2. 递归深度受限于调用栈
3. 状态传递需要大量参数
4. 无法在中间状态做 GC

VS

while(true) + State 的优势：

1. 单一闭包，内存恒定
2. 无栈深度限制
3. 所有状态集中在 State 对象中
4. continue 站点可以精确控制哪些状态重置

每个 continue 站点（即循环中决定"继续下一轮"的地方）都精确控制哪些状态重置、哪些保留。这就像医生在不同情况下开不同的处方——正常复诊时清零所有临时记录，但如果上次治疗出了问题，就要保留那次的记录以避免重蹈覆辙：

```C++
// max_output_tokens 恢复：保留 hasAttemptedReactiveCompact
const next: State = {
  messages: [...messagesForQuery, ...assistantMessages, recoveryMessage],
  maxOutputTokensRecoveryCount: maxOutputTokensRecoveryCount + 1, // 递增
  hasAttemptedReactiveCompact, // ← 保留！不重置
  // ...
}

// 正常下一轮：重置恢复计数器
const next: State = {
  messages: [...messagesForQuery, ...assistantMessages, ...toolResults],
  maxOutputTokensRecoveryCount: 0, // ← 重置
  hasAttemptedReactiveCompact: false, // ← 重置
  // ...
}

// stop_hook_blocking：保留 hasAttemptedReactiveCompact
// 注释解释了为什么：
// "Preserve the reactive compact guard — if compact already ran and
// couldn't recover from prompt-too-long, retrying after a stop-hook
// blocking error will produce the same result. Resetting to false
// here caused an infinite loop: compact → still too long → error →
// stop hook blocking → compact → … burning thousands of API calls."
```

### 完整流程图

```Plain
┌─────────────────────────────────────────────────────────────────────┐
│ query() 主循环 │
├─────────────────────────────────────────────────────────────────────┤
│ │
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │ Phase 0: 预处理管线 (每次迭代) │ │
│ │ ├── applyToolResultBudget() → 大结果持久化到磁盘 │ │
│ │ ├── snipCompact() → 裁剪旧的工具结果 │ │
│ │ ├── microcompact() → 压缩中间工具调用 │ │
│ │ ├── contextCollapse() → 折叠已完成的子任务 │ │
│ │ └── autocompact() → 全量压缩（如果需要） │ │
│ └──────────────────────────────────────────────────────────────┘ │
│ │ │
│ ▼ │
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │ Phase 1: API 调用 │ │
│ │ ├── prependUserContext() → 注入 CLAUDE.md 等 │ │
│ │ ├── callModel() 流式返回 → 逐块接收响应 │ │
│ │ ├── StreamingToolExecutor → 边接收边执行工具 │ │
│ │ └── 错误处理 → fallback model / withhold │ │
│ └──────────────────────────────────────────────────────────────┘ │
│ │ │
│ ┌─────────┴─────────┐ │
│ │ │ │
│ 有 tool_use? 无 tool_use │
│ │ │ │
│ ▼ ▼ │
│ ┌─────────────────────┐ ┌──────────────────────────────────┐ │
│ │ Phase 2: 工具执行 │ │ Phase 3: 停止判断 │ │
│ │ ├── 收集剩余结果 │ │ ├── 413 恢复 (collapse/compact) │ │
│ │ ├── 附件注入 │ │ ├── max_output_tokens 恢复 │ │
│ │ ├── 记忆预取消费 │ │ ├── Stop Hooks 执行 │ │
│ │ ├── 技能发现消费 │ │ ├── Token Budget 检查 │ │
│ │ └── 刷新工具列表 │ │ └── return Terminal │ │
│ └────────┬────────────┘ └──────────────────────────────────┘ │
│ │ │
│ ▼ │
│ ┌─────────────────────┐ │
│ │ Phase 4: 继续 │ │
│ │ state = next │ ──── continue ────→ 回到 Phase 0 │
│ └─────────────────────┘ │
│ │
└─────────────────────────────────────────────────────────────────────┘
```

端到端请求追踪

```YAML
时间线                    发生了什么                                    数据变化
─────────────────────────────────────────────────────────────────────────────────────
T+0ms    用户按回车
         │
         ├── processUserInput()                                messages = [
         │   将用户输入包装为 Message                              { role: 'user',
         │   type: 'user', content: [{ type: 'text',               content: '帮我修复...' }
         │   text: '帮我修复 src/auth.ts 中的空指针 bug' }]       ]
         │
         ├── query() 入口
         │   ├── buildQueryConfig() → 快照不可变配置
         │   ├── startRelevantMemoryPrefetch() → 后台预取记忆
         │   └── 初始化 State 对象 (turnCount=0)
         │
         ▼ ═══ 进入 while(true) 循环 ═══

T+1ms    Phase 0: 预处理管线
         │
         ├── applyToolResultBudget()  → 无操作（第一轮没有工具结果）
         ├── snipCompact()            → 无操作（消息太少）
         ├── microcompact()           → 无操作
         ├── contextCollapse()        → 无操作
         └── autocompact()            → 无操作

T+2ms    Phase 1: 组装 API 请求
         │
         ├── prependUserContext()                                API payload = {
         │   注入 CLAUDE.md 内容到 system prompt                   system: [系统提示 + CLAUDE.md],
         │   注入 git status 等上下文                               tools: [Bash, Read, Edit, ...],
         │                                                         messages: [用户消息],
         ├── callModel()                                           model: 'claude-sonnet-4-...',
         │   发送 HTTP POST 到 Anthropic API                       max_tokens: 8192
         │   开始接收 SSE 流                                     }
         │
         ▼

T+500ms  Phase 1: 流式接收（持续 3-8 秒）
         │
         │  ┌─ SSE chunk 1: thinking block (如果启用 extended thinking)
         │  ├─ SSE chunk 2: text "让我先看看这个文件..."
         │  ├─ SSE chunk 3: tool_use 开始 { name: 'Read', id: 'tu_001' }
         │  │   └── StreamingToolExecutor.addTool('Read', 'tu_001')
         │  │       canExecuteTool(true) → 立即开始执行！
         │  │       Read('src/auth.ts') 在后台运行
         │  │
         │  ├─ SSE chunk 4: tool_use input 完成 { path: 'src/auth.ts' }
         │  ├─ SSE chunk 5: tool_use 开始 { name: 'Grep', id: 'tu_002' }
         │  │   └── StreamingToolExecutor.addTool('Grep', 'tu_002')
         │  │       canExecuteTool(true) → Read 是 ConcurrencySafe，并行！
         │  │       Grep('null.*pointer', 'src/') 在后台运行
         │  │
         │  └─ SSE chunk N: message_stop, stop_reason: 'tool_use'
         │
         ▼

T+4000ms Phase 2: 收集工具结果
         │
         ├── getRemainingResults()                               messages = [
         │   Read 已完成 → yield tool_result                       用户消息,
         │   Grep 已完成 → yield tool_result                       assistant(text + 2个tool_use),
         │                                                         user(2个tool_result)
         ├── 消费 memoryPrefetch（如果已完成）                   ]
         ├── 消费 skillDiscovery（如果有）
         └── needsFollowUp = true（有 tool_use）

         Phase 4: 继续
         state = { messages: [..., tool_results], turnCount: 1,
                   transition: { reason: 'next_turn' } }
         continue → 回到 Phase 0

         ▼ ═══ 第二次迭代 ═══

T+4001ms Phase 0: 预处理管线
         ├── snipCompact() → 可能裁剪第一轮的 Read 结果（如果文件很大）
         └── 其他 → 无操作

T+4002ms Phase 1: 第二次 API 调用
         │                                                       API payload = {
         │  messages 现在包含完整的对话历史                         messages: [
         │  前缀与上次相同 → prompt cache 命中！                      用户消息,        ← 缓存命中
         │  只需要为新增的 tool_result 付费                           assistant(...),   ← 缓存命中
         │                                                           user(tool_results) ← 新增
         │  Claude 看到文件内容，生成修复方案                       ]
         │  返回 tool_use: Edit('src/auth.ts', ...)               }
         │
         ▼

T+8000ms Phase 2: 执行 Edit
         │
         ├── Edit 不是 ConcurrencySafe → 独占执行
         ├── 权限检查 → 弹出确认对话框（如果需要）
         ├── 用户确认 → 执行编辑
         └── tool_result: { success: true }

         Phase 4: 继续 → 第三次迭代

         ▼ ═══ 第三次迭代 ═══

T+9000ms Phase 1: 第三次 API 调用
         │
         │  Claude 看到 Edit 成功
         │  返回 text: "已修复空指针 bug，添加了 null check..."
         │  stop_reason: 'end_turn'（没有 tool_use）
         │
         ▼

T+12000ms Phase 3: 停止判断
          │
          ├── 没有 413 错误 → 跳过
          ├── stop_reason != max_output_tokens → 跳过
          ├── handleStopHooks()
          │   ├── 执行用户定义的 stop hooks（如 lint 检查）
          │   ├── hooks 通过 → 继续停止流程
          │   └── 后台: extractMemories(), promptSuggestion()
          ├── checkTokenBudget() → budget 为 null → stop
          └── return { reason: 'completed' }

          ═══ query() 返回 ═══

总计: 3 次 API 调用，~12 秒，消耗 ~5000 input tokens + ~2000 output tokens
其中 prompt cache 节省了第 2、3 次调用的 ~80% input token 费用
```