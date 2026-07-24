package review.redo

/**
 * ⚠️ 上次失误（来自错题记录）：
 * 1. var maxSum = null 导致 maxOf 无法编译，应为 Int.MIN_VALUE
 * 2. O3 函数缺少 return 语句
 * 3. O2 中 maxSum+=nums[i]，把裁判当选手——应 sum+=nums[j]
 * 4. maxof 拼写错误，应为 maxOf（大写O）
 * 5. dp[i]=maxOf(dp[i]+nums[i]..) 用了自己而非 dp[i-1]，递推断裂
 * 6. 返回 dp[nums.size-1] 只是末尾局部最优，非全局最大
 * 7. maxSum=0 导致全负数数组返回 0，应初始化为 dp[0] 或 nums[0]
 * 📈 掌握度: 1/5 | 累计错误: 7次 | 间隔: 1天
 */
class DpMaxSubArray {
    fun maxSubArray(nums: IntArray): Int {
        TODO()
    }
}
