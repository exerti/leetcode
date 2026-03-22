package linkList

/**
 * 链表节点定义
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

/**
 * 链表基础操作类
 */
class LinkedList {
    var head: ListNode? = null

    // 在头部插入节点
    fun insertAtHead(value: Int) {
        val newNode = ListNode(value)
        newNode.next = head
        head = newNode
    }

    // 在尾部插入节点
    fun insertAtTail(value: Int) {
        val newNode = ListNode(value)
        if (head == null) {
            head = newNode
            return
        }
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        current?.next = newNode
    }

    // 在指定位置插入节点
    fun insertAt(position: Int, value: Int): Boolean {
        if (position < 0) return false
        if (position == 0) {
            insertAtHead(value)
            return true
        }
        val newNode = ListNode(value)
        var current = head
        var index = 0
        while (current != null && index < position - 1) {
            current = current.next
            index++
        }
        if (current == null) return false
        newNode.next = current.next
        current.next = newNode
        return true
    }

    // 删除指定值的节点
    fun deleteByValue(value: Int): Boolean {
        if (head == null) return false
        if (head?.`val` == value) {
            head = head?.next
            return true
        }
        var current = head
        while (current?.next != null) {
            if (current.next?.`val` == value) {
                current.next = current.next?.next
                return true
            }
            current = current.next
        }
        return false
    }

    // 删除指定位置的节点
    fun deleteAt(position: Int): Boolean {
        if (position < 0 || head == null) return false
        if (position == 0) {
            head = head?.next
            return true
        }
        var current = head
        var index = 0
        while (current != null && index < position - 1) {
            current = current.next
            index++
        }
        if (current?.next == null) return false
        current.next = current.next?.next
        return true
    }

    // 查找节点
    fun search(value: Int): ListNode? {
        var current = head
        while (current != null) {
            if (current.`val` == value) return current
            current = current.next
        }
        return null
    }

    // 获取指定位置的节点
    fun getAt(position: Int): ListNode? {
        if (position < 0) return null
        var current = head
        var index = 0
        while (current != null && index < position) {
            current = current.next
            index++
        }
        return current
    }

    // 获取链表长度
    fun size(): Int {
        var count = 0
        var current = head
        while (current != null) {
            count++
            current = current.next
        }
        return count
    }

    // 反转链表
    fun reverse() {
        var prev : ListNode? = null
        var current = head
        while (current != null) {
            var next = current.next
            current.next = prev
            prev=current
            current = next
        }
        head= current;
    }

    // 检查是否为空
    fun isEmpty(): Boolean = head == null

    // 清空链表
    fun clear() {
        head = null
    }

    // 打印链表
    fun print() {
        if (head == null) {
            println("链表为空")
            return
        }
        var current = head
        val result = StringBuilder()
        while (current != null) {
            result.append("${current.`val`}")
            if (current.next != null) result.append(" -> ")
            current = current.next
        }
        println(result.toString())
    }

    // 转换为列表
    fun toList(): List<Int> {
        val list = mutableListOf<Int>()
        var current = head
        while (current != null) {
            list.add(current.`val`)
            current = current.next
        }
        return list
    }

    //移除链表元素
    fun removeElements(head: ListNode?, `val`: Int): ListNode? {
        var current =  head;
        while(current.next!=null){
            if(current.`val` ==`val`){
                    current.next = current->next->next;
            }
        }
        return head;

    }
}

// 使用示例
fun main() {
    val list = LinkedList()

    // 插入操作
    list.insertAtTail(1)
    list.insertAtTail(2)
    list.insertAtTail(3)
    list.insertAtHead(0)
    list.print() // 0 -> 1 -> 2 -> 3

    // 查找操作
    val node = list.search(2)
    println("找到节点: ${node?.`val`}") // 找到节点: 2

    // 获取长度
    println("链表长度: ${list.size()}") // 链表长度: 4

    // 删除操作
    list.deleteByValue(2)
    list.print() // 0 -> 1 -> 3

    // 反转链表
    list.reverse()
    list.print() // 3 -> 1 -> 0
}
