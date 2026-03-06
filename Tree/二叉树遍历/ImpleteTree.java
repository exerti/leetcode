import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    };

    TreeNode() {
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class ImpleteTree {
    LinkedList<Integer> result = new LinkedList<>();

    // //前序+ 中序
    TreeNode buildTree(int[] arrs) {

        return new TreeNode();
    }

    // 递归
    void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        result.push(root.val);
        if (root.left != null) {
            preOrder(root.left);
        }
        if (root.right != null) {
            preOrder(root.right);
        }

    }

    void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            inOrder(root.left);
        }
        result.push(root.val);

        if (root.right != null) {
            inOrder(root.right);
        }
    }

    void endOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            endOrder(root.left);
        }

        if (root.right != null) {
            endOrder(root.right);
        }
        result.push(root.val);
    }

    // 迭代
    List<Integer> preOrder2(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        if (root == null)
            return result;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node == null) {
                result.add(stack.pop().val);
            } else {
                // null 根 左 右
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
                stack.push(node);
                stack.push(null);
            }
        }

        return result;
    }

    List<Integer> inOrder2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node == null) {
                result.add(stack.pop().val);
            } else {
                // 左 根 右
                if (node.right != null) {
                    stack.push(node.right);
                }
                stack.push(node);
                stack.push(null);
                if (node.left != null) {
                    stack.push(node.left);
                }

            }
        }

        return result;
    }

    // if (node.left != null) stack.push(node.left);
    // if (node.right != null) stack.push(node.right);
    // stack.push(node);
    // stack.push(null);
    // 最后需要：Collections.reverse(result);

    // 直接按"左 右 根"遍历
    // stack.push(node);
    // stack.push(null);
    // if (node.right != null) stack.push(node.right);
    // if (node.left != null) stack.push(node.left);
    // 不需要翻转
    List<Integer> endOrder2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node == null) {
                result.add(stack.pop().val);
            } else {
                // 左 右 根 ， ——》左 右 根 nul

                stack.push(node);
                stack.push(null);

                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }

            }
        }

        return result;
    }

    // 后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node == null) {
                result.add(stack.pop().val);
            } else {

                stack.push(node);
                stack.push(null);

                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }

            }

        }
        return result;
    }

    // 层次
    List<Integer> levelOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        return result;
    }

    public static void main(String args) {

    }
}
