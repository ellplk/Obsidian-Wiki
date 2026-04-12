# Claude Code Source Study 摘要

> 原始仓库：`raw/02_编码与技术/代码实现/Claude-Code-Source-Study/`
> 来源：https://github.com/luyao618/Claude-Code-Source-Study
> 性质：25 篇中文深度源码分析，覆盖 Claude Code 全栈技术，精确到源码文件路径与行号

---

## 项目概述

- **分析对象**：Claude Code（Anthropic 官方 AI 编程助手）约 1900 个源文件、1884 个 TypeScript 文件
- **特点**：逐文件逐函数拆解，每篇提炼 2-3 个可复用设计模式
- **配套资料**：[[sources/Claude源码阅读摘要|Claude源码阅读笔记]]（source map 还原版）

---

## 章节目录

### Part 1：全局架构

| 文件 | 主题 | 核心内容 |
|------|------|---------|
| `docs/01-项目全景.md` | 技术栈与启动链路 | Bun+TypeScript+Ink，模块依赖全景 |
| `docs/02-启动优化.md` | 毫秒级 CLI 启动 | 快速路径、侧效果前置、API 预连接、DCE |
| `docs/03-状态管理.md` | React 与非 React 桥接 | 35 行极简 Store、三层状态架构 |

### Part 2：AI 核心

| 文件 | 主题 | 核心内容 |
|------|------|---------|
| `docs/04-System-Prompt-工程.md` | 精密控制模型行为 | 分段构建、缓存边界、行为引导（~915 行代码） |
| `docs/05-对话循环.md` | query.ts 核心（1729 行） | AsyncGenerator 驱动、流式处理、工具执行、错误恢复 |
| `docs/06-上下文管理.md` | 无限对话的秘密 | Token 预算、多级压缩策略、文件状态缓存 |
| `docs/07-Prompt-Cache.md` | 跨模块缓存降成本 | cache_control 标记、Dynamic Boundary、Fork Agent 缓存共享 |
| `docs/08-Thinking-与推理控制.md` | Extended Thinking 三模式 | ThinkingConfig、Effort 级别、ultrathink、Advisor |

### Part 3：工具、命令与 Agent 系统

| 文件 | 主题 | 核心内容 |
|------|------|---------|
| `docs/09-工具系统设计.md` | buildTool() Builder 模式 | Tool 接口、40+ 内置工具注册体系 |
| `docs/10-BashTool-深度剖析.md` | 最复杂单工具（12,400 行） | 18 个源码文件、命令语义分类、多层安全防线 |
| `docs/11-命令系统.md` | 六源聚合斜杠命令 | 内建/Skill/Plugin/Bundled Skill/MCP Skill/Workflow |
| `docs/12-Agent-系统.md` | 单体到多智能体协作 | AgentDefinition、runAgent() 生命周期、Context 隔离 |
| `docs/13-内置Agent设计模式.md` | 6 个内置 Agent 解析 | Explore、Plan、Verification 等的 System Prompt 设计 |
| `docs/14-任务系统.md` | Agent 并发执行引擎 | Task 接口、7 种任务类型、生命周期管理 |

### Part 4：安全与工程

| 文件 | 主题 | 核心内容 |
|------|------|---------|
| `docs/15-MCP-协议实现.md` | 外部工具标准桥梁 | 8 种服务器配置、6 种传输层（stdio/SSE/HTTP/WebSocket/SDK/InProcess） |
| `docs/16-权限系统.md` | AI 安全最后防线 | 七种权限模式、细粒度规则、7 步决策管线、AI Classifier 辅助 |
| `docs/17-Settings-系统.md` | 多层配置合并 | 5+1 层优先级（Plugin→User→Project→Local→Flag→Policy）、MDM 集成 |
| `docs/18-Hooks系统.md` | 生命周期钩子框架 | 27 个事件节点（PreToolUse、PostToolUse、SessionStart、Stop 等）、4 种 Hook 类型 |
| `docs/19-Feature-Flag与编译期优化.md` | 同一份代码构建两产品 | Bun feature() 函数、process.env.USER_TYPE、GrowthBook A/B |
| `docs/20-API调用与错误恢复.md` | 面向不可靠网络的鲁棒设计 | withRetry 引擎、流式/非流式双模式、错误分类 |

### Part 5：终端 UI 与知识管理

| 文件 | 主题 | 核心内容 |
|------|------|---------|
| `docs/21-Ink框架深度定制.md` | 终端 React 渲染引擎 | 自定义 Reconciler、Yoga 布局、双缓冲、虚拟滚动、60fps |
| `docs/22-设计系统.md` | 终端 UI 组件化实践 | 80+ 语义颜色 token、6 套主题、15 个设计系统组件 |
| `docs/23-Memory系统.md` | AI 记忆多层架构 | CLAUDE.md 指令、Auto Memory、Session Memory、Agent Memory、Relevant Memories |

### 附录

| 文件 | 主题 |
|------|------|
| `docs/00-目录与阅读指引.md` | 导航与推荐阅读路线 |
| `docs/25-架构模式总结.md` | 7 个可迁移设计模式 |

---

## 推荐阅读路线

| 路线 | 章节 |
|------|------|
| 入门（7 篇） | 01 → 02 → 03 → 05 → 09 → 12 → 25 |
| AI 工程（9 篇） | 01 → 03 → 04 → 05 → 06 → 08 → 09 → 12 → 13 |
| 完整（25 篇） | 按序通读 |

---

## 7 个核心设计模式（来自 25-架构模式总结）

1. **编译期 DCE（Dead Code Elimination）** — Bun feature() 按产品类型裁剪代码
2. **极简 Store** — 35 行桥接 React 与命令式代码
3. **条件工具注册** — 按运行时环境动态组装工具池
4. **Builder 模式** — buildTool() 工厂 + Fail-Closed 默认值
5. **多层防御** — 权限系统 7 步决策管线
6. **配置合并** — 5+1 层优先级覆盖
7. **生命周期钩子** — 27 个事件节点的可扩展框架

---

## 关联

- [[entities/Claude Code|Claude Code]] — 本书分析的工具
- [[entities/Anthropic|Anthropic]] — 开发方
- [[sources/Claude源码阅读摘要|Claude源码阅读笔记]] — 配套的 source map 还原笔记
- [[concepts/Agent工具系统|Agent 工具系统]] — 工具系统相关概念
- [[concepts/MCP协议|MCP 协议]] — 第 15 章专题
