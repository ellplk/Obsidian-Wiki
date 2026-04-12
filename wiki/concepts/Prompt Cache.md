# Prompt Cache

> 通过复用前缀 Token 降低 API 调用成本的缓存机制

---

## 定义

Prompt Cache 是 Anthropic API 提供的功能：当多次 API 调用的消息前缀完全相同时，后续调用只需为新增部分付费，前缀部分命中缓存，成本大幅降低。

## 工作原理

```
第 1 次调用：[System Prompt] [工具列表] [用户消息]        → 全部计费
第 2 次调用：[System Prompt] [工具列表] [用户消息] [tool_result] → 前缀命中缓存，只为 tool_result 付费
第 3 次调用：[System Prompt] [工具列表] [用户消息] [tool_result] [assistant] [tool_result2] → 以此类推
```

实测：第 2、3 次 API 调用节省约 **80% input token 费用**。

## Claude Code 中的实现细节

### cache_control 标记

API 请求中通过 `cache_control: { type: "ephemeral" }` 标记缓存边界。Claude Code 在以下位置插入标记：
- System Prompt 末尾
- 工具列表末尾
- 较长的历史消息之后（Dynamic Boundary）

### 工具列表排序

工具列表**按名字排序**，保证每次构建的顺序稳定，从而保证前缀哈希一致，提高缓存命中率。

### Fork Agent 缓存共享

子 Agent（fork）创建时，会**继承父 Agent 的消息历史前缀**，使子 Agent 的第一次 API 调用也能命中缓存。

### Dynamic Boundary

并非固定在消息开头打标记，而是动态判断"哪段历史最稳定"来设置缓存边界，最大化命中概率。

## 缓存 TTL

Anthropic prompt cache 的 TTL 约为 **5 分钟**。Claude Code 的 `ScheduleWakeup` 等功能设计时需考虑这一限制（超过 5 分钟意味着缓存失效，下次需重新付全价）。

## 实现位置

- 来源分析：`docs/07-Prompt-Cache.md`（Claude-Code-Source-Study）
- 源码位置：`src/services/` 及 `src/query.ts` 中的 `prependUserContext()`

## 关联

- [[entities/Claude Code|Claude Code]] — 实现宿主
- [[concepts/上下文压缩|上下文压缩]] — 压缩后仍可命中缓存
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study]] — 第 7 章详细分析
