import java.util.*;

// 二叉树节点类
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
        this.val = 0;
        this.left = null;
        this.right = null;
    }

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    // 迭代法前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
        return result;
    }

    // 递归法前序遍历（可选用）
    // public List<Integer> preorderTraversal(TreeNode root) {
    // List<Integer> result = new ArrayList<>();
    // traversal(root, result);
    // return result;
    // }
    //
    // private void traversal(TreeNode root, List<Integer> result) {
    // if (root == null) return;
    // result.add(root.val);
    // traversal(root.left, result);
    // traversal(root.right, result);
    //

    // 中序遍历
    public List<Integer> func2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 弹出站定并访问
            root = stack.pop();
            result.add(root.val);
            // 转到右子树
            root = root.right;
        }

        return result;
    }

    // 中序遍历 递归版
    public List<Integer> func2Dg(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traversal2(root, result);
        return result;
    }

    private void traversal2(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        traversal2(root.left, result);
        result.add(root.val);
        traversal2(root.right, result);
    }

    // 后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();

            if (node == null) {
                stack.pop();
                result.add(stack.pop().val); // 访问真正的节点
            } else {
                // 不是 null，按右-左-根-null 顺序入栈
                if (node.right != null)
                    stack.push(node.right);
                if (node.left != null)
                    stack.push(node.left);
                stack.push(node);
                stack.push(null); // 标记
            }
        }

        return result;
    }

    // 后遍历 递归版
    public List<Integer> func3Dg(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traversal2(root, result);
        return result;
    }

    



     // ========== 标记法统一模板 ==========

     // 标记法前序：根-左-右 → 入栈：右、左、null、根
     public List<Integer> preorderMarker(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            if (node == null) {
                // 遇到标记，访问真正的节点
                result.add(stack.pop().val);
            } else {
                // 入栈顺序：右、左、null、根
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                stack.push(node);
                stack.push(null);  // 标记
            }
        }
        return result;
    }

     // 标记法中序：左-根-右 → 入栈：右、null、根、左
     public List<Integer> inorderMarker(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            if (node == null) {
                // 遇到标记，访问真正的节点
                result.add(stack.pop().val);
            } else {
                // 入栈顺序：右、null、根、左
                if (node.right != null) stack.push(node.right);
                stack.push(node);
                stack.push(null);  // 标记
                if (node.left != null) stack.push(node.left);
            }
        }
        return result;
    }

     // 标记法后序：左-右-根 → 前序"根-右-左"，最后反转
     public List<Integer> postorderMarker(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node == null) {
                result.add(stack.pop().val);
            } else {
                // 前序改为"根-右-左"：入栈 左、右、null、根
                if (node.left != null) stack.push(node.left);
                if (node.right != null) stack.push(node.right);
                stack.push(node);
                stack.push(null);
            }
        }
        Collections.reverse(result);
        return result;
    }
}

// 二叉树工具类 - 用于构建和打印树
class TreeUtils {
    // 从数组构建二叉树，null 表示空节点
    public static TreeNode buildTree(Integer[] nums) {
        if (nums == null || nums.length == 0 || nums[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < nums.length) {
            TreeNode node = queue.poll();

            // 左子节点
            if (i < nums.length) {
                if (nums[i] != null) {
                    node.left = new TreeNode(nums[i]);
                    queue.offer(node.left);
                }
                i++;
            }

            // 右子节点
            if (i < nums.length) {
                if (nums[i] != null) {
                    node.right = new TreeNode(nums[i]);
                    queue.offer(node.right);
                }
                i++;
            }
        }
        return root;
    }

    // 打印遍历结果
    public static void printResult(String label, List<Integer> result) {
        System.out.print(label + ": [");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }
}

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // 测试树结构:
        //      1
        //     / \
        //    2   3
        //   / \
        //  4   5
        System.out.println("========== 标记法测试 ==========");
        System.out.println("树结构:");
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

        // 前序遍历标记法
        System.out.println("--- 前序遍历 (根-左-右) ---");
        System.out.println("入栈顺序: 右、左、null、根");
        List<Integer> preorderResult = solution.preorderMarker(root);
        TreeUtils.printResult("结果", preorderResult);
        System.out.println("期望: [1, 2, 4, 5, 3]");
        System.out.println();

        // 中序遍历标记法
        System.out.println("--- 中序遍历 (左-根-右) ---");
        System.out.println("入栈顺序: 右、null、根、左");
        List<Integer> inorderResult = solution.inorderMarker(root);
        TreeUtils.printResult("结果", inorderResult);
        System.out.println("期望: [4, 2, 5, 1, 3]");
        System.out.println();

        // 后序遍历标记法
        System.out.println("--- 后序遍历 (左-右-根) ---");
        System.out.println("入栈顺序: 左、右、null、根（前序根-右-左，最后反转）");
        List<Integer> postorderResult = solution.postorderMarker(root);
        TreeUtils.printResult("结果", postorderResult);
        System.out.println("期望: [4, 5, 2, 3, 1]");
        System.out.println();

        // ========== 边界测试 ==========

        System.out.println("========== 边界测试 ==========");

        // 空树
        System.out.println("--- 空树 ---");
        TreeNode emptyTree = null;
        TreeUtils.printResult("前序", solution.preorderMarker(emptyTree));
        TreeUtils.printResult("中序", solution.inorderMarker(emptyTree));
        TreeUtils.printResult("后序", solution.postorderMarker(emptyTree));
        System.out.println();

        // 单节点
        System.out.println("--- 单节点 [1] ---");
        TreeNode singleNode = new TreeNode(1);
        TreeUtils.printResult("前序", solution.preorderMarker(singleNode));
        TreeUtils.printResult("中序", solution.inorderMarker(singleNode));
        TreeUtils.printResult("后序", solution.postorderMarker(singleNode));
        System.out.println();

        // 只有左子树
        System.out.println("--- 只有左子树 ---");
        TreeNode leftOnly = new TreeNode(1);
        leftOnly.left = new TreeNode(2);
        TreeUtils.printResult("前序", solution.preorderMarker(leftOnly));
        TreeUtils.printResult("中序", solution.inorderMarker(leftOnly));
        TreeUtils.printResult("后序", solution.postorderMarker(leftOnly));
        System.out.println();

        // 只有右子树
        System.out.println("--- 只有右子树 ---");
        TreeNode rightOnly = new TreeNode(1);
        rightOnly.right = new TreeNode(2);
        TreeUtils.printResult("前序", solution.preorderMarker(rightOnly));
        TreeUtils.printResult("中序", solution.inorderMarker(rightOnly));
        TreeUtils.printResult("后序", solution.postorderMarker(rightOnly));
    }
}
