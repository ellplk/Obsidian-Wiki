// 跳表实现

import java.util.Random;

class SkipList {
    private static final int MAX_LEVEL = 16; // 最大层数
    private Node head; // 头节点
    private int level; // 当前层数
    private Random random; // 随机数生成器

    // 节点类
    private static class Node {
        int value; // 节点值
        Node[] next; // 节点在各层指向下一节点的指针

        public Node(int value, int level) {
            this.value = value;
            this.next = new Node[level]; // 初始化指针数组
        }
    }

    // 构造函数
    public SkipList() {
        this.head = new Node(-1, MAX_LEVEL);
        this.level = 0;
        this.random = new Random();
    }

    // 随机生成层数
    private int randomLevel() {
        int lvl = 1;
        while(random.nextDouble() < 0.5 && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }

    // 查找节点
    public Node find(int value) {
        Node currNode = head;
        for (int i = level; i>=0; i--) {
            // 找到大于等于value的前一个节点
            while (currNode.next[i] != null && currNode.next[i].value < value) {
                currNode = currNode.next[i];
            }
        }
        if (currNode.next[0] != null && currNode.next[0].value == value) {
            return currNode.next[0]; // 找到节点
        }
        return null; // 未找到节点
    }

    // 插入节点
    public void insert(int value) {
        Node[] update = new Node[MAX_LEVEL]; // update数组记录每层查找到的前驱节点，用于后续链接新节点
        Node currNode = head;

        // 从最高层开始查找插入位置
        for (int i = level; i >= 0; i--) {
            while (currNode.next[i] != null && currNode.next[i].value < value) {
                currNode = currNode.next[i];
            }
            update[i] = currNode; // 记录前驱节点
        }

        // 如果值已存在，则不插入
        if (currNode.next[0] != null && currNode.next[0].value == value) {
            return; // 值已存在
        }

        // 随机生成新节点的层数
        int newLevel = randomLevel();
        // 如果新节点的层数大于当前层数，则更新头节点的层数,并初始化 update数组中的新增层
        if (newLevel > level) {
            for (int i = level + 1; i < newLevel; i++) {
                update[i] = head; // 更新头节点
            }
            level = newLevel - 1; // 更新当前层数,因为 level 是从 0 开始，所以是 newlevel - 1
        }

        Node newNode = new Node(value, newLevel);

        // 更新新节点的 next指针
        for (int i = 0; i < newLevel; i++) {
            if (update[i] != null) {
                newNode.next[i] = update[i].next[i]; // 新节点的 next 指向前驱节点的 next
                update[i].next[i] = newNode; // 前驱节点的 next 指向新节点
            }
        }
    }

    // 删除节点
    public void delete(int value) {
        Node[] update = new Node[MAX_LEVEL]; // update数组记录每层查找到的前驱节点
        Node currNode = head;

        // 从最高层开始查找删除位置
        for (int i = level; i >= 0; i--) {
            while (currNode.next[i] != null && currNode.next[i].value < value) {
                currNode = currNode.next[i];
            }
            update[i] = currNode; // 记录前驱节点
        }

        // 如果值不存在，则不删除
        if (currNode.next[0] == null || currNode.next[0].value != value) {
            return; // 值不存在
        }

        currNode = currNode.next[0]; // 找到要删除的节点
        // 更新 next 指针
        for (int i = 0; i <= level; i++) {
            // 如果当前节点的 next 指针不是要删除的节点，则跳过
            if (update[i].next[i] != currNode) {
                break;
            }
            update[i].next[i] = currNode.next[i]; // 前驱节点的 next 指向当前节点的 next
        }

        // 更新当前层数
        while (level > 0 && head.next[level] == null) {
            level--;
        }
    }
}