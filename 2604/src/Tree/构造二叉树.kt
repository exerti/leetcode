package Tree

//从前序与中序遍历序列构造二叉树
fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
 return buildTree(preorder, inorder)
}

//从中序与后序遍历序列构造二叉树

fun buildTree2(inorder: IntArray, postorder: IntArray): TreeNode? {
    return buildTree(postorder, inorder)
}