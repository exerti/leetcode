package ListNode

// 递归版：自顶向下归并排序（O(n log n)，递归栈 O(log n)）
fun sortList(head: ListNode?): ListNode? {
    if (head == null || head.next == null) return head

    var slow = head
    var fast = head
    var preMid: ListNode? = null

    while (fast != null && fast.next != null) {
        preMid = slow
        slow = slow?.next
        fast = fast.next?.next
    }

    preMid?.next = null

    val left = sortList(head)
    val right = sortList(slow)
    return mergeTwo(left, right)
}

// 迭代版：自底向上归并排序（O(n log n)，额外空间 O(1)）
fun sortListIterative(head: ListNode?): ListNode? {
    if (head == null || head.next == null) return head

    val n = getLength(head)
    val dummy = ListNode(-1)
    dummy.next = head

    var size = 1
    while (size < n) {
        var prev: ListNode = dummy
        var cur = dummy.next
        while (cur != null) {
            val left = cur
            val right = split(left, size)
            cur = split(right, size)

            val (mergedHead, mergedTail) = mergeWithTail(left, right)
            prev.next = mergedHead
            prev = mergedTail
        }

        size = size shl 1
    }

    return dummy.next
}

fun getLength(head: ListNode?): Int {
    var cur = head
    var len = 0
    while (cur != null) {
        len++
        cur = cur.next
    }
    return len
}

fun split(head: ListNode?, size: Int): ListNode? {
    var cur = head
    for (i in 1 until size) {
        if (cur?.next == null) return null
        cur = cur.next
    }

    val second = cur?.next
    cur?.next = null
    return second
}

fun mergeTwo(left: ListNode?, right: ListNode?): ListNode? {
    val dummy = ListNode(-1)
    var tail: ListNode = dummy
    var l = left
    var r = right

    while (l != null && r != null) {
        if (l.`val` <= r.`val`) {
            tail.next = l
            l = l.next
        } else {
            tail.next = r
            r = r.next
        }
        tail = tail.next!!
    }

    tail.next = l ?: r
    return dummy.next
}

fun mergeWithTail(left: ListNode?, right: ListNode?): Pair<ListNode?, ListNode> {
    val dummy = ListNode(-1)
    var tail: ListNode = dummy
    var l = left
    var r = right

    while (l != null && r != null) {
        if (l.`val` <= r.`val`) {
            tail.next = l
            l = l.next
        } else {
            tail.next = r
            r = r.next
        }
        tail = tail.next!!
    }

    tail.next = l ?: r
    while (tail.next != null) {
        tail = tail.next!!
    }

    return Pair(dummy.next, tail)
}

fun buildList(values: IntArray): ListNode? {
    val dummy = ListNode(-1)
    var tail: ListNode = dummy
    for (v in values) {
        tail.next = ListNode(v)
        tail = tail.next!!
    }
    return dummy.next
}

fun toList(head: ListNode?): List<Int> {
    val result = mutableListOf<Int>()
    var cur = head
    while (cur != null) {
        result.add(cur.`val`)
        cur = cur.next
    }
    return result
}

fun main() {
    val input = intArrayOf(4, 2, 1, 3, 5, 0)

    val recursiveInput = buildList(input)
    val iterativeInput = buildList(input)

    val recursiveSorted = sortList(recursiveInput)
    val iterativeSorted = sortListIterative(iterativeInput)

    val recursiveResult = toList(recursiveSorted)
    val iterativeResult = toList(iterativeSorted)

    println("递归结果: $recursiveResult")
    println("迭代结果: $iterativeResult")
    println("结果一致: ${recursiveResult == iterativeResult}")
}

