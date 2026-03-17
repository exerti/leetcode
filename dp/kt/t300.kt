import kotlin.comparisons.maxOf

fun lengthofLcs(nums: IntArray): Int {

    val n = nums.size
    if (n <= 1) return n
    // dp[i] 到  i -1 的最大Lcs 长度
    val dp = IntArray(n + 1) { 1 }

    for (i in 1 until n) {
        for (j in 0 until i) {
            if (nums[i] > nums[j]) {
                dp[i] = maxOf(dp[i], dp[j - 1] + 1)
            }
        }
    }
    return dp.max()
}
