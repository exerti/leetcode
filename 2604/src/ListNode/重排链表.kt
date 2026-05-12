   
   
    fun reorderList(head: ListNode?): Unit {
        if(head?.next==null) return 
        
        //快满指针找中点
        var slow = head
        var fast = head
        while(fast?.next!=null){
            slow=slow?.next
            fast = fast?.next
        }

        //reverse
        var prev:ListNode? = null
        var curr = slow
        while(curr!=null){
            var node= curr.next
            curr.next=prev
            prev=curr
            curr=node
        }

        var first = head
        var second =prev
        while(second?.next!=null){
            var temp1 =fist?.next
            var temp2= second?.next
            first?.next=second
            second?.next=temp1
            first=temp1
            second = temp2
        }
    }

