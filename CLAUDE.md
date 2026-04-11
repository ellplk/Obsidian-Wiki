# LLM Knowledge Base Agent Config

这是基于 Andrej Karpathy "LLM Knowledge Base" 架构的个人知识库。

## 核心架构

```
ObsidianWiki/
├── raw/                    # 原始资料（只读，人类放入，LLM读取）
│   └── assets/             # 图片、PDF、附件
├── wiki/                   # LLM维护的结构化wiki
│   ├── sources/            # 原始资料摘要
│   ├── entities/           # 人、组织、产品、工具
│   ├── concepts/           # 概念、框架、理论
│   ├── synthesis/          # 综合分析、主题比较
│   ├── index.md            # 主目录
│   └── log.md              # 操作记录
├── output/                 # 生成的报告和产物
└── CLAUDE.md               # 本文件
```

## 工作流

### 1. Ingest（编译）
当 raw/ 中有新资料时：
- 阅读 raw/ 中的新文档
- 在 wiki/sources/ 中创建摘要
- 提取 entities 到 wiki/entities/
- 提取 concepts 到 wiki/concepts/
- 更新 wiki/index.md
- 在 wiki/log.md 中记录操作

### 2. Query（查询）
回答用户问题时：
- 先读 wiki/index.md 了解知识库结构
- 再读相关 sources/entities/concepts/synthesis 页面
- 综合信息后回答
- 如答案有价值，可写入 wiki/synthesis/ 或 output/

### 3. Lint（健康检查）
定期检查：
- 孤立页面（无入链的页面）
- 矛盾或过时的信息
- 缺失的 backlinks
- 改进 index.md 的分类结构

## 命名规范

- 文件名使用中文，保持与原始资料一致的主题
- 使用 `[[wikilink]]` 在 Obsidian 中建立连接
- concepts/ 中的概念页面使用通用名称
- entities/ 中的实体页面使用全称
- sources/ 中的摘要引用原始文件路径

## 当前领域

本知识库主要覆盖：
- 深度学习与计算机视觉
- 数据结构与算法
- 软件工程与设计模式
- 个人学习工作流与工具链
