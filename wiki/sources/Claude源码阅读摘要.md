# Claude源码阅读笔记摘要

> 原始文件：`raw/02_编码与技术/技术笔记/Claude源码阅读.md`
> 关联资料：`raw/02_编码与技术/代码实现/claude-code-sourcemap/`（v2.1.88 source map 还原版，非官方）

---

## 一、技术栈

| 层级 | 技术 | 说明 |
|------|------|------|
| 语言 | TypeScript | 全项目严格类型 |
| 运行时 | Bun | 替代 Node.js，更快启动 |
| 终端 UI | React + Ink | 用 React 组件渲染终端界面 |
| CLI 框架 | Commander.js | 命令行参数解析 |
| LLM API | Anthropic SDK | 调用 Claude 模型 |
| 扩展协议 | MCP | 标准化工具扩展协议 |

项目规模：1900+ 文件，512,000+ 代码行。

## 二、框架层次

```
main.tsx (入口)
  └─ REPL.tsx (接收输入 / slash 命令)
       └─ QueryEngine.ts (核心引擎)
            └─ query() 查询循环
                 ├─ 构建 System Prompt
                 ├─ 调用 Claude API（流式）
                 ├─ 解析 tool_use 块
                 ├─ 权限检查 → 工具执行
                 └─ 循环直到 stop_reason = 'end_turn'
```

## 三、工具系统

### Tool 接口核心字段
- **身份**：`name`、`aliases`、`searchHint`
- **Schema**：`inputSchema`（Zod）、`outputSchema`
- **执行**：`call(args, context, canUseTool, ...)`
- **权限**：`checkPermissions`、`isReadOnly`、`isDestructive`、`isConcurrencySafe`
- **UI**：`renderToolUseMessage`、`renderToolResultMessage`

### 设计哲学：Fail-Closed
- `isConcurrencySafe` 默认 `false` → 不确定就不并发
- `isReadOnly` 默认 `false` → 不确定就假设会写入
- 新工具即使遗漏属性，也不会破坏安全性

### 工具执行链路
`tool_use` → 查找工具 → Zod 验证 → `validateInput` → 权限检查（deny/ask/allow rules + 确认框）→ PreToolUse Hooks → `call()` → PostToolUse Hooks → 序列化为 `tool_result` → 追加消息继续循环

### 大结果处理
输出超过 `maxResultSizeChars` → 持久化到 `~/.claude/projects/{cwd}/tool-results/{uuid}.txt`，返回路径引导 Claude 用 Read 工具读取。

## 四、查询循环（query.ts）

### 状态机设计：while(true) + State 对象
放弃递归的原因：长对话 100+ 轮递归会内存泄漏；while 循环单一闭包，内存恒定。

**State 对象关键字段：**
- `messages`：完整会话历史
- `turnCount`：迭代轮数
- `transition`：上一次继续的原因（防止恢复死循环）
- `maxOutputTokensRecoveryCount`：输出截断恢复计数
- `hasAttemptedReactiveCompact`：是否已尝试紧急压缩

### 循环 4 个阶段
1. **预处理管线**：大结果持久化、snip/micro/context/auto compact
2. **API 调用**：`callModel()` 流式接收，`StreamingToolExecutor` 边收边执行（支持并行）
3. **工具执行 / 停止判断**：有 tool_use → 收集结果；无 tool_use → 判断 stop hooks、token budget
4. **继续**：state = next，continue 回到 Phase 0

### transition 防死循环机制
不同 continue 站点精确控制哪些计数器重置：
- 正常下一轮：重置所有恢复计数器
- max_output_tokens 恢复：保留 `hasAttemptedReactiveCompact`
- stop_hook_blocking：同样保留 `hasAttemptedReactiveCompact`（避免 compact → 还太长 → stop hook → compact 无限循环）

## 五、性能优化：Prompt Cache

第 2、3 次 API 调用时，前缀消息与第 1 次相同 → prompt cache 命中，节省 ~80% input token 费用。工具列表按名字排序让顺序稳定，也是为了提高 cache 命中率。

## 六、关联概念

- [[concepts/MCP协议|MCP 协议]] — 工具扩展标准协议
- [[entities/Anthropic|Anthropic]] — Claude Code 开发方
- [[entities/Claude Code|Claude Code]] — 本笔记描述的工具
- [[concepts/Agent工具系统|Agent 工具系统]] — 工具注册、执行、权限体系
