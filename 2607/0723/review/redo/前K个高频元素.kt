package review.redo

import java.util.PriorityQueue

/**
 * ⚠️ 上次失误：
 * 1. PriorityQueue 拼写（不是 Pority）
 * 2. 比较器应排频率 → compareBy { freqMap[it] } 不是 compareBy { it }
 * 3. 遍历 freqMap.keys 不是 nums（避免重复入堆）
 * 4. 返回 IntArray → toList().toIntArray()
 * 📈 掌握度: 1/5 | 累计错误: 4次 | 间隔: 1天
 */
class TopKFrequent {
    fun topFreq(nums: IntArray, k: Int): IntArray {
        // TODO
    }
}
