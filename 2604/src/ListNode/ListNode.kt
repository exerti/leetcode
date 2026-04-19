package ListNode

// 🔥 修复：正确的 ListNode 类（Kotlin 写法）
class ListNode(var `val`: Int) {
    var next: ListNode? = null

}


fun  printListNode(node: ListNode?) {
    var root: ListNode? = node
    if (root != null) {
        while (root != null) {
            print("${root.`val`} -> ")
            root = root.next
        }
    }
    println()
}



fun removeElements(head: ListNode?, `val`: Int): ListNode? {
    val dummy = ListNode(0) // 虚拟头节点
    dummy.next = head
    var current = dummy

    while (current.next != null) {
        if (current.next!!.`val` == `val`) {
            // 找到目标，删除节点
            current.next = current.next!!.next
        } else {
            // 没找到，继续走
            current = current.next!!
        }
    }

    return dummy.next
}

fun main() {
    // 测试用例
    // 1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6
    val head = ListNode(1)
    head.next = ListNode(2)
    head.next!!.next = ListNode(6)
    head.next!!.next!!.next = ListNode(3)
    head.next!!.next!!.next!!.next = ListNode(4)
    head.next!!.next!!.next!!.next!!.next = ListNode(5)
    head.next!!.next!!.next!!.next!!.next!!.next = ListNode(6)

    val result = removeElements(head, 6)

    // 打印结果
    var cur = result
    while (cur != null) {
        print("${cur.`val`} -> ")
        cur = cur.next
    }
    print("null")
}