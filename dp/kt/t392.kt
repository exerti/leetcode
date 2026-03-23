fun isSubsequence(s: String, t: String): Boolean {
    val n = s.length
    val m = t.length
    if (n == 0) return true   // s 为空，是任何字符串的子序列
    if (m == 0) return false  // t 为空但 s 不为空，不可能是子序列
    
    val dp = Array(n + 1) { IntArray(m + 1) { 0 } }
    for (i in 1..n) {
        for (j in 1..m) {
            if (s[i - 1] == t[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
            } else {
                dp[i][j] = maxOf(dp[i - 1][j], dp[i][j - 1])
            }
        }
    }
    return dp[n][m] == n  // LCS 长度等于 s 的长度，说明 s 是 t 的子序列
}
