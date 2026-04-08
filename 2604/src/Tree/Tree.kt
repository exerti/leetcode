package Tree

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun main() {
    // 测试 1：普通数组
    val arr1 = arrayOf(1, 2, 3, 4, 5)
    val tree1 = convertArrayToTree(arr1 as Array<Int?>)
    println(tree1?.`val`) // 输出 1

    // 测试 2：带空节点的数组（推荐算法题使用）
    val arr2 = arrayOf<Int?>(1, 2, 3, null, 4)
    val tree2 = convertArrayToTree(arr2)
    println(tree2?.left?.right?.`val`) // 输出 4


    var list = inorderTraversal(tree2)
    println()
    println("inorderTraversal  tree2")

    for (i in list.indices) {
        print("${list[i]} ")
    }


     list = inorderTraversalUseStack(tree2)
    println()
    println("inorderTraversalUseStack  tree2")

    for (i in list.indices) {
        print("${list[i]} ")
    }


    var list2 = preorderTraversal(tree2)
    println()
    println("preorderTraversal  tree2")

    for (i in list2.indices) {
        print("${list2[i]} ")
    }

    list2 = preorderTraversalUseStack(tree2)
    println()
    println("preorderTraversalUseStack  tree2")

    for (i in list2.indices) {
        print("${list2[i]} ")
    }


    var list3 = postorderTraversal(tree2)
    println()
    println("preorderTraversal  tree2")
    for (i in list3.indices) {
        print("${list3[i]} ")
    }


     list3 = postorderTraversalUseStack(tree2)
    println()
    println("preorderTraversal  tree2")
    for (i in list3.indices) {
        print("${list3[i]} ")
    }


}