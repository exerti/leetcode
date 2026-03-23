fun maxUncrossedLines(nums1: IntArray, nums2: IntArray): Int {
    val n = nums1.size
    val m = nums2.size
    val dp = Array(n + 1) { IntArray(m + 1) { 0 } }
    for (i in 1..n) {
        for (j in 1..m) {
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
            } else {
                dp[i][j] = maxOf(dp[i - 1][j], dp[i][j - 1])
            }
        }
    }
    return dp[n][m]
}
