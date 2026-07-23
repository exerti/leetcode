package review.heap

class Solution555 {

    fun lengthOfLongestSubstring(s: String): Int {
        val map = HashMap<Char, Int>() // s[right] 返回 Char，不是 String
        var left = 0
        var maxCount = 0
        for (right in 0 until s.length) {
            val ch = s[right]
            val preIndex = map[ch]
            // 字符在窗口内出现过 → 收缩左边界
            if (preIndex != null && preIndex >= left) { // >= left，不是 >= right
                left = preIndex + 1                     // +1 跳过重复字符
            }
            map[ch] = right
            maxCount = maxOf(maxCount, right - left + 1)
        }
        return maxCount
    }
}