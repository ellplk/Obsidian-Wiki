# Claude Code

> Anthropic 官方开发的 AI 编程助手 CLI 工具

---

## 基本信息

- **开发方**：[[Anthropic]]
- **类型**：CLI 工具（命令行界面）
- **语言**：TypeScript（运行时：Bun）
- **当前研究版本**：2.1.88（source map 还原）

## 核心理念

> 让 Claude 模型直接操作你的文件系统和终端，像一个真正的编程搭档一样工作。

## 技术架构要点

- **终端 UI**：React + Ink（用 React 组件渲染终端，而非 Web）
- **查询引擎**：`QueryEngine.ts` → `query()` 状态机循环（while(true) + State 对象）
- **工具系统**：30+ 内置工具（Bash、Read、Edit、Grep、Glob、Agent 等），支持 MCP 扩展
- **权限模型**：deny rules → ask rules → tool.checkPermissions() → allow rules → 弹框确认
- **Prompt Cache**：工具列表按名排序保证稳定，节省 ~80% 重复 input token

## 项目规模

| 指标 | 数值 |
|------|------|
| 文件数 | 1,900+ |
| 代码行数 | 512,000+ |
| 工具数 | 30+（内置） |
| 命令数 | 40+（slash 命令） |

## 关键文件

| 文件 | 大小 | 功能 |
|------|------|------|
| `src/main.tsx` | 785KB | CLI 入口 |
| `src/query.ts` | 67KB | 核心查询循环 |
| `src/QueryEngine.ts` | 45KB | 查询引擎封装 |
| `src/tools/BashTool/bashSecurity.ts` | 100KB | 命令安全分析 |
| `src/services/mcp/client.ts` | 116KB | MCP 客户端 |

## 相关资料

- [[sources/Claude源码阅读摘要|Claude源码阅读笔记]] — 架构与实现深度分析（source map 还原）
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study（25章）]] — 全栈技术深度分析
- [[concepts/MCP协议|MCP 协议]] — 工具扩展协议
- [[concepts/Agent工具系统|Agent 工具系统]] — 工具注册与执行机制
