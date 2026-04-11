# 知识库操作日志

## 2026-04-12

### 初始化知识库
- 复制 iCloud Obsidian 文档到当前目录
- 建立 Karpathy LLM Knowledge Base 架构：raw/、wiki/、output/
- 迁移图片、PDF、xmind 等附件到 raw/assets/
- 创建 CLAUDE.md 作为 Agent 配置文件
- 创建 wiki/index.md 主索引

### 首次编译 (Ingest)
- 为 10+ 份原始资料创建 sources/ 摘要
- 提取 11 个 entities（人物、组织、工具）
- 提取 17 个核心 concepts（深度学习、算法、软件工程、学习方法）
- 创建 4 篇 synthesis 综合分析
- 建立 backlinks 连接各个页面

### 当前统计
- 原始资料 (raw/): 25+ markdown 文件，18 PDF 论文，5 xmind 思维导图，13 张图片
- wiki 页面: ~40 页
- 覆盖领域: 深度学习/计算机视觉、数据结构与算法、软件工程、学习工作流

---

## 2026-04-12（第二次编译）

### 论文 Ingest（PDF 精读）

阅读并整理 `raw/assets/` 中 12 篇深度学习论文 PDF，全部创建 `wiki/sources/` 摘要页面：

#### 图像分类
- **AlexNet** (Krizhevsky et al., NIPS 2012)：ReLU/Dropout/GPU，ILSVRC-2012 冠军 15.3% top-5
- **VGGNet** (Simonyan & Zisserman, ICLR 2015)：3×3 堆叠，16-19 层，ILSVRC-2014 亚军
- **ResNet** (He et al., CVPR 2016)：残差连接 y=F(x)+x，152 层，3.57% top-5，ILSVRC-2015 冠军
- **ViT** (Dosovitskiy et al., ICLR 2021)：16×16 patch token，纯 Transformer，需 JFT-300M

#### 目标检测
- **Faster R-CNN** (Ren et al., NIPS 2015)：RPN + 共享特征，~300 提议，5fps
- **YOLO** (Redmon et al., CVPR 2016)：S×S 网格回归，45fps，63.4% mAP

#### 图像分割
- **FCN** (Long et al., CVPR 2015)：全卷积+跳跃架构，62.7% mIU PASCAL VOC
- **U-Net** (Ronneberger et al., MICCAI 2015)：编码器-解码器+跳跃连接，生物医学分割

#### 生成模型
- **GAN** (Goodfellow et al., NIPS 2014)：min_G max_D V(D,G)，对抗训练原始框架
- **StyleGAN** (Karras et al., CVPR 2019)：映射网络 f:Z→W + AdaIN，FFHQ 数据集，FID 4.40
- **DDPM** (Ho et al., NeurIPS 2020)：前向加噪+逆向去噪马尔可夫链，T=1000，FID 3.17
- **LDM** (Rombach et al., CVPR 2022)：潜在空间扩散+交叉注意力条件，Stable Diffusion 基础

### 更新内容
- 新增 12 个 `wiki/sources/` 摘要文件
- 更新 `wiki/index.md`，增加"深度学习论文"分类区块

### 更新后统计
- wiki/sources/: 22 页（原 10 页 + 新增 12 篇论文摘要）
- 覆盖时间线：AlexNet(2012) → VGGNet(2015) → ResNet(2016) → YOLO(2016) → ViT(2021) → DDPM(2020) → StyleGAN(2019) → LDM(2022)
