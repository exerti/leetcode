package Tree

import ListNode.ListNode
import java.util.LinkedList
import java.util.Queue


fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
    if(root == null) {
        return emptyList()
    }
    val queue = LinkedList<TreeNode>()
    val result = mutableListOf<List<Int>>()
    queue.add(root)
    var leftToRight = true
    while (!queue.isEmpty()) {
        val size = queue.size
        val level = mutableListOf<Int>()
        for (i in 0 until size) {
            val node = queue.poll()
            level.add(node.`val`)
            if (node.left  != null) queue.add(node.left!!)
            if (node.right != null) queue.add(node.right!!)

        }
        if (!leftToRight) {
            level.reverse()
        }
        result.add(level)
        leftToRight = !leftToRight
    }
    return result
}

fun main() {
    val root = TreeNode(3).apply {
        left = TreeNode(9)
        right = TreeNode(20).apply {
            left = TreeNode(15)
            right = TreeNode(7)
        }
    }
    val result = zigzagLevelOrder(root)
    println(result) // expected: [[3], [20, 9], [15, 7]]
}