import java.util.*;

// 二叉树节点类
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

// 层序遍历（BFS - 广度优先搜索）
class LevelOrderSolution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();  // 当前层的节点数
            List<Integer> level = new ArrayList<>();

            // 处理当前层的所有节点
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

    // 层序遍历（返回一维数组）
    public List<Integer> levelOrderFlat(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        return result;
    }
}

class LevelOrderMain {
    public static void main(String[] args) {
        LevelOrderSolution solution = new LevelOrderSolution();

        // 构建测试树：
        //       1
        //      / \
        //     2   3
        //    / \   \
        //   4   5   6
        System.out.println("树结构:");
        System.out.println("       1");
        System.out.println("      / \\");
        System.out.println("     2   3");
        System.out.println("    / \\   \\");
        System.out.println("   4   5   6");
        System.out.println();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        // 分层结果
        System.out.println("=== 层序遍历（分层） ===");
        List<List<Integer>> levelResult = solution.levelOrder(root);
        for (int i = 0; i < levelResult.size(); i++) {
            System.out.println("第 " + i + " 层: " + levelResult.get(i));
        }

        // 扁平结果
        System.out.println("\n=== 层序遍历（扁平） ===");
        List<Integer> flatResult = solution.levelOrderFlat(root);
        System.out.println("结果: " + flatResult);
    }
}
