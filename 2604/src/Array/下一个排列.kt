/**
整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。

例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。

例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
给你一个整数数组 nums ，找出 nums 的下一个排列。

必须 原地 修改，只允许使用额外常数空间。



示例 1：

输入：nums = [1,2,3]
输出：[1,3,2]
示例 2：

输入：nums = [3,2,1]
输出：[1,2,3]
示例 3：

输入：nums = [1,1,5]
输出：[1,5,1]
 */

/**

nums = [1, 2, 7, 4, 3, 1]

下一个排列应该是 [1, 3, 1, 2, 4, 7]。
 */

fun nextPermutation(nums: IntArray): Unit {
    val n = nums.size
    if (n <= 1) return

    //从右往左找第一个 i 满足 nums[i] < nums[i+1]
    var i = n - 2
    while (i >= 0 && nums[i] >= nums[i + 1]) i--

    if (i >= 0) {
        var j = n - 1

        //  从右往左找第一个 j 满足 nums[j] > nums[i]
        while (j >= 0 && nums[i] >= nums[j]) j--
        swap(nums, i, j)
    }
    reverse(nums, i + 1, n - 1)
}


fun swap(nums: IntArray, x: Int, y: Int) {
    var temp = nums[x]
    nums[x] = nums[y]
    nums[y] = temp
}

fun reverse(nums: IntArray, left: Int, right: Int) {
    var left = left
    var right = right
    while (left <= right) {
        var temp = nums[left]
        nums[left] = nums[right]
        nums[right] = temp
        left++
        right--
    }
}


fun main(args: Array<String>) {
    val testCases = listOf(
        intArrayOf(1, 2, 7, 4, 3, 1) to intArrayOf(1, 3, 1, 2, 4, 7),
        intArrayOf(3, 2, 1) to intArrayOf(1, 2, 3),
        intArrayOf(1, 2, 3) to intArrayOf(1, 3, 2),
        intArrayOf(1, 1, 5) to intArrayOf(1, 5, 1),
    )

    for ((input, expected) in testCases) {
        val before = input.copyOf()
        nextPermutation(input)
        val ok = input.contentEquals(expected)
        println(
            "输入=${before.contentToString()} " +
                    "期望=${expected.contentToString()} " +
                    "实际=${input.contentToString()} " +
                    "通过=$ok"
        )
    }
}


/**
用户问：在 nextPermutation 里，为什么"从右往左找下降点 i"是从 n-2 开始，而"从右往左找 j"是从 n-1 开始？这是两次从右往左扫描，起点不同的原因。

Problem Decomposition:

第一次扫描（找下降点 i）：要比较 nums[i] 和 nums[i+1]，所以最大的合法 i 是 n-2，否则 i+1 = n 会越界。本质是"比较相邻两数"需要两个元素。
第二次扫描（找交换目标 j）：只访问 nums[j] 本身，和 nums[i] 比，不需要 j+1，所以 j 可以从最右端 n-1 开始。 */