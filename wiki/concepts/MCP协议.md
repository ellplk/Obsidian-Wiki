# MCP 协议（Model Context Protocol）

> 标准化的 AI 工具扩展协议，由 Anthropic 提出

---

## 定义

MCP（Model Context Protocol）是一种标准化协议，允许外部工具/服务以统一的方式接入 AI 模型。相当于 AI 世界的"USB 接口"——任何符合协议的工具都能被 AI 调用。

## 在 Claude Code 中的应用

- Claude Code 内置 MCP 客户端（`src/services/mcp/client.ts`，116KB）
- 用户可以配置外部 MCP Server，将自定义工具暴露给 Claude
- 内置工具（Bash、Read 等）与 MCP 工具合并注册到工具池
- 合并时内置工具优先，工具列表按名排序保证 prompt cache 稳定

## 工作流程

```
外部 MCP Server
    │ 暴露工具定义（JSON Schema）
    ▼
Claude Code MCP Client
    │ 发现工具 → 注册到工具池
    ▼
assembleToolPool()
    │ 内置工具 + MCP 工具合并
    ▼
Claude API（tools 参数）
```

## 关联

- [[entities/Claude Code|Claude Code]] — MCP 的主要使用方
- [[entities/Anthropic|Anthropic]] — MCP 协议制定方
- [[concepts/Agent工具系统|Agent 工具系统]] — MCP 工具的注册机制
