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

class TopKFrequentHeap {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val size= nums.size
        val freq = nums.toList().groupingBy{it}.eachCount()
        val minHeap = PriorityQueue<Int>(compareBy{freq[it]})
        for(num in freq.keys){
            minHeap.offer(num )
            if(minHeap.size>k){
                minHeap.poll()
            }

        }
        return minHeap.toIntArray()
    }
}

fun main() {
    // 测试用例
    val nums = intArrayOf(1, 1, 1, 2, 2, 3)
    val k = 2
    // 预期: [1, 2]
}
