#include <iostream>
#include <vector>
#include <stack>
using namespace std;

// 二叉树节点定义
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right)
        : val(x), left(left), right(right) {}
};

// 迭代法前序遍历
vector<int> preorderTraversal(TreeNode* root) {
    vector<int> arr;
    stack<TreeNode*> _stack;
    if (!root) return arr;
    TreeNode* cur = root;
    _stack.push(cur);
    while (!_stack.empty()) {
        auto treeNode = _stack.top();
        _stack.pop();
        arr.push_back(treeNode->val);
        if (treeNode->right) _stack.push(treeNode->right);
        if (treeNode->left) _stack.push(treeNode->left);
    }
    return arr;
}

// 递归法前序遍历（注释掉了，可选用）
// void traversal(TreeNode* root, vector<int>& arr) {
//     if (root == nullptr) return;
//     arr.push_back(root->val);
//     traversal(root->left, arr);
//     traversal(root->right, arr);
// }
//
// vector<int> preorderTraversal(TreeNode* root) {
//     vector<int> arr;
//     traversal(root, arr);
//     return arr;
// }

// 构建二叉树（从数组）
// null 表示空节点
TreeNode* buildTree(vector<int>& nums, int index) {
    if (index >= nums.size() || nums[index] == INT_MIN) {
        return nullptr;
    }
    TreeNode* root = new TreeNode(nums[index]);
    root->left = buildTree(nums, 2 * index + 1);
    root->right = buildTree(nums, 2 * index + 2);
    return root;
}

// 输出遍历结果
void printResult(vector<int>& result) {
    cout << "前序遍历结果: [";
    for (int i = 0; i < result.size(); i++) {
        cout << result[i];
        if (i < result.size() - 1) cout << ", ";
    }
    cout << "]" << endl;
}

int main() {
    // 测试用例1: [1,null,2,3]
    cout << "=== 测试用例1 ===" << endl;
    cout << "输入: [1,null,2,3]" << endl;
    vector<int> nums1 = {1, INT_MIN, 2, INT_MIN, INT_MIN, 3};
    TreeNode* root1 = buildTree(nums1, 0);
    vector<int> result1 = preorderTraversal(root1);
    printResult(result1);
    cout << endl;

    // 测试用例2: [1,2,3,4,5,null,8,null,null,6,7,9]
    cout << "=== 测试用例2 ===" << endl;
    cout << "输入: [1,2,3,4,5,null,8,null,null,6,7,9]" << endl;
    vector<int> nums2 = {1, 2, 3, 4, 5, INT_MIN, 8, INT_MIN, INT_MIN, 6, 7, 9};
    TreeNode* root2 = buildTree(nums2, 0);
    vector<int> result2 = preorderTraversal(root2);
    printResult(result2);
    cout << endl;

    // 测试用例3: 空树 []
    cout << "=== 测试用例3 ===" << endl;
    cout << "输入: []" << endl;
    vector<int> nums3 = {};
    TreeNode* root3 = buildTree(nums3, 0);
    vector<int> result3 = preorderTraversal(root3);
    printResult(result3);
    cout << endl;

    // 测试用例4: [1]
    cout << "=== 测试用例4 ===" << endl;
    cout << "输入: [1]" << endl;
    vector<int> nums4 = {1};
    TreeNode* root4 = buildTree(nums4, 0);
    vector<int> result4 = preorderTraversal(root4);
    printResult(result4);
    cout << endl;

    // 手动构建一棵树测试
    //      1
    //     / \
    //    2   3
    //   / \
    //  4   5
    cout << "=== 手动构建测试 ===" << endl;
    cout << "树结构:" << endl;
    cout << "      1" << endl;
    cout << "     / \\" << endl;
    cout << "    2   3" << endl;
    cout << "   / \\" << endl;
    cout << "  4   5" << endl;
    TreeNode* manualRoot = new TreeNode(1);
    manualRoot->left = new TreeNode(2);
    manualRoot->right = new TreeNode(3);
    manualRoot->left->left = new TreeNode(4);
    manualRoot->left->right = new TreeNode(5);
    vector<int> manualResult = preorderTraversal(manualRoot);
    printResult(manualResult);

    return 0;
}
