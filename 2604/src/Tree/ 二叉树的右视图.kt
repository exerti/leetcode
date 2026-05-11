package Tree

/**
 * 给定一个二叉树的根节点 root,想象自己站在它的右侧,
 * 按照从顶部到底部的顺序,返回从右侧所能看到的节点值。
 *
 * 思路:BFS 层序遍历,每层最后一个出队的节点就是右视图看到的那个。
 */
fun rightSideView(root: TreeNode?): List<Int> {
    val ans = mutableListOf<Int>()
    if (root == null) return ans

    val queue = ArrayDeque<TreeNode>()   // 非空泛型,避免 removeFirst 后还得判空
    queue.addLast(root)

    while (queue.isNotEmpty()) {
        val size = queue.size             // 先"拍照",锁定这一层的节点数
        for (i in 0 until size) {
            val node = queue.removeFirst()
            if (i == size - 1) ans.add(node.`val`)         // 这层最后一个出队
            node.left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
    }
    return ans
}

/**
 * DFS 右优先:前序 + 先右后左。
 * 第一次到达某深度的节点,一定是该层最右的节点。
 */
fun rightSideViewDfs(root: TreeNode?): List<Int> {
    val ans = mutableListOf<Int>()
    dfsRight(root, 0, ans)
    return ans
}

private fun dfsRight(node: TreeNode?, depth: Int, ans: MutableList<Int>) {
    if (node == null) return
    if (depth == ans.size) ans.add(node.`val`)   // 本深度第一次被访问
    dfsRight(node.right, depth + 1, ans)         // 先右
    dfsRight(node.left, depth + 1, ans)
}

/**
 * 按层序构造二叉树(null 表示空节点),仅 testCase 用。
 * 例:buildTreeLevel(1, 2, 3, null, 5, null, 4) 构造:
 *        1
 *       / \
 *      2   3
 *       \   \
 *        5   4
 */
private fun buildTreeLevel(vararg values: Int?): TreeNode? {
    if (values.isEmpty() || values[0] == null) return null
    val root = TreeNode(values[0]!!)
    val queue = ArrayDeque<TreeNode>()
    queue.addLast(root)
    var i = 1
    while (queue.isNotEmpty() && i < values.size) {
        val node = queue.removeFirst()
        if (i < values.size) {
            values[i]?.let {
                node.left = TreeNode(it)
                queue.addLast(node.left!!)
            }
            i++
        }
        if (i < values.size) {
            values[i]?.let {
                node.right = TreeNode(it)
                queue.addLast(node.right!!)
            }
            i++
        }
    }
    return root
}

fun main() {
    data class Case(val tree: Array<Int?>, val expected: List<Int>)
    val cases = listOf(
        Case(arrayOf(1, 2, 3, null, 5, null, 4), listOf(1, 3, 4)),   // LC 199 标准
        Case(arrayOf(1, null, 3),                listOf(1, 3)),
        Case(arrayOf(),                          emptyList()),
        Case(arrayOf(1, 2, null, 4),             listOf(1, 2, 4)),   // 只有左链,也要看见
        Case(arrayOf(1, 2, 3),                   listOf(1, 3)),
    )

    for ((idx, c) in cases.withIndex()) {
        val root = buildTreeLevel(*c.tree)
        val bfs = rightSideView(root)
        val dfs = rightSideViewDfs(root)
        val ok = bfs == c.expected && dfs == c.expected
        println(
            "case#$idx 输入=${c.tree.toList()} " +
            "期望=${c.expected} BFS=$bfs DFS=$dfs 通过=$ok"
        )
    }
}
