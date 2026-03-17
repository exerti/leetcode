class LastStoneWeightIISolution {
    fun lastStoneWeightII(stones: IntArray): Int {
        val sum = stones.sum()
        val target = sum / 2
        val dp = IntArray(target + 1) { 0 }

        for (i in stones.indices) {
            for (j in target downTo stones[i]) {
                dp[j] = maxOf(dp[j], dp[j - stones[i]] + stones[i])
            }
        }
        // 一堆石头重量 dp[target]，另一堆 sum - dp[target]
        // 差值 = (sum - dp[target]) - dp[target]
        return sum - 2 * dp[target]
    }
}

fun main() {
    val solution = LastStoneWeightIISolution()
    println(solution.lastStoneWeightII(intArrayOf(2, 7, 4, 1, 8, 1))) // 1
    println(solution.lastStoneWeightII(intArrayOf(31, 26, 33, 21, 40))) // 5
}