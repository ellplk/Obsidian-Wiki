---
type: synthesis
domain: 学习方法论
---

# Obsidian 学习工作流构建

## 概述

本知识库包含了一套基于 [[entities/Obsidian|Obsidian]] 的个人学习工作流，核心理念来自 [[sources/日常学习工作流摘要|日常学习工作流]]（CS 自学指南）：**多参考源、勤于提问、建立联系**。

## 底层逻辑：信息损失图

信息源的可靠性层级：
```
源代码 > 官方文档 > 英文书籍 > 英文博客 > 中文博客
```

策略：优先一手信息，同时参考多源，采百家之长。

## 信息处理流程

### 1. 收集
| 来源 | 工具 |
|------|------|
| 网页文章 | [[entities/简悦 SimpRead|简悦 SimpRead]] 剪藏为 Markdown |
| 跨端收藏 | Cubox |
| RSS 订阅 | RSSHub Radar + Feedly |
| PDF/电子书 | 直接用 Obsidian 阅读；非 PDF 用 calibre 转换 |

### 2. 处理
- **英文文本**：Quicker + 沙拉查词划词翻译
- **英文视频**：Language Reactor 导出字幕 → Obsidian 阅读
- **视频关联**：Media Extended 插件实现笔记到视频时间点的跳转
- **PDF 关联**：Annotator 插件实现笔记内跳转到 PDF 原文

### 3. 回顾
- **双链连接**：Obsidian 自带 `[[wikilink]]` 连接相关笔记
- **间隔重复**：Obsidian-Anki 插件将笔记片段导出为复习卡片

## 当前升级：LLM Knowledge Base

本知识库现已升级为 [[entities/Andrej Karpathy|Andrej Karpathy]] 提出的 LLM Knowledge Base 架构：
- `raw/`：存放原始资料
- `wiki/`：LLM 维护的结构化百科
- `output/`：生成的报告

Obsidian 作为前端工具，用于浏览双链、查看图谱、编辑 Markdown。

## 相关链接
- [[sources/日常学习工作流摘要|日常学习工作流]]
- [[entities/Obsidian|Obsidian]]
- [[entities/简悦 SimpRead|简悦 SimpRead]]
- [[concepts/双链笔记|双链笔记]]
- [[concepts/间隔重复|间隔重复]]
- [[concepts/信息损失图|信息损失图]]
