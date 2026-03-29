package ListNode

class 设计链表 {
}


class MyLinkedList() {
    var size = 0  // 链表长度
    var head: ListNode? = null  // 头节点

    // 获取第 index 个节点的值
    fun get(index: Int): Int {
        if (index < 0 || index >= size) return -1
        var cur = head
        for (i in 0 until index) {
            cur = cur?.next
        }
        return cur?.`val` ?: -1
    }

    // 头部添加节点
    fun addAtHead(`val`: Int) {
        val newNode = ListNode(`val`)
        newNode.next = head
        head = newNode
        size++
    }

    // 尾部添加节点
    fun addAtTail(`val`: Int) {
        if (head == null) {
            addAtHead(`val`)
            return
        }
        var cur = head
        while (cur?.next != null) {
            cur = cur.next
        }
        cur?.next = ListNode(`val`)
        size++
    }

    // 指定 index 添加节点
    fun addAtIndex(index: Int, `val`: Int) {
        if (index < 0 || index > size) return
        if (index == 0) {
            addAtHead(`val`)
            return
        }
        var cur = head
        for (i in 0 until index - 1) {
            cur = cur?.next
        }
        val newNode = ListNode(`val`)
        newNode.next = cur?.next
        cur?.next = newNode
        size++
    }

    // 删除指定 index 节点
    fun deleteAtIndex(index: Int) {
        if (index < 0 || index >= size) return
        if (index == 0) {
            head = head?.next
            size--
            return
        }
        var cur = head
        for (i in 0 until index - 1) {
            cur = cur?.next
        }
        cur?.next = cur?.next?.next
        size--
    }
}
