class CanPartitionSolution {
    fun canPartition(nums: IntArray): Boolean {
        val sum = nums.sum()
        if (sum % 2 != 0) return false
        val target = sum / 2
        // dp[j] 表示容量为 j 的背包能装的最大价值
        val dp = IntArray(target + 1) { 0 }
        //先便利物品在便利背包
        for (i in nums.indices) {
            // 倒序遍历，防止重复使用
            for (j in target downTo nums[i]) {
                dp[j] = maxOf(dp[j], dp[j - nums[i]] + nums[i])
            }
        }

        return dp[target] == target
    }
}

fun main() {
    val solution = CanPartitionSolution()
    println(solution.canPartition(intArrayOf(1, 5, 11, 5))) // true
    println(solution.canPartition(intArrayOf(1, 2, 3, 5))) // false
}


/**
 * 初始化

sum = 1 + 5 + 11 + 5 = 22
target = 22 / 2 = 11
dp = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]  // 长度 12 (target+1)
      ↑                                ↑
    dp[0]                           dp[11]

第 1 轮：处理 nums[0] = 1
j 从 11 倒序到 1：

j=11: dp[11] = max(dp[11], dp[11-1] + 1) = max(0, 0+1) = 1
j=10: dp[10] = max(dp[10], dp[10-1] + 1) = max(0, 0+1) = 1
...
j=1:  dp[1]  = max(dp[1],  dp[1-1] + 1)  = max(0, 0+1) = 1

dp = [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]


第 2 轮：处理 nums[1] = 5

j 从 11 倒序到 5：

j=11: dp[11] = max(dp[11], dp[11-5] + 5) = max(1, 1+5) = 6
j=10: dp[10] = max(dp[10], dp[10-5] + 5) = max(1, 1+5) = 6
j=9:  dp[9]  = max(dp[9],  dp[9-5] + 5)  = max(1, 1+5) = 6
j=8:  dp[8]  = max(dp[8],  dp[8-5] + 5)  = max(1, 1+5) = 6
j=7:  dp[7]  = max(dp[7],  dp[7-5] + 5)  = max(1, 1+5) = 6
j=6:  dp[6]  = max(dp[6],  dp[6-5] + 5)  = max(1, 1+5) = 6
j=5:  dp[5]  = max(dp[5],  dp[5-5] + 5)  = max(1, 0+5) = 5

dp = [0, 1, 1, 1, 1, 5, 6, 6, 6, 6, 6, 6]

第 3 轮：处理 nums[2] = 11

j 从 11 倒序到 11（只有一次）：

j=11: dp[11] = max(dp[11], dp[11-11] + 11) = max(6, 0+11) = 11 ✓

dp = [0, 1, 1, 1, 1, 5, 6, 6, 6, 6, 6, 11]


第 4 轮：处理 nums[3] = 5

j 从 11 倒序到 5：

j=11: dp[11] = max(dp[11], dp[11-5] + 5) = max(11, 6+5) = 11
j=10: dp[10] = max(dp[10], dp[10-5] + 5) = max(6, 5+5)  = 10
j=9:  dp[9]  = max(dp[9],  dp[9-5] + 5)  = max(6, 6+5)  = 11
j=8:  dp[8]  = max(dp[8],  dp[8-5] + 5)  = max(6, 6+5)  = 11
j=7:  dp[7]  = max(dp[7],  dp[7-5] + 5)  = max(6, 6+5)  = 11
j=6:  dp[6]  = max(dp[6],  dp[6-5] + 5)  = max(6, 5+5)  = 10
j=5:  dp[5]  = max(dp[5],  dp[5-5] + 5)  = max(5, 0+5)  = 5

dp = [0, 1, 1, 1, 1, 5, 10, 11, 11, 11, 10, 11]



遍历方向	更新 dp[j] 时用的 dp[j-num]	效果
正序	本轮已更新的值	同一物品可能被重复使用
倒序	上一轮的旧值	每个物品只用一次
倒序保证了在更新大容量时，小容量的值还没被当前物品污染，从而实现"选或不选"的 01 选择。
 * 
 * 
 */