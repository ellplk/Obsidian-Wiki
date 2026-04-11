// 1. 对待删除操作，先进行标记，待数组满后再进行删除
// 2. 对于插入操作，现将对应位置元素移到数组末尾，之后进行插入
// 3. 对于查询操作，直接返回对应位置元素
public class NumsOptimize {
    private int[] nums;
    private boolean[] deleted;
    private int size;

    public NumsOptimize(int capacity) {
        nums = new int[capacity];
        deleted = new boolean[capacity];
        size = 0;
    }

    public void insert(int index, int newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        // 在进行插入时，如果数组已满，则进行删除操作
        if (size == nums.length) {
            compact();
        }
        if (size == nums.length) {
            throw new IllegalStateException("Array is full");
        }
        // 将插入位置的元素移到数组末尾
        nums[size] = nums[index];
        deleted[size] = deleted[index];
        // 插入新元素
        nums[index] = newValue;
        deleted[index] = false;
        size++;
    }

    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        deleted[index] = true;
    }

    public int query(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (deleted[index]) {
            throw new IllegalStateException("Element has been deleted");
        }
        return nums[index];
    }
    public void compact() {
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (!deleted[i]) {
                nums[j] = nums[i];
                deleted[j] = false;
                j++;
            }
        }
        size = j;
    }
    public int getSize() {
        return size;
    }
    public int getCapacity() {
        return nums.length;
    }
    public static void main(String[] args) {
        NumsOptimize numsOptimize = new NumsOptimize(5);
        numsOptimize.insert(1);
        numsOptimize.insert(2);
        numsOptimize.insert(3);
        numsOptimize.insert(4);
        numsOptimize.insert(5);
        System.out.println(numsOptimize.query(2)); // 3
        numsOptimize.delete(2);
        System.out.println(numsOptimize.getSize()); // 5
        numsOptimize.compact();
        System.out.println(numsOptimize.getSize()); // 4
    }
     
}