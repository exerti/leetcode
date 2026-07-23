package new

/**
 * 最大子数组和 (LeetCode 53)
 * 示例: [-2,1,-3,4,-1,2,1,-5,4] → 子数组 [4,-1,2,1] → 最大和 = 6
 *
 * 四种解法思路，只给思考过程，代码自己填。
 */

class Solution {

    // =====================================================================
    // 解法1：暴力 O(n³) → O(n²)
    // =====================================================================
    /**
     * 直觉："把所有子数组枚举出来，算一遍和，取最大不就行了？"
     *
     * ▎子数组怎么枚举？
     *   起点 i (0..n-1)，终点 j (i..n-1)
     *   i 在外层，j 在内层
     *
     * ▎O(n³) vs O(n²) 的区别在哪？
     *   O(n³): 每对 (i,j) 都要从 i 加到 j
     *   O(n²): 固定 i，j 每次右移一位时，和 = 之前的和 + nums[j]，不用重新算
     *
     * ▎全负数数组 [-3, -1, -2]：
     *   如果最大和初始值设为 0，会发生什么？
     *   应该设为什么？
     *
     * ▎手写推演 [-2, 1, -3]:
     *   i=0: sum=-2 → max=?; j=1: sum=? → max=?; j=2: sum=? → max=?
     *   i=1: sum=? → max=?
     *   i=2: sum=? → max=?
     *   最终答案？
     */

    //O3
    fun maxSumBruteO3(nums: IntArray): Int {
        val size = nums.size
        var maxSum = Int.MIN_VALUE
        for (i in 0..size - 1) {
            for (j in i..size - 1) {
                var sum = 0
                for (k in i..j) {
                    sum += nums[k]
                }
                maxSum = maxOf(maxSum, sum)
            }
        }
        return maxSum
    }

    fun maxSumBruteO2(nums: IntArray): Int {
        val size = nums.size
        var maxSum = Int.MIN_VALUE
        for (i in 0..size - 1) {
            var sum = 0
            for (j in i..size - 1) {
                sum += nums[j]
                maxSum = maxOf(maxSum, sum)
            }
        }
        return maxSum

    }

    // =====================================================================
    // 解法2：动态规划 / Kadane O(n) ⭐
    // =====================================================================
    /**
     * ▎第一层：定义状态
     *   "最大子数组和"太模糊。加一个约束——定义 dp[i] = ?
     *   （提示：限定子数组必须以谁结尾？）
     *
     * ▎第二层：递推关系
     *   站在位置 i，你有两个选择：
     *     a) 把 nums[i] 接到前面后面 → ?
     *     b) 前面的不要了，自己单干 → ?
     *   什么时候选 a，什么时候选 b？
     *   （提示：前面的累积和是负数时，接上它只会 _____）
     *
     * ▎第三层：手写推演 [-2, 1, -3, 4]
     *   请按上述逻辑，一步步算：
     *   i=0: cur=?, max=?
     *   i=1: cur=max(1, ?) = ? → 为什么选这个？
     *   i=2: cur=max(-3, ?) = ?
     *   i=3: cur=max(4, ?) = ?
     *   最终答案？
     *
     * ▎第四层：空间优化
     *   dp[i] 只依赖 dp[i-1]，能不能不用数组？
     *   需要几个变量？分别存什么？
     *
     * ▎第五层：全负数验证
     *   [-3, -1, -2]：用你的逻辑走一遍，答案应该是什么？
     */
    fun maxSumDP(nums: IntArray): Int {
        val dp = IntArray(nums.size)
        dp[0] = nums[0]
        var maxSum = dp[0]
        for (i in 1..(nums.size - 1)) {
            dp[i] = maxOf(dp[i - 1] + nums[i], nums[i])

            maxSum = maxOf(maxSum, dp[i])
        }
        return maxSum
    }

