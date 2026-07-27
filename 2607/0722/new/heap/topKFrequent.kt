package new.heap

import java.util.PriorityQueue
import kotlin.math.min


class Solution3 {

    fun topKFrequent(nums:IntArray,k:Int):IntArray{
        val freqMap = nums.toList().groupingBy { it }.eachCount()
        val minHeap = PriorityQueue<Int>( compareBy { freqMap[it]!! })

        for( num in freqMap.keys){
            minHeap.add(num)
            if(minHeap.size > k){
                minHeap.poll()
            }
        }

        return minHeap.toIntArray()
    }
}