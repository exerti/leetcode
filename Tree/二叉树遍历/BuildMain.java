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

class BuildSolution {
    private Map<Integer, Integer> inorderMap; // 值到中序索引的映射（优化查找）

    // ========== 根据前序和中序构建二叉树 ==========

    /**
     * 核心思路：
     * 1. 前序第一个元素 = 根节点
     * 2. 在中序中找到根，左边是左子树，右边是右子树
     * 3. 递归构建左右子树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0) {
            return null;
        }

        // 优化：建立中序值到索引的映射
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildHelper(preorder, 0, preorder.length - 1,
                       inorder, 0, inorder.length - 1);
    }

    /**
     * 递归构建辅助方法
     * @param preorder  前序数组
     * @param preStart 前序起始索引
     * @param preEnd   前序结束索引
     * @param inorder   中序数组
     * @param inStart  中序起始索引
     * @param inEnd    中序结束索引
     */
    private TreeNode buildHelper(int[] preorder, int preStart, int preEnd,
                             int[] inorder, int inStart, int inEnd) {
        // 基础情况
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 前序第一个元素就是根
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // 在中序中找到根的位置（使用 HashMap 优化）
        int rootPosInInorder = inorderMap.get(rootVal);

        // 计算左子树的大小
        int leftSubtreeSize = rootPosInInorder - inStart;

        // 递归构建左子树
        root.left = buildHelper(preorder,
                             preStart + 1,              // 跳过根
                             preStart + leftSubtreeSize,  // 左子树在前序中的范围
                             inorder,
                             inStart,                   // 左子树在中序中的范围
                             rootPosInInorder - 1);

        // 递归构建右子树
        root.right = buildHelper(preorder,
                              preStart + leftSubtreeSize + 1, // 右子树在前序中的起始
                              preEnd,
                              inorder,
                              rootPosInInorder + 1,       // 右子树在中序中的起始
                              inEnd);

        return root;
    }
}

// 工具类 - 打印和验证
class BuildTreeUtils {
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

    // 打印树结构（简化版）
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

public class BuildMain {
    public static void main(String[] args) {
        BuildSolution solution = new BuildSolution();

        // ========== 测试用例1 ==========

        System.out.println("========== 测试用例1 ==========");
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};

        System.out.println("前序遍历: " + Arrays.toString(preorder1));
        System.out.println("中序遍历: " + Arrays.toString(inorder1));
        System.out.println();

        TreeNode root1 = solution.buildTree(preorder1, inorder1);
        BuildTreeUtils.printTree(root1);
        System.out.println();

        // 验证结果
        List<Integer> verifyPre1 = BuildTreeUtils.preorder(root1);
        List<Integer> verifyIn1 = BuildTreeUtils.inorder(root1);
        System.out.println("验证 - 前序: " + verifyPre1);
        System.out.println("验证 - 中序: " + verifyIn1);
        System.out.println();

        // ========== 测试用例2 ==========

        System.out.println("========== 测试用例2 ==========");
        int[] preorder2 = {-1};
        int[] inorder2 = {-1};

        System.out.println("前序遍历: " + Arrays.toString(preorder2));
        System.out.println("中序遍历: " + Arrays.toString(inorder2));
        System.out.println();

        TreeNode root2 = solution.buildTree(preorder2, inorder2);
        BuildTreeUtils.printTree(root2);
        System.out.println();

        List<Integer> verifyPre2 = BuildTreeUtils.preorder(root2);
        List<Integer> verifyIn2 = BuildTreeUtils.inorder(root2);
        System.out.println("验证 - 前序: " + verifyPre2);
        System.out.println("验证 - 中序: " + verifyIn2);
        System.out.println();

        // ========== 测试用例3 ==========

        System.out.println("========== 测试用例3 ==========");
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        int[] preorder3 = {1, 2, 4, 5, 3};
        int[] inorder3 = {4, 2, 5, 1, 3};

        System.out.println("前序遍历: " + Arrays.toString(preorder3));
        System.out.println("中序遍历: " + Arrays.toString(inorder3));
        System.out.println();

        TreeNode root3 = solution.buildTree(preorder3, inorder3);
        BuildTreeUtils.printTree(root3);
        System.out.println();

        List<Integer> verifyPre3 = BuildTreeUtils.preorder(root3);
        List<Integer> verifyIn3 = BuildTreeUtils.inorder(root3);
        System.out.println("验证 - 前序: " + verifyPre3);
        System.out.println("验证 - 中序: " + verifyIn3);
    }
}
