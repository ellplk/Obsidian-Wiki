# Hooks 系统

> Claude Code 的生命周期钩子框架，允许用户在工具执行前后注入自定义逻辑

---

## 定义

Hooks 系统是 Claude Code 提供的可扩展点，允许用户通过配置 shell 脚本，在 AI 操作的关键节点自动触发自定义逻辑，实现自动化审计、二次确认、环境同步等需求。

## 事件节点（27 个）

主要分为 4 类：

| 类型 | 代表事件 | 用途 |
|------|---------|------|
| **工具前置** | `PreToolUse` | 工具执行前拦截、校验 |
| **工具后置** | `PostToolUse` | 工具执行后处理结果 |
| **会话级** | `SessionStart`、`SessionEnd` | 会话初始化/清理 |
| **停止钩子** | `Stop` | AI 准备停止时触发，可阻断（blocking）或放行 |

## 4 种 Hook 类型

1. **允许型**：观察、记录，不影响流程
2. **阻断型（blocking）**：可返回错误阻止 AI 继续停止（`stop_hook_blocking`）
3. **修改型**：可修改工具输入/输出
4. **环境型**：触发外部系统同步

## Stop Hook 的特殊机制

Stop Hook 是最强大也最危险的钩子——它可以阻止 AI 结束当前轮次，强制继续执行。Claude Code 的 `query()` 循环中有专门的 `transition.reason = 'stop_hook_blocking'` 来处理这种情况，并保留 `hasAttemptedReactiveCompact` 避免死循环。

## 配置方式

在 `.claude/settings.json` 的 `hooks` 字段中配置：

```json
{
  "hooks": {
    "PreToolUse": [
      { "matcher": "Bash", "hooks": [{ "type": "command", "command": "my-audit-script.sh" }] }
    ]
  }
}
```

## 实现位置

- 来源分析：`docs/18-Hooks系统.md`（Claude-Code-Source-Study）
- 源码位置：`src/hooks/` 目录

## 关联

- [[entities/Claude Code|Claude Code]] — 实现宿主
- [[concepts/Agent工具系统|Agent 工具系统]] — PreToolUse/PostToolUse 与工具执行流程联动
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study]] — 第 18 章详细分析
