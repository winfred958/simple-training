package com.leetcode.algorithms.tree;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 1. 深度优先搜索(DFS)
 * -- Inorder Traversal     (中序遍历) => (left -> top -> right)
 * -- Preorder Traversal    (前序遍历) => (top -> left -> right)
 * -- Postorder Traversal   (后序遍历) => (left -> right -> top)
 * <p>
 * 2. 宽度优先搜索(BFS)
 * -- 层序遍历
 */
public class BinaryTree {


    public static void main(String[] args) {

        BinaryTreeNode<Integer> inorderTree = buildInorderTraversalTestData();
        Solution solution = new Solution();
        List<BinaryTreeNode<Integer>> inorderList = solution.inorderTraversal(inorderTree);
        for (BinaryTreeNode<Integer> binaryTreeNode : inorderList) {
            System.out.println(binaryTreeNode.getT());
        }

        System.out.println("===========================");

        BinaryTreeNode<Integer> preorderTree = buildPreorderTraversalTestData();
        List<BinaryTreeNode<Integer>> preorderList = solution.preorderTraversal(preorderTree);
        for (BinaryTreeNode<Integer> binaryTreeNode : preorderList) {
            System.out.println(binaryTreeNode.getT());
        }

        System.out.println("===========================");

        BinaryTreeNode<Integer> postorderTree = buildPostorderTraversalTestData();
        List<BinaryTreeNode<Integer>> postorderList = solution.postorderTraversal(postorderTree);
        for (BinaryTreeNode<Integer> binaryTreeNode : postorderList) {
            System.out.println(binaryTreeNode.getT());
        }

    }

    static class Solution {


        /**
         * 中序遍历
         *
         * @param root
         * @return
         */
        public List<BinaryTreeNode<Integer>> inorderTraversal(BinaryTreeNode<Integer> root) {
            List<BinaryTreeNode<Integer>> result = new CopyOnWriteArrayList<>();
            inorderTraversal(root, result);
            return result;
        }

        private void inorderTraversal(BinaryTreeNode<Integer> root, List<BinaryTreeNode<Integer>> result) {

            if (null == root) {
                return;
            }
            if (root.hasLeftChild()) {
                inorderTraversal(root.getLeft(), result);
            }
            result.add(root);
            if (root.hasRightChild()) {
                inorderTraversal(root.getRight(), result);
            }
        }


        /**
         * 前序遍历
         *
         * @param root
         * @return
         */
        public List<BinaryTreeNode<Integer>> preorderTraversal(BinaryTreeNode<Integer> root) {
            List<BinaryTreeNode<Integer>> result = new CopyOnWriteArrayList<>();
            preorderTraversal(root, result);
            return result;
        }

        private void preorderTraversal(BinaryTreeNode<Integer> node, List<BinaryTreeNode<Integer>> result) {
            if (null == node) {
                return;
            }
            result.add(node);
            if (node.hasLeftChild()) {
                preorderTraversal(node.getLeft(), result);
            }
            if (node.hasRightChild()) {
                preorderTraversal(node.getRight(), result);
            }
        }

        /**
         * 后序遍历
         *
         * @param root
         * @return
         */
        public List<BinaryTreeNode<Integer>> postorderTraversal(BinaryTreeNode<Integer> root) {
            List<BinaryTreeNode<Integer>> result = new CopyOnWriteArrayList<>();
            postorderTraversal(root, result);
            return result;
        }

        private void postorderTraversal(BinaryTreeNode<Integer> node, List<BinaryTreeNode<Integer>> result) {
            if (null == node) {
                return;
            }
            if (node.hasLeftChild()) {
                postorderTraversal(node.getLeft(), result);
            }
            if (node.hasRightChild()) {
                postorderTraversal(node.getRight(), result);
            }
            result.add(node);
        }
    }

    private static BinaryTreeNode<Integer> buildInorderTraversalTestData() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(null);

        BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> value7 = new BinaryTreeNode<>(7);
        value2.setLeft(value1);
        value2.setRight(value3);

        value5.setLeft(value4);
        value5.setRight(value6);

        value6.setRight(value7);

        root.setLeft(value2);
        root.setRight(value5);
        return root;
    }


    private static BinaryTreeNode<Integer> buildPreorderTraversalTestData() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(null);

        BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> value7 = new BinaryTreeNode<>(7);
        root.setLeft(value1);
        value1.setLeft(value2);
        value2.setLeft(value3);
        value1.setRight(value4);

        root.setRight(value5);
        value5.setLeft(value6);
        value5.setRight(value7);
        return root;
    }

    private static BinaryTreeNode<Integer> buildPostorderTraversalTestData() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(null);

        BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> value7 = new BinaryTreeNode<>(7);
        BinaryTreeNode<Integer> value8 = new BinaryTreeNode<>(8);

        root.setLeft(value3);
        value3.setLeft(value1);
        value3.setRight(value2);

        root.setRight(value8);
        value8.setLeft(value4);
        value8.setRight(value7);

        value7.setLeft(value5);
        value7.setRight(value6);

        return root;
    }
}
