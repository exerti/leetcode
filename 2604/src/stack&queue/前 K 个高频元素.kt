package `stack&queue`

import java.util.PriorityQueue


/**
 * 示例 1：
 *
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2：
 *
 * 输入: nums = [1], k = 1
 * 输出: [1]
 */
fun topKFrequent(nums: IntArray, k: Int): IntArray {

    //hashmap 统计频次
    var hashmap = HashMap<Int, Int>()
    for (num in nums) {
        hashmap[num] = hashmap.getOrDefault(num, 0) + 1
    }

    // 小堆维护k

    val heap = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })

    for ((num, counts) in hashmap) {
        var size = heap.size
        if (size < k) {
            heap.offer(Pair(num, counts))
        } else {
            if (counts > heap.peek().second) {   // 频率 > 堆顶频率
                heap.poll()
                heap.offer(num to counts)
            }
        }
    }
    return heap.map { it.first }.toIntArray()

}


fun main() {


    var testcases = mapOf(
        intArrayOf(
            1, 1, 1, 2, 2, 3
        ) to 2,
        intArrayOf(
            1, 1, 1, 2, 2, 3
        ) to 2
    )

    for (testCase in testcases.entries) {

        print(

            topKFrequent(testCase.key, testCase.value).toList()
        )
    }
}
