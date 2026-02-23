import java.util.*;

// 二叉树节点类
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class RecoverSolution {
    /**
     * 简洁递归法：前序+中序构建二叉树
     * 核心思路：
     * 1. 前序第一个元素 = 根节点
     * 2. 在中序中找到根的位置（indexOf）
     * 3. 递归构建左右子树
     *
     * 时间复杂度: O(n²) - indexOf是O(n)
     * 空间复杂度: O(n) - 递归栈深度
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (inorder.length == 0) return null;

        // 前序第一个元素就是根
        TreeNode root = new TreeNode(preorder[0]);

        // 在中序中找到根的位置
        int mid = indexOf(inorder, preorder[0]);

        // 递归构建左子树
        // preorder.slice(1, mid + 1) → 跳过根，取左子树部分
        // inorder.slice(0, mid) → 左子树的中序部分
        root.left = buildTree(
            Arrays.copyOfRange(preorder, 1, mid + 1),
            Arrays.copyOfRange(inorder, 0, mid)
        );

        // 递归构建右子树
        // preorder.slice(mid + 1) → 右子树的前序部分
        // inorder.slice(mid + 1) → 右子树的中序部分
        root.right = buildTree(
            Arrays.copyOfRange(preorder, mid + 1, preorder.length),
            Arrays.copyOfRange(inorder, mid + 1, inorder.length)
        );

        return root;
    }

    /**
     * 查找元素在数组中的索引
     */
    private int indexOf(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }
}

// 工具类 - 打印和验证
class RecoverTreeUtils {
    // 前序遍历
    public static List<Integer> preorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    // 中序遍历
    public static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    // 打印树结构
    public static void printTree(TreeNode root) {
        System.out.println("构建的树结构（前序）:");
        printTreeHelper(root, "", true);
    }

    private static void printTreeHelper(TreeNode node, String prefix, boolean isTail) {
        if (node == null) return;

        System.out.print(prefix);
        System.out.print(isTail ? "└── " : "├── ");
        System.out.println(node.val);

        String childPrefix = prefix + (isTail ? "    " : "│   ");
        printTreeHelper(node.left, childPrefix, node.right == null);
        printTreeHelper(node.right, childPrefix, true);
    }
}

public class RecoverMain {
    public static void main(String[] args) {
        RecoverSolution solution = new RecoverSolution();

        // ========== 测试用例1 ==========
        // 树:
        //       3
        //      / \
        //     9   20
        //        /  \
        //       15   7
        System.out.println("========== 测试用例1 ==========");
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};

        System.out.println("前序: " + Arrays.toString(preorder1));
        System.out.println("中序: " + Arrays.toString(inorder1));
        System.out.println();

        TreeNode root1 = solution.buildTree(preorder1, inorder1);
        RecoverTreeUtils.printTree(root1);
        System.out.println();

        List<Integer> verifyPre1 = RecoverTreeUtils.preorder(root1);
        List<Integer> verifyIn1 = RecoverTreeUtils.inorder(root1);
        System.out.println("验证 - 前序: " + verifyPre1);
        System.out.println("验证 - 中序: " + verifyIn1);
        System.out.println();

        // ========== 测试用例2 ==========
        System.out.println("========== 测试用例2 ==========");
        int[] preorder2 = {-1};
        int[] inorder2 = {-1};

        System.out.println("前序: " + Arrays.toString(preorder2));
        System.out.println("中序: " + Arrays.toString(inorder2));
        System.out.println();

        TreeNode root2 = solution.buildTree(preorder2, inorder2);
        RecoverTreeUtils.printTree(root2);
        System.out.println();

        List<Integer> verifyPre2 = RecoverTreeUtils.preorder(root2);
        List<Integer> verifyIn2 = RecoverTreeUtils.inorder(root2);
        System.out.println("验证 - 前序: " + verifyPre2);
        System.out.println("验证 - 中序: " + verifyIn2);
        System.out.println();

        // ========== 测试用例3 ==========
        // 树:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        System.out.println("========== 测试用例3 ==========");
        int[] preorder3 = {1, 2, 4, 5, 3};
        int[] inorder3 = {4, 2, 5, 1, 3};

        System.out.println("前序: " + Arrays.toString(preorder3));
        System.out.println("中序: " + Arrays.toString(inorder3));
        System.out.println();

        TreeNode root3 = solution.buildTree(preorder3, inorder3);
        RecoverTreeUtils.printTree(root3);
        System.out.println();

        List<Integer> verifyPre3 = RecoverTreeUtils.preorder(root3);
        List<Integer> verifyIn3 = RecoverTreeUtils.inorder(root3);
        System.out.println("验证 - 前序: " + verifyPre3);
        System.out.println("验证 - 中序: " + verifyIn3);
    }
}
