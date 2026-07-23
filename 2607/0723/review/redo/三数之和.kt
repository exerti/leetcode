package review.redo

/**
 * ⚠️ 上次失误：
 * 1. when 表达式缺少 {} → Kotlin 必须用 when { ... }
 * 2. 存的是索引而非值 → listOf(nums[i], nums[left], nums[right])
 * 3. 未跳过重复 i → if (i > 0 && nums[i] == nums[i - 1]) continue
 * 📈 掌握度: 1/5 | 累计错误: 3次 | 间隔: 1天
 */
class ThreeSum {
    fun threeSum(nums: IntArray): List<List<Int>> {
        // TODO
    }
}
