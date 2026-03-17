fun lengthOfLIS(nums: IntArray): Int {
    val n = nums.size
    if (n <= 1) return n
    
    // dp[i] 表示以 nums[i] 结尾的最长递增子序列长度
    val dp = IntArray(n) { 1 }

    for (i in 1 until n) {
        for (j in 0 until i) {
            if (nums[i] > nums[j]) {
                dp[i] = maxOf(dp[i], dp[j] + 1)  // 修正：dp[j] + 1
            }
        }
    }
    return dp.max()
}
