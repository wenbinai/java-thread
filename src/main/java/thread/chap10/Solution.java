package thread.chap10;


import java.util.ArrayList;
import java.util.List;

// 二叉树, 根节点root节点到叶子节点的路径集合
public class Solution {
    public static void main(String[] args) {
        TreeNode root = createTree();
        res = solve(root);
        for (List<Integer> tmp : res) {
            for (Integer i : tmp) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    static List<List<Integer>> res = new ArrayList<List<Integer>>();

    public static List<List<Integer>> solve(TreeNode root) {
        if (root == null) return null;
        helper(root, new ArrayList<Integer>());
        return res;
    }

    public static void helper(TreeNode node, List<Integer> list) {
        if (node.left == null && node.right == null) {
            list.add(node.val);
            res.add(list);
            return;
        } else {
            list.add(node.val);
            if (node.left != null) {
                helper(node.left, list);
            }
            if (node.right != null) {
                helper(node.right, list);
            }
        }
    }

    public static TreeNode createTree() {
        TreeNode root = new TreeNode(1, null, null);
        root.left = new TreeNode(2, null, null);
        root.right = new TreeNode(3, null, null);
        root.left.left = new TreeNode(4, null, null);
        root.left.right = new TreeNode(5, null, null);
        root.right.left = new TreeNode(6, null, null);
        root.right.right = new TreeNode(7, null, null);
        return root;
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

