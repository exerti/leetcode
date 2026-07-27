/**
 * 【原代码错误分析】
 *
 * 错误在第 13 行：`left = right - 1`
 *
 * 当遇到重复字符时，left 应该跳到「上一个相同字符的下一个位置」，
 * 即 `left = map[ch]!! + 1`，而不是 `right - 1`。
 *
 * 反例：输入 "abba"
 *   right=0, ch='a': map['a']=null, 无重复, left=0, maxLen=1
 *   right=1, ch='b': map['b']=null, 无重复, left=0, maxLen=2
 *   right=2, ch='b': map['b']=1, 重复, left = right-1 = 1, maxLen=2
 *   right=3, ch='a': map['a']=0, 重复, left = right-1 = 2, maxLen=2
 *   ❌ 输出 2，正确答案是 2（"ab"或"ba"）—— 这个例子碰巧对了
 *
 * 反例：输入 "pwwkew"
 *   right=2, ch='w': 重复, left = right-1 = 1
 *   窗口变为 s[1..2]="ww" → 错误！w 还在窗口里重复了
 *   ❌ 输出 4，正确答案是 3（"wke"）
 *
 * 正确做法：
 *   找到重复字符上一次出现的位置 lastPos，
 *   如果 lastPos >= left（说明重复在窗口内），left 跳到 lastPos + 1。
 *   如果 lastPos < left（重复在窗口外），不需要移动 left。
 */

class Solution {

    fun lengthOfLongestSubString(s: String): Int {
        val map = HashMap<Char, Int>()
        var left = 0
        var maxLen = 0
        for (right in s.indices) {
            val ch = s[right]
            val lastPos = map[ch]
            // 字符重复 且 上一次出现在窗口内 → 收缩左边界
            if (lastPos != null && lastPos >= left) {
                left = lastPos + 1
            }
            map[ch] = right
            maxLen = maxOf(maxLen, right - left + 1)
        }
        return maxLen
    }
}

fun main() {

    val handler = Solution()::lengthOfLongestSubString
    val testCase1 = "abcabcbb"
    println(handler(testCase1))  // 期望 3

    val testCase2 = "bbbbb"
    println(handler(testCase2))  // 期望 1

    val testCase3 = "pwwkew"
    println(handler(testCase3))  // 期望 3

    val testCase4 = "dvdf"
    println(handler(testCase4))  // 期望 3

}
