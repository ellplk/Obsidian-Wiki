# YOLO 论文摘要

**原始文件**：`raw/assets/You Only Look Once- Unified, Real-Time Object Detection.pdf`
**论文标题**：You Only Look Once: Unified, Real-Time Object Detection
**作者**：Joseph Redmon, Santosh Divvala, Ross Girshick, Ali Farhadi（华盛顿大学）
**发表**：CVPR 2016

---

## 核心贡献

将目标检测重新定义为**单一回归问题**，用一个网络一次前向传播同时预测所有目标的边界框和类别，实现实时检测（45fps）。

## 核心思想：统一检测框架

将输入图像划分为 **S×S 网格**（S=7）：
- 每个格子负责预测**中心点落在其中**的目标
- 每个格子预测 B 个边界框（B=2），每个框包含 (x, y, w, h, confidence)
- 每个格子还预测 C 个类别概率（C=20，PASCAL VOC）
- 最终输出张量：S×S×(B×5 + C) = 7×7×30

**Confidence = Pr(Object) × IoU**

## 网络架构

受 GoogLeNet 启发：
- 24 个卷积层 + 2 个全连接层
- 1×1 降维 + 3×3 卷积交替
- 预训练（ImageNet）后接检测层
- Fast YOLO 版：9 层卷积，155fps

## 训练细节

- **损失函数**：位置损失 + 置信度损失 + 分类损失的加权组合
- 坐标用平方根处理（减少大框影响）
- λ_coord=5（增强位置损失权重），λ_noobj=0.5（降低无目标置信度损失）

## 实验结果

| 方法 | mAP (VOC 2007) | 速度 |
|------|----------------|------|
| Faster R-CNN | 73.2% | 7fps |
| **YOLO** | **63.4%** | **45fps** |
| Fast YOLO | 52.7% | 155fps |

- mAP 略低于两阶段方法，但速度远超
- 背景误检率低于 Fast R-CNN（全局感受野优势）
- YOLO + Fast R-CNN 集成：75.0% mAP

## 局限与改进方向

- 每个格子最多检测一类目标，密集小目标效果差
- 后续：YOLOv2（锚框）、YOLOv3（多尺度）、YOLOv4-v8（持续改进）

## 关联页面

- [[../concepts/目标检测|目标检测]]
- [[Faster R-CNN论文摘要|Faster R-CNN]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
