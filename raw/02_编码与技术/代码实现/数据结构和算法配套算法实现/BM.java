public class BM {
    // BM 字符串匹配算法
    // 计算坏字符规则
    public static int[] badChar(String pattern) {
        int[] badChar = new int[256]; // ASCII 字符集大小
        for (int i = 0; i < badChar.length; i++) {
            badChar[i] = -1; // 初始化为 -1
        }
        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i; // 更新坏字符表
        }
        return badChar;
    }

    // 计算好后缀规则
    public static int[] goodSuffix(String pattern) {
        int m = pattern.length();
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        
        // 初始化
        for (int i = 0; i < m; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        
        // 计算suffix和prefix数组
        for (int i = 0; i < m - 1; i++) {
            int j = i;
            int k = 0; // 公共后缀长度
            while (j >= 0 && pattern.charAt(j) == pattern.charAt(m - 1 - k)) {
                j--;
                k++;
                suffix[k] = j + 1;
            }
            if (j == -1) { // 如果j为-1，表示子串也是模式串的前缀
                prefix[k] = true;
            }
        }
        
        // 计算移动规则
        int[] gs = new int[m];
        for (int i = 0; i < m; i++) {
            gs[i] = m; // 默认移动整个模式串长度
        }
        
        // 情况1：模式串中有子串匹配好后缀
        for (int i = m - 1; i > 0; i--) {
            if (suffix[i] != -1) {
                gs[m - 1 - i] = m - 1 - suffix[i];
            }
        }
        
        // 情况2：模式串的前缀匹配好后缀的后缀
        for (int i = 0; i < m - 1; i++) {
            int len = 0;
            // 好后缀的后缀子串长度
            for (int j = i; j >= 0; j--) {
                if (prefix[j]) {
                    len = j;
                    break;
                }
            }
            if (len > 0) {
                gs[len - 1] = m - len;
            }
        }
        
        return gs;
    }
    
    // BM搜索主函数
    public static int search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        
        if (m > n) return -1;
        if (m == 0) return 0;
        
        int[] bc = badChar(pattern);
        int[] gs = goodSuffix(pattern);
        
        int i = 0; // text中的位置
        while (i <= n - m) {
            int j = m - 1; // pattern从右向左匹配
            
            // 从右向左比较
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                j--;
            }
            
            if (j < 0) {
                return i; // 找到匹配
            }
            
            // 计算坏字符规则下移动距离
            int bcShift = j - bc[text.charAt(i + j)];
            
            // 计算好后缀规则下移动距离
            int gsShift = 0;
            if (j < m - 1) {
                gsShift = gs[j];
            }
            
            // 取较大的移动距离
            i += Math.max(bcShift, gsShift);
        }
        
        return -1; // 未找到匹配
    }
    
    public static void main(String[] args) {
        String text = "HERE IS A SIMPLE EXAMPLE";
        String pattern = "EXAMPLE";
        
        int pos = search(text, pattern);
        System.out.println("Pattern found at position: " + pos);
    }
}
