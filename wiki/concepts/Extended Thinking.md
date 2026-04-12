# Extended Thinking（扩展推理）

> Claude 模型的深度推理模式，允许模型在回答前进行显式的内部推理过程

---

## 定义

Extended Thinking 是 Anthropic 为 Claude 模型提供的一种推理增强机制。模型在生成最终回答前，会先输出一段"思考块"（thinking block），对问题进行分析、推导、自我反思，类似人类的草稿纸。

## 三种模式（Claude Code 中的实现）

| 模式 | 配置 | 适用场景 |
|------|------|---------|
| **关闭** | `thinkingConfig: { type: 'disabled' }` | 普通快速问答 |
| **标准** | `thinkingConfig: { type: 'enabled', budgetTokens: N }` | 复杂任务，给定 token 预算 |
| **ultrathink** | 关键词触发，`budgetTokens` 大幅提升 | 极复杂推理，用户主动触发 |

## ThinkingConfig 参数

```typescript
type ThinkingConfig = {
  type: 'disabled' | 'enabled';
  budgetTokens?: number;  // 思考可用的 token 上限
}
```

`budgetTokens` 控制模型思考的"深度"——越大允许越长的推理链，但也消耗更多 token。

## Advisor 模式

Claude Code 内部还实现了 **Advisor** 角色：在正式执行前，先用 Extended Thinking 做一轮策略咨询，再切换到普通模式执行。这是"先想清楚再动手"的工程化体现。

## 实现位置

- 来源分析：`docs/08-Thinking-与推理控制.md`（Claude-Code-Source-Study）
- 源码位置：`src/QueryEngine.ts` 中的 `thinkingConfig` 选择逻辑

## 关联

- [[entities/Claude Code|Claude Code]] — 实现宿主
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study]] — 第 8 章详细分析
- [[concepts/Agent工具系统|Agent 工具系统]] — Agent 也可配置 thinking 模式
