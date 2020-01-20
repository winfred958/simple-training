package com.leetcode.algorithms.tree;

import java.util.List;

public class BinaryTree {

    public static void main(String[] args) {

    }


    private List<BinaryTreeNode<Integer>> inorderTraversal(BinaryTreeNode<Integer> root) {


        return null;
    }


    private static BinaryTreeNode<Integer> buildTestData() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(null);

        BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);

        value2.setLeft(value1);
        value2.setRight(value3);

        value5.setLeft(value4);
        value5.setRight(value6);

        root.setLeft(value2);
        root.setRight(value5);
        return root;
    }
}
