import ListNode.ListNode

fun  mergeTwo(l1: ListNode?, l2:ListNode?):ListNode?{
    val dummy = ListNode(0)
    var tail: ListNode = dummy
    var a = l1
    var b = l2
   while(a!=null&&b!=null){
      
      if(a.`val`<=b.`val`){
        tail.next=a
        a=a?.let{it.next}
      }else{
        tail.next=b
         b=b?.let{it.next}
      }
       tail = tail.next!!      
   }
   
   tail.next= a?:b

   return dummy.next
}