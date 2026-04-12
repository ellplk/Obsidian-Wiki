# Ink

> 基于 React 的终端 UI 渲染框架

---

## 基本信息

- **类型**：开源 JavaScript 库
- **作用**：用 React 组件模型构建终端（CLI）用户界面
- **底层**：自定义 React Reconciler + Yoga 布局引擎

## 核心机制

| 组件 | 说明 |
|------|------|
| **自定义 Reconciler** | 将 React 虚拟 DOM 渲染为终端字符而非 HTML |
| **Yoga 布局** | Facebook 开源的跨平台 Flexbox 布局引擎，用于计算终端元素位置 |
| **双缓冲** | 避免终端闪烁：先在内存中构建新帧，再一次性刷新到屏幕 |
| **虚拟滚动** | 只渲染可见区域的内容，支持大量输出不卡顿 |
| **鼠标事件** | 支持终端内的鼠标点击、滚动 |
| **60fps 渲染** | 动画与实时更新流畅 |

## 在 Claude Code 中的应用

Claude Code 使用 Ink 作为整个终端 UI 层：
- 工具执行状态、进度显示
- 权限确认对话框
- 代码 diff 高亮展示
- REPL 输入框与历史记录

Claude Code 对 Ink 进行了深度定制（80+ 语义颜色 token、6 套主题、15 个设计系统组件），详见 `docs/21-Ink框架深度定制.md` 和 `docs/22-设计系统.md`。

## 关联

- [[entities/Claude Code|Claude Code]] — 主要使用方
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study]] — 第 21、22 章深度分析
