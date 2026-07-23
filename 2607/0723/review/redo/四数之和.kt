package review.redo

/**
 * ⚠️ 上次失误：
 * 1. 忘记 nums.sort() → 双指针必须先排序
 * 2. 去重条件越界 → if (i > 0 && ...) 不是 if (i < size-3 && ...)
 * 3. j 去重 → if (j > i + 1 && nums[j] == nums[j - 1]) continue
 * 4. 存索引非值 → listOf(nums[i], nums[j], nums[left], nums[right])
 * 5. target 参数 → sum == target 不是 sum == 0
 * 📈 掌握度: 1/5 | 累计错误: 4次 | 间隔: 1天
 */
class FourSum {
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        // TODO
    }
}
