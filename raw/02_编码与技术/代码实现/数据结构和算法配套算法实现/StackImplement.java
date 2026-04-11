// 使用数组、链表实现栈
import java.util.EmptyStackException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
public class StackImplement {
    // 使用链表实现栈
    private ListNode top;

    public StackImplement() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(int value) {
        ListNode newNode = new ListNode(value);
        newNode.next = top;
        top = newNode;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        int value = top.val;
        top = top.next;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.val;
    }

    // 使用数组实现栈
    private int[] stackArray;
    private int topIndex;
    private int capacity;
    
    public StackImplement(int capacity) {
        this.capacity = capacity;
        stackArray = new int[capacity];
        topIndex = -1;
    }

    public boolean isEmpty() {
        return topIndex == -1;
    }

    public boolean isFull() {
        return topIndex == capacity - 1;
    }

    public void push(int value) {
        if (isFull()) {
            throw new StackOverflowError();
        }
        stackArray[++topIndex] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackArray[topIndex--];
    }

    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackArray[topIndex];
    }

}
