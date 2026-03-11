data class TreeNode(
    var `val`: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

class Solution {
    // 前序遍历
    fun preorder(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        if (root == null) return result
        result.add(root.`val`)
        result.addAll(preorder(root.left))
        result.addAll(preorder(root.right))
        return result
    }

    // 中序遍历
    fun inorder(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        if (root == null) return result
        result.addAll(inorder(root.left))
        result.add(root.`val`)
        result.addAll(inorder(root.right))
        return result
    }

    // 后序遍历
    fun postorder(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        if (root == null) return result
        result.addAll(postorder(root.left))
        result.addAll(postorder(root.right))
        result.add(root.`val`)
        return result
    }

    // 树的高度
    fun height(root: TreeNode?): Int {
        if (root == null) return 0
        return 1 + maxOf(height(root.left), height(root.right))
    }
}

fun main() {
    val solution = Solution()

    // 构建测试树
    //       1
    //      / \
    //     2   3
    //    / \
    //   4   5
    val root = TreeNode(1).apply {
        left = TreeNode(2).apply {
            left = TreeNode(4)
            right = TreeNode(5)
        }
        right = TreeNode(3)
    }

    println("前序遍历: ${solution.preorder(root)}")
    println("中序遍历: ${solution.inorder(root)}")
    println("后序遍历: ${solution.postorder(root)}")
    println("树的高度: ${solution.height(root)}")
}
