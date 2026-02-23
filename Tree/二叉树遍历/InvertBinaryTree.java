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

class InvertSolution {
    // ========== 1. 递归法 ==========
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;

        return root;
    }

    // ========== 2. 非递归法 - 前序遍历翻转 ==========
    public TreeNode invertTreePreorder(TreeNode root) {
        if (root == null) return null;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            // 交换左右子节点
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            // 先右后左入栈（前序）
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }

        return root;
    }

    // ========== 3. 非递归法 - 中序遍历翻转 ==========
    public TreeNode invertTreeInorder(TreeNode root) {
        if (root == null) return null;

        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            // 一直往左走
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            // 弹出并交换
            curr = stack.pop();
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;

            // 转向右子树
            curr = curr.right;  // 注意：此时 curr.right 已经是原来的 left
        }

        return root;
    }

    // ========== 4. 非递归法 - 后序遍历翻转 ==========
    public TreeNode invertTreePostorder(TreeNode root) {
        if (root == null) return null;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            // 先处理子树（后序）
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);

            // 最后交换当前节点
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
        }

        return root;
    }

    // ========== 5. 层次遍历翻转 ==========
    public TreeNode invertTreeLevelOrder(TreeNode root) {
        if (root == null) return null;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            // 交换左右子节点
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            // 将子节点入队
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        return root;
    }
}

// 工具类 - 打印树
class TreePrinter {
    // 层序打印树
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("空树");
            return;
        }

        List<List<Integer>> levels = levelOrder(root);
        int maxLevel = levels.size();

        for (int i = 0; i < maxLevel; i++) {
            List<Integer> level = levels.get(i);
            // 计算缩进
            int indent = (int) Math.pow(2, maxLevel - i - 1) - 1;
            System.out.print(" ".repeat(Math.max(0, indent)));

            for (int j = 0; j < level.size(); j++) {
                System.out.print(level.get(j) + " ");
            }
            System.out.println();
        }
    }

    // 层序遍历
    private static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
        }

        return result;
    }

    // 打印层序结果
    public static void printLevelOrder(TreeNode root) {
        List<List<Integer>> levels = levelOrder(root);
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("第 " + i + " 层: " + levels.get(i));
        }
    }
}

class InvertMain {
    public static void main(String[] args) {
        InvertSolution solution = new InvertSolution();

        // 构建测试树：
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        System.out.println("========== 翻转前树结构 ==========");
        System.out.println("      1");
        System.out.println("     / \\");
        System.out.println("    2   3");
        System.out.println("   / \\");
        System.out.println("  4   5");
        System.out.println();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // ========== 测试递归法 ==========
        System.out.println("=== 1. 递归法 ===");
        TreeNode root1 = copyTree(root);
        solution.invertTree(root1);
        TreePrinter.printLevelOrder(root1);

        // ========== 测试前序非递归 ==========
        System.out.println("\n=== 2. 非递归 - 前序 ===");
        TreeNode root2 = copyTree(root);
        solution.invertTreePreorder(root2);
        TreePrinter.printLevelOrder(root2);

        // ========== 测试中序非递归 ==========
        System.out.println("\n=== 3. 非递归 - 中序 ===");
        TreeNode root3 = copyTree(root);
        solution.invertTreeInorder(root3);
        TreePrinter.printLevelOrder(root3);

        // ========== 测试后序非递归 ==========
        System.out.println("\n=== 4. 非递归 - 后序 ===");
        TreeNode root4 = copyTree(root);
        solution.invertTreePostorder(root4);
        TreePrinter.printLevelOrder(root4);

        // ========== 测试层次遍历 ==========
        System.out.println("\n=== 5. 层次遍历 ===");
        TreeNode root5 = copyTree(root);
        solution.invertTreeLevelOrder(root5);
        TreePrinter.printLevelOrder(root5);

        // ========== 期望结果 ==========
        System.out.println("\n========== 期望结果 ==========");
        System.out.println("翻转后:");
        System.out.println("      1");
        System.out.println("     / \\");
        System.out.println("    3   2");
        System.out.println("       / \\");
        System.out.println("      5   4");
    }

    // 复制树（用于测试不同方法）
    private static TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        TreeNode newNode = new TreeNode(root.val);
        newNode.left = copyTree(root.left);
        newNode.right = copyTree(root.right);
        return newNode;
    }
}
