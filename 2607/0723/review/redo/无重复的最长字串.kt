package review.redo

/**
 * ⚠️ 上次失误：
 * 1. HashMap<String, Int> → HashMap<Char, Int>（s[right] 返回 Char）
 * 2. 窗口条件 → preIndex >= left 不是 preIndex >= right
 * 3. left 收缩 → left = preIndex + 1 不是 left = right - 1
 * 📈 掌握度: 2/5 | 累计错误: 3次 | 间隔: 3天
 */
class LongestSubstring {
    fun lengthOfLongestSubstring(s: String): Int {
        TODO()
        return TODO("Provide the return value")
    }
}
