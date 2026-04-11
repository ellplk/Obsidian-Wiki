// 使用数组高效实现普通队列、双端队列、优先级队列、循环队列
public class QueueImplement {
    // 使用数组实现循环队列
    private int[] queueArray;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    public QueueImplement(int capacity) {
        this.capacity = capacity;
        queueArray = new int[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public boolean isFull() {
        return size == capacity;
    }
    public void enqueue(int value) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        queueArray[rear] = value;
        size++;
    }
    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        int value = queueArray[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queueArray[front];
    }
    // 数组实现优先级队列
    private int[] priorityQueueArray;
    private int priorityQueueSize;
    private int priorityQueueCapacity;
    public QueueImplement(int capacity) {
        this.priorityQueueCapacity = capacity;
        priorityQueueArray = new int[capacity];
        priorityQueueSize = 0;
    }
    public boolean isPriorityQueueEmpty() {
        return priorityQueueSize == 0;
    }
    public boolean isPriorityQueueFull() {
        return priorityQueueSize == priorityQueueCapacity;
    }
    public void enqueuePriority(int value) {
        if (isPriorityQueueFull()) {
            throw new IllegalStateException("Priority Queue is full");
        }
        int i;
        for (i = priorityQueueSize - 1; (i >= 0 && priorityQueueArray[i] > value); i--) {
            priorityQueueArray[i + 1] = priorityQueueArray[i];
        }
        priorityQueueArray[i + 1] = value;
        priorityQueueSize++;
    }
    public int dequeuePriority() {
        if (isPriorityQueueEmpty()) {
            throw new IllegalStateException("Priority Queue is empty");
        }
        return priorityQueueArray[--priorityQueueSize];
    }
    public int peekPriority() {
        if (isPriorityQueueEmpty()) {
            throw new IllegalStateException("Priority Queue is empty");
        }
        return priorityQueueArray[priorityQueueSize - 1];
    }
    // 双端队列
    private int[] dequeArray;
    private int dequeFront;
    private int dequeRear;
    private int dequeSize; 
    private int dequeCapacity;
    public QueueImplement(int capacity) {
        this.dequeCapacity = capacity;
        dequeArray = new int[capacity];
        dequeFront = 0;
        dequeRear = -1;
        dequeSize = 0;
    }
    public boolean isDequeEmpty() {
        return dequeSize == 0;
    }
    public boolean isDequeFull() {
        return dequeSize == dequeCapacity;
    }
    public void enqueueFront(int value) {
        if (isDequeFull()) {
            throw new IllegalStateException("Deque is full");
        }
        dequeFront = (dequeFront - 1 + dequeCapacity) % dequeCapacity;
        dequeArray[dequeFront] = value;
        dequeSize++;
    }
    public void enqueueRear(int value) {
        if (isDequeFull()) {
            throw new IllegalStateException("Deque is full");
        }
        dequeRear = (dequeRear + 1) % dequeCapacity;
        dequeArray[dequeRear] = value;
        dequeSize++;
    }
    public int dequeueFront() {
        if (isDequeEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        int value = dequeArray[dequeFront];
        dequeFront = (dequeFront + 1) % dequeCapacity;
        dequeSize--;
        return value;
    }
    public int dequeueRear() {
        if (isDequeEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        int value = dequeArray[dequeRear];
        dequeRear = (dequeRear - 1 + dequeCapacity) % dequeCapacity;
        dequeSize--;
        return value;
    }
    public int peekFront() {
        if (isDequeEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return dequeArray[dequeFront];
    }
    public int peekRear() {
        if (isDequeEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return dequeArray[dequeRear];
    }

}
