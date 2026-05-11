/**
 * 上一个排列(字典序更小的那个排列,对称于"下一个排列")。
 *
 * 整数数组的 上一个排列 是指其整数的上一个字典序更小的排列。
 * 如果不存在(当前就是最小,即全升序),那么数组必须重排为字典序最大的排列(全降序)。
 *
 * 必须原地修改,只允许使用额外常数空间。
 *
 * 示例:
 *   [3,2,1] -> [3,1,2]
 *   [1,2,3] -> [3,2,1]   // 已是最小,翻转成最大
 *   [1,3,2] -> [1,2,3]
 *   [2,3,1] -> [2,1,3]
 *   [1,1,5] -> [5,1,1]   // 已是最小,翻转成最大
 */
fun prevPermutation(nums: IntArray): Unit {
    val n = nums.size
    if (n <= 1) return

    // 1) 从右往左找第一个"上升点" i:nums[i] > nums[i+1]
    //    注意和 nextPermutation 完全对称,这里比较符号是 >
    var i = n - 2
    while (i >= 0 && nums[i] <= nums[i + 1]) i--

    if (i >= 0) {
        // 2) 从右往左找第一个 j 满足 nums[j] < nums[i]
        //    i 右边现在是升序,所以从最右往左遇到第一个 < nums[i] 的就是
        //    "恰好小于 nums[i] 的最大值",贪心正确
        var j = n - 1
        while (j >= 0 && nums[j] >= nums[i]) j--
        swap(nums, i, j)
    }

    // 3) 反转 i+1..n-1 这段。
    //    反转前是升序(i 右边性质),反转后变降序 —— 让后半段取到最大,
    //    整体才是"上一个"(字典序减一)。
    //    i 为 -1 时,相当于整个数组已是最小,反转整段得到最大排列。
    reverse(nums, i + 1, n - 1)
}

private fun swap(nums: IntArray, x: Int, y: Int) {
    val temp = nums[x]
    nums[x] = nums[y]
    nums[y] = temp
}

private fun reverse(nums: IntArray, leftIn: Int, rightIn: Int) {
    var left = leftIn
    var right = rightIn
    while (left < right) {
        val temp = nums[left]
        nums[left] = nums[right]
        nums[right] = temp
        left++
        right--
    }
}

fun main() {
    val testCases = listOf(
        intArrayOf(3, 2, 1) to intArrayOf(3, 1, 2),
        intArrayOf(1, 2, 3) to intArrayOf(3, 2, 1),   // 最小 -> 最大
        intArrayOf(1, 3, 2) to intArrayOf(1, 2, 3),
        intArrayOf(2, 3, 1) to intArrayOf(2, 1, 3),
        intArrayOf(1, 1, 5) to intArrayOf(5, 1, 1),   // 最小 -> 最大(含重复)
    )

    for ((input, expected) in testCases) {
        val before = input.copyOf()
        prevPermutation(input)
        val ok = input.contentEquals(expected)
        println(
            "输入=${before.contentToString()} " +
            "期望=${expected.contentToString()} " +
            "实际=${input.contentToString()} " +
            "通过=$ok"
        )
    }
}
