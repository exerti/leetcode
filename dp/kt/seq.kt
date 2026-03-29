class SeqSolution {
    fun lengthOfLIS(nums: IntArray): Int {
        val n = nums.size
        if (n <= 1) return n
        // dp[i] 表示以 nums[i] 结尾的最长递增子序列长度
        val dp = IntArray(n) { 1 }
        for (i in 1 until n) {
            //内层循环是找当前元素之前所有比它小的元素，取最长的子序列长度 +1。
            for (j in 0 until i) {
                if (nums[i] > nums[j]) {
                    dp[i] = maxOf(dp[i], dp[j] + 1)
                }
            }
        }
        return dp.max()
    }


}

fun main() {
    val solution = SeqSolution()
    println(solution.lengthOfLIS(intArrayOf(10, 9, 2, 5, 3, 7, 101, 18))) // 4
    println(solution.lengthOfLIS(intArrayOf(0, 1, 0, 3, 2, 3))) // 4
}

/**
 * 
 * 
举例：nums = [10, 9, 2, 5, 3, 7]
i	nums[i]	内层循环检查	dp[i]
0	10	无	1
1	9	9 < 10，不能接	1
2	2	2 < 10, 2 < 9，不能接	1
3	5	5 > 2 ✓，可接在 2 后面	dp[2]+1 = 2
4	3	3 > 2 ✓	dp[2]+1 = 2
5	7	7 > 2 ✓, 7 > 5 ✓, 7 > 3 ✓	max(dp[2]+1, dp[3]+1, dp[4]+1) = 3
最终 dp = [1, 1, 1, 2, 2, 3]，最长递增子序列是 [2, 5, 7] 或 [2, 3, 7]，长度为 3。
 */