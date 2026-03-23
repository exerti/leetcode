fun countSubstrings(s: String): Int {
    val len = s.length
    //dp[i][j] 表示 i 到 j 这个字符串是不是回文
    val dp = Array(len) { BooleanArray(len) { false } }

    var result = 0
    // 从下往上遍历 i，从左往右遍历 j
    for (i in len - 1 downTo 0) {
        for (j in i until len) {
            if (s[i] == s[j]) {
                if (j - i <= 1) {
                    dp[i][j] = true
                    result++
                } else if (dp[i + 1][j - 1]) {
                    dp[i][j] = true
                    result++
                }
            }
        }
    }
    return result
}

/**
 * 
 * 对，因为 j 从 i 开始（j >= i），所以只遍历矩阵的右上三角部分：


     j →  0  1  2  3  4
i ↓
0        [✓] [✓] [✓] [✓] [✓]
1            [✓] [✓] [✓] [✓]
2                [✓] [✓] [✓]
3                    [✓] [✓]
4                        [✓]
遍历方向是从底行往上（i 从 len-1 到 0），每行从对角线往右（j 从 i 到 len-1）。

这是因为子串 s[i..j] 要求 i <= j，左下三角（i > j）没有意义。对角线上 i == j 就是单个字符，天然是回文。
 */