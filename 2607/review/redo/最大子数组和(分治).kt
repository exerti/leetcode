package review.redo

/**
 * ⚠️ 上次失误（来自错题记录）：
 * 1. 横跨扫描时 crossMax 既当累加和又当最大值，需分 sum 和 leftMax/rightMax
 * 2. 左右扫描结果混入同一个变量 crossMax，应分开 leftMax+rightMax 再求和
 * 3. dfs(nums,left,mid-1) 排除 mid 导致 left>right 触发越界 base case
 * 4. 参数名 riht 拼写错误，应为 right
 * 📈 掌握度: 1/5 | 累计错误: 4次 | 间隔: 1天
 */
class Solution {
    fun maxSubArray(nums: IntArray): Int {
        TODO()
    }
}
