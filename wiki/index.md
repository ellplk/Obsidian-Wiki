# 知识库主索引

> 基于 Karpathy LLM Knowledge Base 架构的个人知识库。最后更新：2026-04-12（第二次编译：12 篇 PDF 论文）

---

## 📚 来源摘要 (Sources)

### 深度学习论文（PDF）

#### 图像分类
- [[sources/AlexNet论文摘要|AlexNet (2012)]] — 深度学习复兴起点，ILSVRC-2012 冠军
- [[sources/VGGNet论文摘要|VGGNet (2015)]] — 3×3 小卷积核堆叠，16-19 层
- [[sources/ResNet论文摘要|ResNet (2016)]] — 残差连接，152 层，ILSVRC-2015 冠军
- [[sources/ViT论文摘要|ViT (2021)]] — 图像 Patch 作 Token，纯 Transformer 分类

#### 目标检测
- [[sources/Faster R-CNN论文摘要|Faster R-CNN (2016)]] — RPN + 共享卷积特征，5fps
- [[sources/YOLO论文摘要|YOLO (2016)]] — 检测即回归，45fps 实时检测

#### 图像分割
- [[sources/FCN论文摘要|FCN (2015)]] — 全卷积网络，语义分割先驱
- [[sources/U-Net论文摘要|U-Net (2015)]] — 编码器-解码器+跳跃连接，生物医学分割

#### 生成模型
- [[sources/GAN原始论文摘要|GAN (2014)]] — 对抗生成网络原始论文，Goodfellow
- [[sources/StyleGAN论文摘要|StyleGAN (2019)]] — 映射网络+AdaIN，NVIDIA，风格分离控制
- [[sources/DDPM论文摘要|DDPM (2020)]] — 去噪扩散概率模型，FID 3.17
- [[sources/LDM论文摘要|LDM (2022)]] — 潜在扩散模型，Stable Diffusion 基础

### 学习与思考
- [[sources/大模型底层原理学习路径摘要|大模型底层原理学习路径]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[sources/图像领域深度学习模型进阶之路摘要|图像领域深度学习模型进阶之路]]
- [[sources/动态规划学习笔记摘要|动态规划学习笔记]]
- [[sources/学习方法总结摘要|学习方法总结]]
- [[sources/日常学习工作流摘要|日常学习工作流]]
- [[sources/面向六个月后的AICode摘要|面向六个月后的AI Code]]

### 编码与技术
- [[sources/软件代码设计笔记摘要|软件代码设计笔记]]
- [[sources/单元测试笔记摘要|单元测试笔记]]
- [[sources/Claude源码阅读摘要|Claude Code 源码阅读]] — v2.1.88 架构、工具系统、查询循环深度分析
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study（25章）]] — 全栈技术深度分析，含设计模式提炼

### 资源与模板
- [[sources/学术论文阅读Prompt摘要|学术论文阅读Prompt]]

---

## 🏛️ 实体 (Entities)

### 人物
- [[entities/Andrej Karpathy|Andrej Karpathy]]
- [[entities/Geoffrey Hinton|Geoffrey Hinton]]
- [[entities/Yann LeCun|Yann LeCun]]
- [[entities/李飞飞|李飞飞]]

### 组织
- [[entities/OpenAI|OpenAI]]
- [[entities/华为|华为]]
- [[entities/Hugging Face|Hugging Face]]

### 工具与框架
- [[entities/PyTorch|PyTorch]]
- [[entities/Obsidian|Obsidian]]
- [[entities/pytest|pytest]]
- [[entities/简悦 SimpRead|简悦 SimpRead]]
- [[entities/Claude Code|Claude Code]]
- [[entities/Ink|Ink]] — React 终端 UI 渲染框架
- [[entities/Bun|Bun]] — 高性能 JS/TS 运行时

### AI 公司
- [[entities/Anthropic|Anthropic]]

---

## 💡 概念 (Concepts)

### 深度学习
- [[concepts/Transformer|Transformer]]
- [[concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[concepts/ResNet|ResNet]]
- [[concepts/YOLO|YOLO]]
- [[concepts/GAN|GAN]]
- [[concepts/Diffusion Model|Diffusion Model]]
- [[concepts/ViT|ViT]]
- [[concepts/目标检测|目标检测]]
- [[concepts/语义分割|语义分割]]

### 数据结构与算法
- [[concepts/动态规划|动态规划]]
- [[concepts/背包问题|背包问题]]
- [[concepts/最长公共子序列|最长公共子序列]]
- [[concepts/最长递增子序列|最长递增子序列]]

### 软件工程
- [[concepts/SOLID原则|SOLID原则]]
- [[concepts/设计模式|设计模式]]
- [[concepts/单元测试|单元测试]]
- [[concepts/重构|重构]]
- [[concepts/面向对象编程|面向对象编程]]

### AI Agent 与工具
- [[concepts/MCP协议|MCP 协议]] — 标准化 AI 工具扩展协议
- [[concepts/Agent工具系统|Agent 工具系统]] — 工具注册、权限、执行体系
- [[concepts/Extended Thinking|Extended Thinking]] — 模型显式推理模式（ultrathink）
- [[concepts/Hooks系统|Hooks 系统]] — 27 个生命周期事件钩子框架
- [[concepts/上下文压缩|上下文压缩]] — 多级压缩策略，支持无限长对话
- [[concepts/Prompt Cache|Prompt Cache]] — 前缀 Token 复用，节省 ~80% 成本

### 学习方法论
- [[concepts/双链笔记|双链笔记]]
- [[concepts/间隔重复|间隔重复]]
- [[concepts/信息损失图|信息损失图]]

---

## 🔬 综合 (Synthesis)

- [[synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
- [[synthesis/数据结构与算法学习体系|数据结构与算法学习体系]]
- [[synthesis/软件工程核心知识体系|软件工程核心知识体系]]
- [[synthesis/Obsidian学习工作流构建|Obsidian学习工作流构建]]

---

## 📁 原始资料

所有原始文档存放在 [[../raw/|raw/]] 目录中。

## 📝 操作日志

查看 [[log|log.md]] 了解知识库维护记录。
