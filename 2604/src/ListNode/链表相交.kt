package ListNode

fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
    var pA = headA
    var pB = headB

    // 核心：你走我的路，我走你的路，相遇就是交点
    while (pA != pB) {
        pA = if (pA == null) headB else pA.next
        pB = if (pB == null) headA else pB.next
    }

    return pA // 相交则返回节点，不相交则返回 null
}