package review


class  Node(var `val`: Int ){
    var next: Node? = null
}
class  Solution{

    fun  reverseList(head: Node?): Node?{
        if(head==null){
            return  head
        }
        var cur : Node? = head
        var pre : Node? = null
        while (cur!=null){
            var node = cur.next
            cur.next= pre
            pre= cur
            cur= node
        }
        return  pre
    }
}