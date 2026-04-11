public class KMP {
    // KMP算法
    public static int[] getNext(String pattern) {
        int m = pattern.length();
        int[] next = new int[m];
        next[0] = -1; // 第一个字符的next值为-1
        int j = 0; // j指向模式串
        int k = -1; // k指向前缀

        while (j < m - 1) {
            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
                next[j] = k;
            } else {
                k = next[k]; // 回溯
            }
        }
        return next;
    }

    public static int kmpSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int[] next = getNext(pattern);
        int i = 0; // i指向文本串
        int j = 0; // j指向模式串

        while (i < n && j < m) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j]; // 回溯
            }
        }
        if (j == m) {
            return i - j; // 匹配成功，返回匹配位置
        } else {
            return -1; // 匹配失败
        }
    }
}
