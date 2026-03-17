class longestCommonSubsequenceSolution {
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val n = text1.length
        val m = text2.length
        val dp = Array(n + 1) { IntArray(m + 1) { 0 } }
        for(i in 1..n ){
            for( j in 1..m){
                if(text1[i-1]==text2[j-1]){
                    dp[i][j] =  dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = maxOf(dp[i][j-1],dp[i-1][j])
                }
            }
        }
        return dp[n][m];
    }
}


/**
 * 
 * 
 * 
 * 这里 i-1 和 j-1 的设计有两层含义：

1. 坐标系的偏移

DP 数组是 (n+1) x (m+1) 大小，其中：

dp[0][...] 和 dp[...][0] 表示空字符串的情况（base case）
dp[i][j] 表示 text1 前 i 个字符与 text2 前 j 个字符的 LCS 长度
而字符串是 0-indexed，所以：

dp[1][1] 对应的是 text1[0] 和 text2[0]
dp[i][j] 对应的是 text1[i-1] 和 text2[j-1]
2. 状态转移的优雅性

这样设计后，状态转移方程可以直接写成：


dp[i][j] = dp[i-1][j-1] + 1      // 字符匹配
dp[i][j] = max(dp[i-1][j], dp[i][j-1])  // 不匹配
不需要额外处理边界条件，因为 dp[0][...] 和 dp[...][0] 天然为 0。

本质：这是一种"哨兵"技巧，用多开一行一列的空间，换取代码的简洁和边界处理的统一。


 */