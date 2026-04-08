package Tree

import ListNode.ListNode
import java.util.LinkedList

class 数组转为二叉树 {
}


///将层序遍历的数组转换为二叉树
fun convertArrayToTree( arr: Array<Int?> ): TreeNode? {
    if(arr.isEmpty()||arr[0]==null) return null
    val root = TreeNode(arr[0]!!)
    val queue = ArrayDeque<TreeNode>()
    queue.add(root)
    var index = 1
    while(queue.isNotEmpty()&&index<arr.size){
        var currentNode = queue.removeFirst();

        if(arr[index]!=null){
            currentNode.left = TreeNode(arr[index]!!)
            queue.add(currentNode.left!!)
        }
        index++

        if(index<arr.size&&arr[index]!=null){
            currentNode.right = TreeNode(arr[index]!!)
            queue.add(currentNode.right!!)
        }
        index++

    }
    return root
}