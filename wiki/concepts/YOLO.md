---
type: concept
domain: 深度学习
---

# YOLO

## 定义

YOLO（You Only Look Once）是一种单阶段（One-Stage）[[concepts/目标检测|目标检测]]算法，由 Joseph Redmon 等人在 2016 年提出。

## 核心思想
- 将目标检测视为**单个回归问题**
- 将图像划分为 $S \times S$ 网格，每个网格直接预测边界框和类别概率
- 一次性从完整图像输出检测结果，速度极快

## 演进
- **YOLOv1-v3**：Redmon
- **YOLOv4** (2020)：Alexey Bochkovskiy
- **YOLOv5/v8**：Ultralytics（工业界最常用）

## 特点
- **速度优先**：适合实时应用（如视频监控、自动驾驶）
- **与 Faster R-CNN 对比**：YOLO 速度更快，Faster R-CNN 精度更高

## 相关链接
- [[concepts/目标检测|目标检测]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[sources/图像领域深度学习模型进阶之路摘要|图像领域深度学习模型进阶之路]]
