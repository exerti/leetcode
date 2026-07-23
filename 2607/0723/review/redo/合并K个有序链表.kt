package review.redo

import java.util.PriorityQueue

class ListNode(var value: Int) {
    var next: ListNode? = null
}

/**
 * ⚠️ 上次失误：
 * 1. Kotlin lambda → lists.forEach { head -> ... } 不是 lists.forEach(head -> {})
 * 2. poll() 不是 peek() → peek 只读不弹出会死循环
 * 3. cur 指针必须前进 → cur = node
 * 📈 掌握度: 2/5 | 累计错误: 3次 | 间隔: 3天
 */
class MergeKLists {
    fun mergeKListNode(lists: Array<ListNode?>): ListNode? {
        TODO()
    }
}
