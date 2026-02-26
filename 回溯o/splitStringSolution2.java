package 回溯o;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


// 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。

// 返回符合要求的 最少分割次数 。

 

// 示例 1：

// 输入：s = "aab"
// 输出：1
// 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。

public class splitStringSolution2 {
    List<List<String>> result = new ArrayList<>();
    LinkedList<String> path = new LinkedList<String>();

    public List<List<String>> partition(String s) {
        trackPart(s, 0);
        return result;
    }

    void trackPart(String s, int startIndex) {
        // 终止条件
        if (startIndex >= s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        // 尝试每一种分割方式
        for (int i = startIndex; i < s.length(); i++) {
            String subString = s.substring(startIndex, i + 1);
            if (ishuiwen(subString)) {

                path.add(subString);
                trackPart(s, i + 1);
                path.removeLast();

            }

        }
    }

    boolean ishuiwen(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left <= right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }

        return true;
    }

    public int minCut(String s) {
        int n = s.length();
        // isPalin[i][j] = s[i..j] 是否为回文
        boolean[][] isPalin = new boolean[n][n];
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    isPalin[i][j] = (len <= 2) || isPalin[i + 1][j - 1];
                }
            }
        }
        // dp[i] = s[0..i] 的最少分割次数
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (isPalin[0][i]) {
                dp[i] = 0;
            } else {
                dp[i] = i; // 最多切 i 次
                for (int j = 1; j <= i; j++) {
                    if (isPalin[j][i]) {
                        dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                    }
                }
            }
        }
        return dp[n - 1];
    }
}
