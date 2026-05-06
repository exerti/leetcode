package Array.`88`


/**
 * 哈希表
 * 字符串
 * 滑动窗口
 */

/**
 * 示例 1:
 *
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。注意 "bca" 和 "cab" 也是正确答案。
 * 示例 2:
 *
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 */

fun lengthOfLongestSubstring(s: String): Int {
    val map = HashMap<Char, Int>()
    var left = 0
    var maxLen = 0

    for(i in s.indices){
        var c = s[i]
        var prev = map[c]
        //prev >= left 就是为了过滤掉那些已经作废的历史记录。
        if(prev != null&&prev>=left){
            left = prev+1
        }
        map[c] = i
        maxLen = Math.max(maxLen,i-left+1)

    }

    return maxLen
}

fun longestSubstring(s: String): String {
    val map = HashMap<Char, Int>()
    var left = 0
    var start = 0
    var maxLen = 0

    for (right in s.indices) {
        val c = s[right]
        val prev = map[c]
        if (prev != null && prev >= left) {
            left = prev + 1
        }
        map[c] = right
        if (right - left + 1 > maxLen) {
            maxLen = right - left + 1
            start = left
        }
    }

    return s.substring(start, start + maxLen)
}

fun allLongestSubstrings(s: String): List<String> {
    val map = HashMap<Char, Int>()
    var left = 0
    var maxLen = 0
    val starts = mutableListOf<Int>()

    for (right in s.indices) {
        val c = s[right]
        val prev = map[c]
        if (prev != null && prev >= left) {
            left = prev + 1
        }
        map[c] = right
        val curLen = right - left + 1
        when {
            curLen > maxLen -> {
                maxLen = curLen
                starts.clear()
                starts.add(left)
            }
            curLen == maxLen -> {
                starts.add(left)
            }
        }
    }

    return starts.map { s.substring(it, it + maxLen) }.distinct()
}

//T1 遍历   所有字串，

//t2 哈希表

/**
 t  m  m  z  u  x  t
 0  1  2  3  4  5  6
            ↑       ↑
          left=2   right=6
当前窗口 [2,6] = "mzuxt"，窗口里没有重复。

查表：map['t'] = 0，prev = 0 不为 null → 触发跳转：


left = 0 + 1 = 1   ← left 从 2 退回到了 1
窗口变成 [1,6] = "mmzuxt"。
 */



//t3 滑动窗口



fun main() {
    val testCases = mapOf(
        "abcabcbb" to 3,
        "bbbbb" to 1,
        "pwwkew" to 3,
        "" to 0,
        " " to 1,
        "au" to 2,
        "dvdf" to 3,
    )
    for ((s, expected) in testCases) {
        val result = lengthOfLongestSubstring(s)
        val status = if (result == expected) "✓" else "✗"
        println("$status lengthOfLongestSubstring: s=\"$s\" → $result (expected $expected)")
    }

    println("\n--- longestSubstring ---")
    for (s in listOf("abcabcbb", "bbbbb", "pwwkew", "dvdf", "")) {
        println("s=\"$s\" → \"${longestSubstring(s)}\"")
    }

    println("\n--- allLongestSubstrings ---")
    for (s in listOf("abcabcbb", "bbbbb", "pwwkew", "dvdf", "")) {
        println("s=\"$s\" → ${allLongestSubstrings(s)}")
    }
}

