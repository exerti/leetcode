// 主解：Kadane(空间 O(1))
// 思路：f(i) = max(f(i-1) + nums[i], nums[i]),累加变亏就重来
fun maxSubArray(nums: IntArray): Int {
    var cur = nums[0]
    var best = nums[0]
    for (i in 1 until nums.size) {
        cur = maxOf(cur + nums[i], nums[i])
        best = maxOf(best, cur)
    }
    return best
}

// 解法 1:暴力 O(n^2)
// 固定左端点,累加扫右端点
fun maxSubArrayBrute(nums: IntArray): Int {
    var best = Int.MIN_VALUE
    for (i in nums.indices) {
        var s = 0
        for (j in i until nums.size) {
            s += nums[j]
            best = maxOf(best, s)
        }
    }
    return best
}

// 解法 2:DP 完整数组 O(n) 时间 / O(n) 空间
// f[i] = 以 i 结尾的最大子数组和
fun maxSubArrayDP(nums: IntArray): Int {
    val f = IntArray(nums.size)
    f[0] = nums[0]
    var best = f[0]
    for (i in 1 until nums.size) {
        f[i] = maxOf(f[i - 1] + nums[i], nums[i])
        best = maxOf(best, f[i])
    }
    return best
}

// 解法 3:前缀和 O(n)
// best = max(pre[j] - min(pre[0..j-1]))
fun maxSubArrayPrefix(nums: IntArray): Int {
    var pre = 0
    var minPre = 0
    var best = Int.MIN_VALUE
    for (x in nums) {
        pre += x
        best = maxOf(best, pre - minPre)
        minPre = minOf(minPre, pre)
    }
    return best
}

// 解法 4:分治 O(n log n)
// 结果要么全在左半,要么全在右半,要么跨中点
fun maxSubArrayDivide(nums: IntArray): Int {
    return divide(nums, 0, nums.size - 1)
}

private fun divide(nums: IntArray, l: Int, r: Int): Int {
    if (l == r) return nums[l]
    val mid = (l + r) ushr 1

    val leftBest = divide(nums, l, mid)
    val rightBest = divide(nums, mid + 1, r)

    // 跨中点:从 mid 向左的最大后缀 + 从 mid+1 向右的最大前缀
    var s = 0
    var leftSuffix = Int.MIN_VALUE
    for (i in mid downTo l) {
        s += nums[i]
        leftSuffix = maxOf(leftSuffix, s)
    }
    s = 0
    var rightPrefix = Int.MIN_VALUE
    for (i in mid + 1..r) {
        s += nums[i]
        rightPrefix = maxOf(rightPrefix, s)
    }
    val cross = leftSuffix + rightPrefix

    return maxOf(leftBest, rightBest, cross)
}

fun main() {
    val cases = listOf(
        intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4),  // 6
        intArrayOf(1),                                // 1
        intArrayOf(5, 4, -1, 7, 8),                   // 23
        intArrayOf(-1, -2, -3),                       // -1
    )

    for (nums in cases) {
        val a = maxSubArray(nums)
        val b = maxSubArrayBrute(nums)
        val c = maxSubArrayDP(nums)
        val d = maxSubArrayPrefix(nums)
        val e = maxSubArrayDivide(nums)
        println("${nums.toList()} -> Kadane=$a Brute=$b DP=$c Prefix=$d Divide=$e 一致=${a == b && b == c && c == d && d == e}")
    }
}
