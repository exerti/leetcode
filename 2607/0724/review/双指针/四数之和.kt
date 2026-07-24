package review

/**
 * ⚠️ 上次失误:
 * 1. 忘记 nums.sort()，双指针必须基于有序数组
 * 2. i<size-3 无法防止 nums[i-1] 越界，应为 i>0 判断
 * 3. 同样存索引非值 intArrayOf(i,j,left,right)
 * 4. sum == 0 硬编码，四数之和 target 由参数传入
 * 📈 掌握度: 1/5 | 累计错误: 4次 | 间隔: 1天
 */

class FourSum {
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        TODO()
    }
}

fun main() {
    // 测试用例
    val nums = intArrayOf(1, 0, -1, 0, -2, 2)
    val target = 0
    // 预期: [[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]
}
