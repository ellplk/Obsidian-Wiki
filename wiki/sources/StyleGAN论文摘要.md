# StyleGAN 论文摘要

**原始文件**：`raw/assets/A Style-Based Generator Architecture for Generative Adversarial Networks.pdf`
**论文标题**：A Style-Based Generator Architecture for Generative Adversarial Networks
**作者**：Tero Karras, Samuli Laine, Timo Aila（NVIDIA）
**发表**：CVPR 2019（arXiv 2018）

---

## 核心贡献

重新设计 GAN 的生成器架构：引入**映射网络**和**自适应实例归一化（AdaIN）**，实现对图像高层属性（姿态、脸型）与细节（发色、皮肤纹理）的分离控制，并引入 FFHQ 数据集。

## 架构创新

### 传统 GAN vs StyleGAN 生成器

| 对比项 | 传统生成器 | StyleGAN |
|--------|-----------|---------|
| 输入 | 潜码 z 直接送入 | 从固定常量出发 |
| 风格控制 | 间接，全局混合 | AdaIN 在每层注入 |
| 随机细节 | 无显式控制 | 每层独立高斯噪声 |

### 映射网络（Mapping Network f）

```
z ∈ Z → f (8层 MLP) → w ∈ W
```

- 将 z 映射到中间隐空间 W，解耦各属性因子
- W 不受 p(z) 约束，可自由学习更线性、更解耦的表示

### AdaIN（自适应实例归一化）

```
AdaIN(xᵢ, y) = yₛ,ᵢ · (xᵢ - μ(xᵢ))/σ(xᵢ) + yᵦ,ᵢ
```

- 每层卷积后对特征图做归一化，再用从 w 仿射变换出的 y 缩放和偏移
- **粗粒度层（4²-8²）**：控制姿态、脸型、发型等全局属性
- **中粒度层（16²-32²）**：控制发色、眼睛等面部特征
- **细粒度层（64²-1024²）**：控制肤色、微结构

### 随机性注入（Stochastic Variation）

每层卷积后加入每像素独立高斯噪声，控制雀斑、发丝等随机细节，与全局风格互不干扰。

## 混合正则化（Style Mixing）

训练时随机在合成网络中途切换两个 w 码，防止网络假设相邻层的风格强关联，增强风格局部化。

## 质量指标

| 配置 | FFHQ FID | CelebA-HQ FID |
|------|----------|--------------|
| 基础 ProGAN | 8.04 | 7.79 |
| + 映射网络 + AdaIN | 4.85 | 5.34 |
| + 噪声输入 | **4.40** | **5.06** |

总参数：26.2M（映射网络 f 8层 + 合成网络 g 18层）。

## FFHQ 数据集

- 70,000 张高质量人脸图像，1024² 分辨率
- 从 Flickr 爬取，涵盖年龄、种族、配件多样性
- 已开源：https://github.com/NVlabs/ffhq-dataset

## 感知路径长度（新指标）

PPL（Perceptual Path Length）：插值时图像变化的感知距离，W 空间比 Z 空间更短（更线性），表明 W 的解耦更好。

## 关联页面

- [[../concepts/GAN|GAN]]
- [[GAN原始论文摘要|GAN 原始论文]]
- [[DDPM论文摘要|DDPM]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
