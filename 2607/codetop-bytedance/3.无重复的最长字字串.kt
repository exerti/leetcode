/**
 * 字节-国际化电商-后端 2022-04-22
 * 
 * 原题：3. 无重复字符的最长子串（K = 1，每个字符最多出现1次）
 * 变体：允许每个字符最多重复 K 次，求最长子串
 * 
 * 核心思路：
 * - K=1 时可用 Map 记录位置，遇重复直接跳跃
 * - K>1 时只能用频率计数 + while 逐步收缩窗口
 * - 同样是滑动窗口，时间复杂度 O(n)
 */

// ============================================================
// 原题：K = 1（无重复）
// ============================================================
fun LengthOfLongestSubStr(s: String): Int {
    val map = HashMap<Char, Int>()
    var left = 0
    var maxLen = 0
    for (i in s.indices) {
        val ch = s[i]
        val prev = map[ch]
        if (prev != null && prev >= left) {
            left = prev + 1   // 直接跳跃
        }
        map[ch] = i
        maxLen = maxOf(maxLen, i - left + 1)
    }
    return maxLen
}

// ============================================================
// 变体：每个字符最多出现 K 次（通用版）
//
// 为什么 K>1 不能用"跳跃"技巧？
// ─────────────────────────────────
// K=1 时，每个字符只有 1 个历史位置，Map 直接告诉你跳哪。
// K>1 时，字符可能已经出现过 K 次分布在窗口不同位置，
// 你无法一步跳到正确位置，只能逐步收缩、逐次减频率。
//
// 举例：s = "a a a b c", K = 2
// ┌─────────┬───────┬──────┬─────────────────────────────┐
// │  right  │  ch   │ left │   窗口 & freq                │
// ├─────────┼───────┼──────┼─────────────────────────────┤
// │    0    │  'a'  │   0  │ "a"        freq={a:1}        │
// │    1    │  'a'  │   0  │ "aa"       freq={a:2}  ← 达标 │
// │    2    │  'a'  │   1  │ ⚠️ freq[a]=3 > 2             │
// │         │       │      │ left++ → "aa"  freq={a:2}   │
// │    3    │  'b'  │   1  │ "aab"      freq={a:2, b:1}  │
// │    4    │  'c'  │   1  │ "aabc"     freq={a:2,b:1,c:1}│
// └─────────┴───────┴──────┴─────────────────────────────┘
// 最终 maxLen = 4 = right(4) - left(1) + 1
//
// 与 K=1 跳跃式对比：
// K=1: 遇 'b' 重复 → Map 说 'b' 上次在位置1 → left 直接跳 1+1=2
// K=2: 遇 'a' 第3次 → freq[a]=3 > 2 → while 循环 left++ 删到 freq[a]≤2
// ============================================================
fun lengthOfLongestSubstringWithKRepeats(s: String, k: Int): Int {
    if (k <= 0) return 0                // K=0 表示任何字符都不允许，答案必为 0

    val freq = HashMap<Char, Int>()      // 窗口内每个字符 → 当前出现次数
    var left = 0                         // 窗口左边界（含）
    var maxLen = 0                       // 全局最长长度

    for (right in s.indices) {           // right = 窗口右边界（含），逐个字符扩展
        val ch = s[right]
        // 步骤1：新字符入窗，频率 +1
        freq[ch] = freq.getOrDefault(ch, 0) + 1

        // 步骤2：如果这个字符超标（出现次数 > K）
        //        从左边逐字删除，直到它的频率降回 K
        //        【关键】只删到 freq[ch] == K 就停，不会多删
        //        因为 while 收缩后窗口内所有字符必定 ≤ K
        while (freq[ch]!! > k) {
            val leftCh = s[left]         // 要被踢出窗口的字符
            freq[leftCh] = freq[leftCh]!! - 1
            if (freq[leftCh] == 0) {     // 频率归0 → 彻底不在窗口内，清理 key
                freq.remove(leftCh)
            }
            left++                       // 左边界右移 1 格
        }
        // 循环结束后，窗口 [left, right] 内所有字符频率 ≤ K

        // 步骤3：更新答案
        maxLen = maxOf(maxLen, right - left + 1)
    }

    return maxLen
}

// ============================================================
// 返回子串版本
// ============================================================
fun getSubOfLongestSubstringWithKRepeats(s: String, k: Int): String {
    if (k <= 0 || s.isEmpty()) return ""

    val freq = HashMap<Char, Int>()
    var left = 0
    var maxLen = 0
    var start = 0

    for (right in s.indices) {
        val ch = s[right]
        freq[ch] = freq.getOrDefault(ch, 0) + 1

        while (freq[ch]!! > k) {
            val leftCh = s[left]
            freq[leftCh] = freq[leftCh]!! - 1
            if (freq[leftCh] == 0) freq.remove(leftCh)
            left++
        }

        val curLen = right - left + 1
        if (curLen > maxLen) {
            maxLen = curLen
            start = left
        }
    }

    return s.substring(start, start + maxLen)
}

// ============================================================
// 测试
// ============================================================
fun main() {
    val tests = listOf(
        Triple("aabac", 2, "aaba"),   // 原题 K=1 → 答案 "bac" 长度3; K=2 → "aaba" 长度4
        Triple("abcabcbb", 1, "abc"),  // K=1 = 原题
        Triple("aaabb", 2, "aaabb"),   // K=2 全部符合
        Triple("aaabb", 1, "ab"),     // K=1 = 无重复
        Triple("abcde", 0, ""),       // K=0 全都不允许
    )

    for ((s, k, expected) in tests) {
        val len = lengthOfLongestSubstringWithKRepeats(s, k)
        val sub = getSubOfLongestSubstringWithKRepeats(s, k)
        println("s=\"$s\", K=$k → 长度=$len, 子串=\"$sub\" ${if (sub == expected) "✅" else "❌ 期望=\"$expected\""}")
    }
}
