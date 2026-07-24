package review

/**
 * ⚠️ 上次失误:
 * 1. HashMap<String,Int> 键类型错误，s[right] 返回 Char 非 String
 * 2. preIndex >= right 应为 >= left，窗口收缩条件写反
 * 3. left = right-1 应为 preIndex+1，应跳过重复字符而非回退到 right-1
 * 📈 掌握度: 2/5 | 累计错误: 3次 | 间隔: 3天 | 下次复习: 07-26
 */

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        TODO()
    }
}

fun main() {
    // 测试用例
    val s1 = "abcabcbb"  // 预期: 3 ("abc")
    val s2 = "pwwkew"    // 预期: 3 ("wke")
}
