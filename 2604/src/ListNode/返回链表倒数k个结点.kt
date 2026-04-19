package ListNode

fun returnK(root:ListNode,k: Int):ListNode?{
  if(root==null) return root;
  var fast: ListNode? = root
  var slow : ListNode?= root
  var step = k
 while (step>0&&fast!=null){
   fast = fast.next
   step--
 }
  while (fast!=null){
    fast = fast.next
    slow = slow?.next
  }

  return  slow

}
// 这里修复：去掉多余的 fun
fun main(args: Array<String>) {
  // 构建测试链表 1 -> 2 -> 3 -> 4 -> 5
  val head = ListNode(1)
  head.next = ListNode(2)
  head.next?.next = ListNode(3)
  head.next?.next?.next = ListNode(4)
  head.next?.next?.next?.next = ListNode(5)

  // 测试：倒数第 2 个节点 → 应该是 4
  val resultNode = returnK(head, 2)
  println("倒数第2个节点的值：${resultNode?.`val`}") // 输出 4
}
