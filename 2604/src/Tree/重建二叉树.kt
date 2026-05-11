package Tree

/**
 * 重建二叉树合集 + "为什么单序列不能重建"的讲解。
 *
 * 能唯一重建二叉树的输入组合:
 *   1) 前序 + 中序   (LC 105)   本文件 buildTreePreIn
 *   2) 后序 + 中序   (LC 106)   本文件 buildTreePostIn
 *   3) 层序 + 中序   思路同上:层序每次拿当前层的根,在中序里切左右
 *   4) 前序 + 后序   一般 *不唯一*;仅当二叉树是"满二叉树(每个节点 0 或 2 个孩子)"时才唯一
 *   5) 带 null 占位的前序字符串(LC 297 序列化)  用递归 / 队列直接反序列化
 *
 * ===== 为什么单独"中序"不能重建? =====
 * 中序只记录"左 -> 根 -> 右"的相对顺序,但没告诉你"哪个值是根"。
 * 仅凭中序 [1, 2, 3],下列多棵树都满足:
 *
 *       2            1              3              1
 *      / \            \            /                \
 *     1   3            2          2                  3
 *                       \        /                  /
 *                        3      1                  2
 *
 * 它们中序都是 [1,2,3],但形态完全不同。
 *
 * ===== 为什么单独"前序 / 后序"也不能? =====
 * 前序知道"根最先",但看不出"左子树到哪里为止" —— 没有边界信息。
 * 后序知道"根最后",同样缺少切分依据。
 *
 * 所以必须配合两种序列:
 *   - 一种提供根位置(前序 / 后序 / 层序)
 *   - 一种提供切分边界(中序)
 * 两者配合,递归"确定根 -> 切出左右区间 -> 递归建子树"。
 *
 * 本文件假设树里的值两两不同(LC 题惯例),这样可以用 HashMap O(1) 定位根。
 */

// 1) 前序 + 中序
fun buildTreePreIn(preorder: IntArray, inorder: IntArray): TreeNode? {
    val idx = HashMap<Int, Int>()
    for ((i, v) in inorder.withIndex()) idx[v] = i
    return helperPreIn(preorder, 0, preorder.size - 1, 0, inorder.size - 1, idx)
}

private fun helperPreIn(
    pre: IntArray, pl: Int, pr: Int,
    il: Int, ir: Int,
    idx: HashMap<Int, Int>,
): TreeNode? {
    if (pl > pr) return null
    val rootVal = pre[pl]                          // 前序第一个是根
    val root = TreeNode(rootVal)
    val mid = idx[rootVal]!!                       // 中序里根的位置
    val leftSize = mid - il                        // 左子树节点数
    root.left  = helperPreIn(pre, pl + 1, pl + leftSize, il, mid - 1, idx)
    root.right = helperPreIn(pre, pl + leftSize + 1, pr, mid + 1, ir, idx)
    return root
}

// 2) 后序 + 中序
fun buildTreePostIn(postorder: IntArray, inorder: IntArray): TreeNode? {
    val idx = HashMap<Int, Int>()
    for ((i, v) in inorder.withIndex()) idx[v] = i
    return helperPostIn(postorder, 0, postorder.size - 1, 0, inorder.size - 1, idx)
}

private fun helperPostIn(
    post: IntArray, pl: Int, pr: Int,
    il: Int, ir: Int,
    idx: HashMap<Int, Int>,
): TreeNode? {
    if (pl > pr) return null
    val rootVal = post[pr]                         // 后序最后一个是根
    val root = TreeNode(rootVal)
    val mid = idx[rootVal]!!
    val leftSize = mid - il
    root.left  = helperPostIn(post, pl, pl + leftSize - 1, il, mid - 1, idx)
    root.right = helperPostIn(post, pl + leftSize, pr - 1, mid + 1, ir, idx)
    return root
}

// 遍历辅助(供 main 对拍用)
private fun preorderSeq(root: TreeNode?, out: MutableList<Int>) {
    if (root == null) return
    out.add(root.`val`)
    preorderSeq(root.left, out); preorderSeq(root.right, out)
}
private fun inorderSeq(root: TreeNode?, out: MutableList<Int>) {
    if (root == null) return
    inorderSeq(root.left, out); out.add(root.`val`); inorderSeq(root.right, out)
}
private fun postorderSeq(root: TreeNode?, out: MutableList<Int>) {
    if (root == null) return
    postorderSeq(root.left, out); postorderSeq(root.right, out); out.add(root.`val`)
}

fun main() {
    // 构造目标树:
    //        3
    //       / \
    //      9   20
    //         /  \
    //        15   7
    val expected = TreeNode(3).apply {
        left = TreeNode(9)
        right = TreeNode(20).apply {
            left = TreeNode(15)
            right = TreeNode(7)
        }
    }

    val pre = mutableListOf<Int>().also { preorderSeq(expected, it) }.toIntArray()
    val ino = mutableListOf<Int>().also { inorderSeq(expected, it) }.toIntArray()
    val pos = mutableListOf<Int>().also { postorderSeq(expected, it) }.toIntArray()

    println("前序 = ${pre.toList()}")
    println("中序 = ${ino.toList()}")
    println("后序 = ${pos.toList()}")

    val t1 = buildTreePreIn(pre, ino)
    val t2 = buildTreePostIn(pos, ino)

    // 还原后重新跑三序遍历,看是否和原树一致 -> 一致即重建成功
    fun threeSeq(root: TreeNode?): Triple<List<Int>, List<Int>, List<Int>> {
        val a = mutableListOf<Int>(); preorderSeq(root, a)
        val b = mutableListOf<Int>(); inorderSeq(root, b)
        val c = mutableListOf<Int>(); postorderSeq(root, c)
        return Triple(a, b, c)
    }
    val (a1, b1, c1) = threeSeq(t1)
    val (a2, b2, c2) = threeSeq(t2)

    println("前+中重建 三序一致 = ${a1 == pre.toList() && b1 == ino.toList() && c1 == pos.toList()}")
    println("后+中重建 三序一致 = ${a2 == pre.toList() && b2 == ino.toList() && c2 == pos.toList()}")
}
