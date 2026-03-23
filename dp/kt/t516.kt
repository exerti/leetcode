fun longestPalindromeSubseq(s: String): Int {
    val len = s.length
    val dp = Array(len) { IntArray(len) { 0 } }
    for (i in 0..len) dp[i][i] = 1
    for (i in len-1 downTo 0) {
        for (j in i + 1..len-1) {
            if (s[i] == s[j]) {
                dp[i][j] = dp[i +1][j - 1] + 2
            } else {
                dp[i][j] = maxOf(dp[i +1][j], dp[i][j - 1])
            }
        }
    }
    return dp[0][len - 1]
}


/**
 * 这题的 i、j 下标直接对应字符串的索引，和之前 LCS 那种多开一行一列的方式不同。

为什么这里不用 i-1、j-1？

因为 dp 数组大小是 len x len，不是 (len+1) x (len+1)。dp[i][j] 直接表示 s[i..j] 这段子串的最长回文子序列长度，没有"空字符串"那一行/列，所以不需要偏移。

遍历顺序的讲究：

dp[i][j] 依赖三个位置：


          j-1    j
i         ...   dp[i][j]
i+1    dp[i+1][j-1]  dp[i+1][j]
dp[i+1][j-1] — 左下方
dp[i+1][j] — 正下方
dp[i][j-1] — 正左方
所以：

i 必须从下往上（len-1 downTo 0），保证 i+1 已经算过
j 必须从左往右（i+1 until len），保证 j-1 已经算过
对比两种风格：

LCS（多开一行列）	本题（直接索引）
dp 大小	(n+1) x (m+1)	len x len
访问原数组	s[i-1], t[j-1]	s[i], s[j]
base case	dp[0][...] 天然为 0	需要手动初始化 dp[i][i] = 1
两种方式都可以，选哪种取决于 base case 处理哪种更方便。


 */