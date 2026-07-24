package review

/**
 * ⚠️ 上次失误:
 * 1. PriorityQueue 拼写错误
 * 2. compareBy{it} 按数值排序，应按频率 freqMap[it] 排序
 * 3. 遍历 nums 而非 freqMap.keys，重复元素多次入堆
 * 4. 返回 List<Int>，函数签名要求 IntArray，缺 toIntArray()
 * 📈 掌握度: 1/5 | 累计错误: 4次 | 间隔: 1天
 */

import java.util.*

class Solution {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        TODO()
    }
}

fun main() {
    // 测试用例
    val nums = intArrayOf(1, 1, 1, 2, 2, 3)
    val k = 2
    // 预期: [1, 2]
}
