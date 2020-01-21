package com.leetcode.algorithms.tree;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BinaryTreeTraversalTest {

    BinaryTreeTraversal.Solution solution = new BinaryTreeTraversal.Solution();

    BinaryTreeNode<Integer> inorderTree = new BinaryTreeNode<>(null);
    BinaryTreeNode<Integer> preorderTree = new BinaryTreeNode<>(null);


    @Test
    public void inorderTraversal() {
        List<BinaryTreeNode<Integer>> inorderTraversalResult = solution.inorderTraversal(inorderTree);
        System.out.println("============Inorder===============");
        for (BinaryTreeNode<Integer> binaryTreeNode : inorderTraversalResult) {
            System.out.println(binaryTreeNode.getT());
        }
    }


    @Before
    public void buildInorderTraversalTestData() {


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

        inorderTree.setLeft(value2);
        inorderTree.setRight(value5);
    }


    @Before
    public void buildPreorderTraversalTestData() {


        BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> value7 = new BinaryTreeNode<>(7);
        preorderTree.setLeft(value1);
        value1.setLeft(value2);
        value2.setLeft(value3);
        value1.setRight(value4);

        preorderTree.setRight(value5);
        value5.setLeft(value6);
        value5.setRight(value7);
    }
}
