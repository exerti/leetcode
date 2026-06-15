// 二叉树中的最大路径和 (LeetCode 124)
// 核心：DFS 后序遍历，同时维护两个值：
//   1. maxSum — 全局最大路径和（可同时取左右子树）
//   2. 返回值  — 当前节点向上提供的最大贡献（只能选一边，因为不能分叉）

private var maxSum = Int.MIN_VALUE  // 全局最大值，初始化为最小负数（处理全负数树）

fun maxPathSum(root: TreeNode?): Int {
    // 1. 重置全局值（防止上次调用污染）
    maxSum = Int.MIN_VALUE
    // 2. 启动 DFS
    dfs(root)
    return maxSum
}

// 返回值：以 node 为起点，向父节点方向能提供的最大路径和（只能走一边）
private fun dfs(node: TreeNode?): Int {
    // 空节点贡献为 0，不影响上层计算
    if (node == null) return 0

    // 3. 后序遍历：先递归求左右子树的贡献
    //    与 0 比较：如果子树贡献为负数，直接舍弃（不走该子树）
    val leftGain = maxOf(dfs(node.left), 0)
    val rightGain = maxOf(dfs(node.right), 0)

    // 4. 以当前节点为「拐点」的完整路径和（可同时取左右子树）
    //    更新全局最大值
    maxSum = maxOf(maxSum, node.`val` + leftGain + rightGain)

    // 5. 向父节点返回：当前节点值 + 左右子树中较大的贡献
    //    只能选一边，因为路径往上走不能分叉
    return node.`val` + maxOf(leftGain, rightGain)
}