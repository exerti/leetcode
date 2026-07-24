package review

/**
 * ⚠️ 上次失误:
 * 1. 横跨扫描时 crossMax 既当累加和又当最大值，需分 sum 和 leftMax/rightMax
 * 2. 左右扫描结果混入同一个变量 crossMax，应分开 leftMax+rightMax 再求和
 * 3. dfs(nums,left,mid-1) 排除 mid 导致 left>right 触发越界 base case
 * 4. 参数名 riht 拼写错误，应为 right
 * 📈 掌握度: 1/5 | 累计错误: 4次 | 间隔: 1天
 */

class DivideMaxSubArray {
    fun maxSubArray(nums: IntArray): Int {
        val size = nums.size
        return dfs(nums, 0, size - 1)
    }

    fun dfs(nums: IntArray, left: Int, right: Int): Int {
        if (left >= right) return nums[left]
        val mid = left + (right - left) / 2
        val _leftMax = dfs(nums, left, mid)
        val _rightMax = dfs(nums, mid + 1, right)
        var _crossMax: Int
        var crossLeftMax = Int.MIN_VALUE
        var leftSum =  0
        var crossRightMax =  Int.MIN_VALUE
        var rightSum =  0
        for (i in mid downTo left) {
            leftSum += nums[i]
            crossLeftMax = maxOf(crossLeftMax, leftSum)
        }
        for (j in mid + 1..right) {
            rightSum += nums[j]
            crossRightMax = maxOf(crossRightMax, rightSum)
        }
        _crossMax = crossLeftMax + crossRightMax
        return maxOf(_crossMax, _leftMax, _rightMax)
    }

}

fun main() {
    // 测试用例
    val testCase = intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)
    // 预期: 6
    // 要求: 用分治法实现
    val handle =  DivideMaxSubArray()::maxSubArray
    println(handle(testCase))
}
