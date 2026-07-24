package review

/**
 * ⚠️ 上次失误:
 * 1. forEach 用了 Java 语法 (head->{})，Kotlin 应为 { head -> }
 * 2. peek() 只读不弹出，while 循环永远不结束
 * 3. cur 指针不前进（缺 cur=node），只连接了第一个节点
 * 📈 掌握度: 2/5 | 累计错误: 3次 | 间隔: 3天 | 下次复习: 07-26
 */

import java.util.*
import kotlin.math.min

class MergeKSortedLists {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val minHeap = PriorityQueue<ListNode>(compareBy { it.`val` })
        lists.forEach { head ->
            if (head != null) {
                minHeap.offer(head)
            }
        }
        val dummy = ListNode(0)
        var cur = dummy
        while(minHeap.isNotEmpty()){
            val node = minHeap.poll()
            cur.next=node
            cur = node
            if(node.next!=null) {
                minHeap.offer(node.next)
            }
        }

        return dummy.next
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun main() {
    // 测试用例
    // [[1,4,5],[1,3,4],[2,6]] → [1,1,2,3,4,4,5,6]
}