    // =====================================================================
    // 解法3：分治法 O(n log n)
    // =====================================================================
    /**
     * ▎最大子数组可能出现在哪三种位置？
     *   ① _________
     *   ② _________
     *   ③ _________
     *
     * ▎①② 是子问题，递归即可。③ 怎么算？
     *   "横跨中点" → 从中点向两边扩展
     *   向左：从 __ 开始向左累加，记录最大值
     *   向右：从 __ 开始向右累加，记录最大值
     *   横跨 = ____ + ____
     *
     * ▎手写推演 [-2, 1, -3, 4, -1, 2]
     *   mid=2 (索引), 中点值=-3
     *   左半递归 [-2,1,-3] → 最大?
     *   右半递归 [4,-1,2]  → 最大?
     *   横跨：向左累加 _____ → leftMax=?
     *         向右累加 _____ → rightMax=?
     *         横跨 = ?
     *   max(?, ?, ?) = ?
     *
     * ▎递归终止条件？
     *   当 left == right 时，返回什么？
     *
     * ▎分治法比 DP 慢，为什么还要学？
     *   （提示：如果数组元素可以修改，需要反复查询最大子数组和呢？）
     */
    fun maxSumDivide(nums: IntArray): Int {
        val size = nums.size
        val left = 0
        val right = size - 1
        return dfs(nums, 0, right)


    }

    fun dfs(nums: IntArray, left: Int, right: Int): Int {
        if (left >= right) return nums[right]
        val mid = left + (right - left) / 2

        val dfsleftMax = dfs(nums, left, mid)
        val dfsrightMax = dfs(nums, mid + 1, right)
        var crossMax = Int.MIN_VALUE
        var sum = 0
        var leftMax = Int.MIN_VALUE
        for (i in mid downTo left) {
            sum += nums[i]
            leftMax = maxOf(leftMax, sum)
        }
        sum = 0
        var rightMax = Int.MIN_VALUE
        for (i in mid + 1..right) {
            sum += nums[i]
            rightMax = maxOf(rightMax, sum)
        }

        crossMax = leftMax + rightMax

        return maxOf(dfsleftMax, dfsrightMax, crossMax)


    }

    // =====================================================================
    // 解法4：前缀和视角 O(n)
    // =====================================================================
    /**
     * 换个角度：子数组 [i.j] 的和 = prefix[j] - prefix[i-1]
     *
     * ▎要让这个差最大，需要什么？
     *   prefix[j] 要尽可能 ___
     *   prefix[i-1] 要尽可能 ___
     *
     * ▎遍历时如何同时维护这两个值？
     *   遍历 j，同时记录「目前为止最小的前缀和」
     *   每步的候选答案 = prefix[j] - minPrefix
     *
     * ▎手写推演 [-2, 1, -3, 4]:
     *   prefix:  ?, ?, ?, ?
     *   minPrefix:  ?, ?, ?, ?
     *   差值:  ?, ?, ?, ? → 答案?
     *
     * ▎这个思路的优势？
     *   很容易扩展到"子数组和 ≥ K 的最短长度"等变体
     */
    fun maxSumPrefix(nums: IntArray): Int {
        var pre = 0               // 当前前缀和
        var minPre = 0            // 之前见过的最小前缀和
        var maxSum = Int.MIN_VALUE
        for (num in nums) {
            pre += num
            maxSum = maxOf(maxSum, pre - minPre)
            minPre = minOf(minPre, pre)
        }
        return maxSum
    }
}

// =====================================================================
// 解法对比 — 填表
// =====================================================================
/**
 * ┌──────────┬────────────┬───────────────────────────────────┐
 * │  解法    │  时间复杂度 │  适用场景 / 一句话评价             │
 * ├──────────┼────────────┼───────────────────────────────────┤
 * │  暴力    │            │                                   │
 * │  DP      │            │                                   │
 * │  分治    │            │                                   │
 * │  前缀和  │            │                                   │
 * └──────────┴────────────┴───────────────────────────────────┘
 */

// =====================================================================
// 变体思考
// =====================================================================
/**
 * ▎变体1：返回子数组本身而不只是和
 *   需要多记录什么信息？什么时候更新？
 *
 * ▎变体2：最大子数组积 (LeetCode 152)
 *   积和和有什么区别？负负得正怎么处理？
 *
 * ▎变体3：子数组和 ≥ K 的最短长度 (LeetCode 209 变体)
 *   能用 DP 吗？前缀和 + 什么数据结构？
 */
