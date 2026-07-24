package review

import kotlin.math.max

/**
 * ⚠️ 上次失误:
 * 1. HashMap<String,Int> 键类型错误，s[right] 返回 Char 非 String
 * 2. preIndex >= right 应为 >= left，窗口收缩条件写反
 * 3. left = right-1 应为 preIndex+1，应跳过重复字符而非回退到 right-1
 * 📈 掌握度: 2/5 | 累计错误: 3次 | 间隔: 3天 | 下次复习: 07-26
 */

//preIndx >= left = "上次出现的位置在窗口左边界之右 → 还在窗口里 → 得收缩"。这是滑动窗口的核心判断。
class LongestNoRepeatSubstring {
    fun lengthOfLongestSubstring(s: String): Int {
        val map = HashMap<Char, Int>()
        var maxCount = 0
        val size = s.length
        var left = 0
        for (right in 0..size - 1) {
            val ch = s[right]
            val preIndx = map[ch]
            if (preIndx != null && preIndx >= left) {
                left = preIndx + 1
            }
            map[ch] = right
            maxCount = maxOf(maxCount, right - left + 1)
        }
        return maxCount
    }
}

fun main() {
    // 测试用例
    val s1 = "abcabcbb"  // 预期: 3 ("abc")
    val s2 = "pwwkew"    // 预期: 3 ("wke")
}
