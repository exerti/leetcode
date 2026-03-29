/**
 * K 个一组翻转链表
 * LeetCode 25: Reverse Nodes in k-Group
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

/**
 * 方法一：递归法
 * 思路：
 * 1. 先检查剩余节点是否够 k 个
 * 2. 翻转前 k 个节点
 * 3. 递归处理后续节点
 * 4. 连接翻转后的头尾
 */
fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
    if (head == null || k == 1) return head

    // 第一步：检查是否有 k 个节点
    var cur = head
    var count = 0
    while (cur != null && count < k) {
        cur = cur.next
        count++
    }

    // 不足 k 个，直接返回
    if (count < k) return head

    // 第二步：翻转前 k 个节点
    var prev: ListNode? = null
    cur = head
    for (i in 0 until k) {
        val next = cur?.next
        cur?.next = prev
        prev = cur
        cur = next
    }

    // 第三步：递归处理剩余部分
    // prev 是新的头节点，head 是新的尾节点，cur 是下一组的开始
    head.next = reverseKGroup(cur, k)

    return prev  // 返回翻转后的头节点
}

/**
 * 方法二：迭代法（更高效）
 * 使用虚拟头节点简化边界处理
 */
fun reverseKGroupIterative(head: ListNode?, k: Int): ListNode? {
    if (head == null || k == 1) return head

    val dummy = ListNode(0)
    dummy.next = head
    var prevGroupEnd = dummy  // 上一组的尾节点

    while (true) {
        // 检查剩余节点是否够 k 个
        val groupStart = prevGroupEnd.next
        var kth = prevGroupEnd
        for (i in 0 until k) {
            kth = kth.next ?: return dummy.next  // 不足 k 个，返回结果
        }
        val nextGroupStart = kth.next

        // 翻转当前组的 k 个节点
        var prev = nextGroupStart
        var cur = groupStart
        while (cur != nextGroupStart) {
            val next = cur?.next
            cur?.next = prev
            prev = cur
            cur = next
        }

        // 连接上一组和当前组
        prevGroupEnd.next = kth  // kth 是翻转后的新头
        prevGroupEnd = groupStart!!  // groupStart 变成了尾节点
    }
}

/**
 * 辅助函数：翻转从 start 开始的 k 个节点
 * 返回翻转后的新头节点和新尾节点
 */
private fun reverseK(start: ListNode?, k: Int): Pair<ListNode?, ListNode?> {
    var prev: ListNode? = null
    var cur = start
    val tail = start  // 原来的头会变成尾

    for (i in 0 until k) {
        val next = cur?.next
        cur?.next = prev
        prev = cur
        cur = next
    }

    return Pair(prev, tail)  // 新头，新尾
}

// 测试代码
fun main() {
    // 构造链表: 1 -> 2 -> 3 -> 4 -> 5
    val head = ListNode(1).apply {
        next = ListNode(2).apply {
            next = ListNode(3).apply {
                next = ListNode(4).apply {
                    next = ListNode(5)
                }
            }
        }
    }

    println("原链表: ${printList(head)}")

    // 测试 k=2
    val result1 = reverseKGroup(head, 2)
    println("k=2 翻转后: ${printList(result1)}")  // 2->1->4->3->5

    // 重新构造链表测试 k=3
    val head2 = ListNode(1).apply {
        next = ListNode(2).apply {
            next = ListNode(3).apply {
                next = ListNode(4).apply {
                    next = ListNode(5)
                }
            }
        }
    }
    val result2 = reverseKGroupIterative(head2, 3)
    println("k=3 翻转后: ${printList(result2)}")  // 3->2->1->4->5
}

fun printList(head: ListNode?): String {
    val sb = StringBuilder()
    var cur = head
    while (cur != null) {
        sb.append(cur.`val`)
        if (cur.next != null) sb.append("->")
        cur = cur.next
    }
    return sb.toString()
}

/**
 * 图解过程（k=3）：
 *
 * 原链表: 1 -> 2 -> 3 -> 4 -> 5
 *
 * 第一组翻转 [1,2,3]:
 *   翻转前: 1 -> 2 -> 3 -> 4 -> 5
 *   翻转后: 3 -> 2 -> 1 -> 4 -> 5
 *                       ↑
 *                    递归处理
 *
 * 第二组 [4,5] 不足 3 个，保持原样
 *
 * 最终: 3 -> 2 -> 1 -> 4 -> 5
 */
