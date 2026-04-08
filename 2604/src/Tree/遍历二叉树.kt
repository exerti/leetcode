package Tree

import java.lang.reflect.Array
import java.util.LinkedList
import java.util.Stack

class 遍历二叉树 {
}

fun preorderTraversal(root: TreeNode?): List<Int> {
    val res = mutableListOf<Int>()
    fun dfs(node: TreeNode?) {
        if (node == null) return
        res.add(node.`val`)
        dfs(node.left)
        dfs(node.right)
    }
    dfs(root)
    return res
}

// 3. 中序遍历 左 → 根 → 右
fun inorderTraversal(root: TreeNode?): List<Int> {
    val res = mutableListOf<Int>()
    fun dfs(node: TreeNode?) {
        if (node == null) return
        dfs(node.left)
        res.add(node.`val`)
        dfs(node.right)
    }
    dfs(root)
    return res
}

// 4. 后序遍历 左 → 右 → 根
fun postorderTraversal(root: TreeNode?): List<Int> {
    val res = mutableListOf<Int>()
    fun dfs(node: TreeNode?) {
        if (node == null) return
        dfs(node.left)
        dfs(node.right)
        res.add(node.`val`)
    }
    dfs(root)
    return res
}


//null = 标记位：表示 “这个节点接下来要被访问（加入结果集）” 标记法
fun preorderTraversalUseStack(root: TreeNode?): List<Int> {
    val res = mutableListOf<Int>()
    if(root == null) return res
    val stack = Stack<TreeNode>()
    stack.push(root)
    while(!stack.empty()) {
        val curr = stack.pop()
        if(curr==null) res.add(stack.pop().`val`)
        else{
            //
            if(curr.right!=null) stack.push(curr.right)
            if(curr.left!=null) stack.push(curr.left)
            stack.push(curr)
            stack.push(null)
        }
    }
    return res
}


fun inorderTraversalUseStack(root: TreeNode?): List<Int> {
    val res = mutableListOf<Int>()
    if(root == null) return res
    val stack = Stack<TreeNode>()
    stack.push(root)
    while(!stack.empty()) {
        val curr = stack.pop()
        if(curr==null) res.add(stack.pop().`val`)
        else{
            //
            if(curr.right!=null) stack.push(curr.right)
            stack.push(curr)
            stack.push(null)
            if(curr.left!=null) stack.push(curr.left)

        }
    }
    return res
}



fun postorderTraversalUseStack(root: TreeNode?): List<Int> {
    val res = mutableListOf<Int>()
    if (root == null) return res
    val stack = Stack<TreeNode?>()
    stack.push(root)

    while (!stack.empty()) {
        val curr = stack.pop()
        if (curr == null) {
            // 标记位：下一个节点要加入结果
            res.add(stack.pop()!!.`val`)
        } else {
            // 后序：左 右 根 → 入栈反过来：根、null、右、左
            stack.push(curr)
            stack.push(null) // 标记：根待访问

            if (curr.right != null) stack.push(curr.right)
            if (curr.left != null) stack.push(curr.left)
        }
    }
    return res
}


