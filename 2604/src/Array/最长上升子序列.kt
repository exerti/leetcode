package Array


//求长度
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


//求一条最长递增子序列(LIS 的元素序列)
fun SOfLIS(nums: IntArray): IntArray {
    val n = nums.size
    if (n == 0) return intArrayOf()
    if (n == 1) return intArrayOf(nums[0])

    // dp[i]  = 以 nums[i] 结尾的 LIS 长度
    // prev[i] = dp[i] 是从哪个 j 转移来的(回溯用),-1 表示没有前驱
    val dp = IntArray(n) { 1 }
    val prev = IntArray(n) { -1 }
    var bestEnd = 0

    for (i in 1 until n) {
        for (j in 0 until i) {
            if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                dp[i] = dp[j] + 1
                prev[i] = j
            }
        }
        if (dp[i] > dp[bestEnd]) bestEnd = i
    }

    // 从最长结尾沿 prev 回溯
    val stack = ArrayDeque<Int>()
    var cur = bestEnd
    while (cur != -1) {
        stack.addFirst(nums[cur])
        cur = prev[cur]
    }
    return stack.toIntArray()
}

//求所有最长递增子序列(数量可能指数级多,仅小规模使用)
fun allLIS(nums: IntArray): List<IntArray> {
    val n = nums.size
    if (n == 0) return emptyList()

    val dp = IntArray(n) { 1 }
    for (i in 1 until n) {
        for (j in 0 until i) {
            if (nums[j] < nums[i]) dp[i] = maxOf(dp[i], dp[j] + 1)
        }
    }
    val maxLen = dp.max()!!

    val result = mutableListOf<IntArray>()
    val path = ArrayDeque<Int>()

    fun dfs(i: Int) {
        path.addFirst(nums[i])
        if (dp[i] == 1) {
            result.add(path.toIntArray())
        } else {
            for (j in 0 until i) {
                if (nums[j] < nums[i] && dp[j] == dp[i] - 1) dfs(j)
            }
        }
        path.removeFirst()
    }

    for (i in 0 until n) {
        if (dp[i] == maxLen) dfs(i)
    }
    return result
}