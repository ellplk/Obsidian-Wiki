# Agent 工具系统

> AI Agent 调用外部能力的核心机制，以 Claude Code 的工具系统为例

---

## 定义

工具（Tool）是 AI Agent 能执行的具体操作——读取文件、跑命令、搜索代码等。工具系统定义了工具的注册、权限、执行、结果序列化全链路。

## Tool 接口的核心维度

| 维度 | 字段 | 作用 |
|------|------|------|
| 身份标识 | `name`、`aliases`、`searchHint` | 工具识别与查找 |
| Schema | `inputSchema`（Zod）、`outputSchema` | 输入验证 |
| 执行 | `call()` | 实际操作 |
| 权限 | `checkPermissions`、`isReadOnly`、`isDestructive` | 安全控制 |
| 并发 | `isConcurrencySafe` | 是否可并行执行 |
| UI | `renderToolUseMessage` 等 | 终端显示 |

## 安全设计：Fail-Closed

- `isConcurrencySafe` 默认 `false` → 不确定就串行执行
- `isReadOnly` 默认 `false` → 不确定就假设会写入
- 新工具遗漏属性 → 不会造成安全漏洞

## 权限检查层次

```
1. deny rules       → 被明确禁止？→ 拒绝
2. ask rules        → 需要每次询问？→ 弹框
3. tool.checkPermissions() → 工具自定义逻辑
4. permission mode  → bypass / plan / default
5. allow rules      → 已预授权？→ 放行
6. 默认             → 弹出确认对话框
```

## 工具注册链路

```
getAllBaseTools()
    → getTools()（按 deny rules / REPL 模式过滤）
    → assembleToolPool()（合并 MCP 工具，按名排序）
```

工具列表按名排序的目的：保证每次顺序稳定，提高 prompt cache 命中率。

## 执行流程

```
tool_use 块 → 查找 → Zod 验证 → validateInput
→ 权限检查 → PreToolUse Hooks → call()
→ PostToolUse Hooks → 序列化 tool_result → 继续循环
```

## 大结果处理

输出超过 `maxResultSizeChars` → 写磁盘 → 返回路径给 Claude → Claude 用 Read 工具获取。

## 关联

- [[entities/Claude Code|Claude Code]] — 工具系统的宿主
- [[concepts/MCP协议|MCP 协议]] — 外部工具接入标准
- [[sources/Claude源码阅读摘要|Claude源码阅读笔记]] — 详细实现分析
