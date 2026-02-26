package 回溯o;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。

// 返回 s 所有可能的分割方案。

// 示例: 输入: "aab" 输出: [ ["aa","b"], ["a","a","b"] ]

public class splitStringSolution {

    List<List<String>> result = new ArrayList<>();
    LinkedList<String> path = new LinkedList<String>();
    public List<List<String>> partition(String s) {
        trackPart(s, 0);
        return result;
    }

    void trackPart(String s, int startIndex) {
        // 终止条件：已经处理完整个字符串
        if (startIndex >= s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }

        // 从startIndex开始，尝试每一种分割方式
        for (int i = startIndex; i < s.length(); i++) {
            // 截取子串 [startIndex, i]
            String sub = s.substring(startIndex, i + 1);

            // 如果子串是回文，才继续分割
            if (ishuiwen(sub)) {
                path.add(sub);              // 加入路径
                trackPart(s, i + 1);        // 递归处理剩余部分
                path.removeLast();          // 回溯，移除当前子串
            }
        }
    }



    boolean ishuiwen(String str){
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        splitStringSolution solution = new splitStringSolution();

        // Example: "aab"
        System.out.println("Input: \"aab\"");
        List<List<String>> result = solution.partition("aab");
        System.out.println("Output: " + result);
        // Expected: [[a, a, b], [aa, b]]
    }
}
