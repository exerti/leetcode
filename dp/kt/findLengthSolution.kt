class FindLengthSolution {
    fun findLength(nums1: IntArray, nums2: IntArray): Int {
        val n = nums1.size
        val m = nums2.size
        // dp[i][j] 表示以 nums1[i-1] 和 nums2[j-1] 结尾的最长公共子数组长度
        val dp = Array(n + 1) { IntArray(m + 1) { 0 } }
        var result = 0

        for (i in 1..n) {
            for (j in 1..m) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1
                    result = maxOf(result, dp[i][j])
                }
            }
        }
        return result
    }
}

fun main() {
    val solution = FindLengthSolution()
    println(solution.findLength(intArrayOf(1, 2, 3, 2, 1), intArrayOf(3, 2, 1, 4, 7))) // 3
}