# Faster R-CNN 论文摘要

**原始文件**：`raw/assets/Faster R-CNN- Towards Real-Time Object Detection with Region Proposal Networks.pdf`
**论文标题**：Faster R-CNN: Towards Real-Time Object Detection with Region Proposal Networks
**作者**：Shaoqing Ren, Kaiming He, Ross Girshick, Jian Sun（微软研究院）
**发表**：NIPS 2015 / TPAMI 2017

---

## 核心贡献

提出**区域提议网络（RPN）**，将候选框生成与检测网络共享卷积特征，首次实现几乎"零代价"的区域提议，推理速度达 5fps，成为两阶段检测的里程碑。

## 背景：检测流水线演进

- **R-CNN**：选择性搜索 + 逐框 CNN（慢，~47s/张）
- **Fast R-CNN**：共享卷积特征 + RoI Pooling（~2s，但提议仍用 SS）
- **Faster R-CNN**：RPN + Fast R-CNN，提议生成融入网络（**5fps**）

## 区域提议网络（RPN）

在共享卷积特征图上滑动 n×n 窗口，每个位置预测 k 个锚框（anchor）：
- **锚框尺度**：3 种（128²、256²、512²）× 3 种宽高比（1:1、1:2、2:1）= 9 个锚
- 对每个锚输出：2 个分类分数（前景/背景）+ 4 个回归偏移
- 整幅图约 20000 个锚，经 NMS 后保留 ~300 个高质量提议

## 端到端训练策略

4 步交替训练（Alternating Training）：
1. 训练 RPN（ImageNet 预训练初始化）
2. 用 RPN 提议训练 Fast R-CNN（独立）
3. 固定卷积层，微调 RPN
4. 固定卷积层，微调 Fast R-CNN

最终两个网络**共享卷积特征**，提议代价几乎可忽略（10ms/张）。

## 实验结果

| 数据集 | mAP | 速度 |
|--------|-----|------|
| PASCAL VOC 2007 | 73.2% | 5fps |
| PASCAL VOC 2012 | 70.4% | — |
| COCO 2015 | 42.7% (mAP@.5:.95) | — |

## 关联页面

- [[../concepts/目标检测|目标检测]]
- [[../concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[YOLO论文摘要|YOLO]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
