package com.leetcode.algorithms.tree;


import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@Slf4j
public class BinaryTreeTraversalTest {
  
  BinaryTreeTraversal.Solution solution = new BinaryTreeTraversal.Solution();
  
  BinaryTreeNode<Integer> inorderTree = new BinaryTreeNode<>(null);
  BinaryTreeNode<Integer> preorderTree = new BinaryTreeNode<>(null);
  BinaryTreeNode<Integer> postorderTree = new BinaryTreeNode<>(null);
  BinaryTreeNode<Integer> levelOrderTree = new BinaryTreeNode<>(null);
  
  /**
   * 深度优先搜索
   * 二叉树 - 中序遍历
   */
  @Test
  public void inorderTraversal() {
    List<BinaryTreeNode<Integer>> inorderTraversalResult = solution.inorderTraversal(inorderTree);
    log.info("============Inorder===============");
    for (BinaryTreeNode<Integer> binaryTreeNode : inorderTraversalResult) {
      System.out.println(binaryTreeNode.getT());
    }
  }
  
  /**
   * 深度优先搜索
   * 二叉树 - 先序遍历
   */
  @Test
  public void preorderTraversal() {
    List<BinaryTreeNode<Integer>> inorderTraversalResult = solution.preorderTraversal(preorderTree);
    log.info("============Preorder===============");
    for (BinaryTreeNode<Integer> binaryTreeNode : inorderTraversalResult) {
      System.out.println(binaryTreeNode.getT());
    }
  }
  
  /**
   * 深度优先搜索
   * 二叉树 - 后序遍历
   */
  @Test
  public void postorderTraversal() {
    List<BinaryTreeNode<Integer>> inorderTraversalResult = solution.postorderTraversal(postorderTree);
    log.info("============Postorder===============");
    for (BinaryTreeNode<Integer> binaryTreeNode : inorderTraversalResult) {
      System.out.println(binaryTreeNode.getT());
    }
  }
  
  /**
   * 广度优先搜索
   * 二叉树 - 层序遍历
   */
  
  @Test
  public void levelOrderTraversal() {
    List<BinaryTreeNode<Integer>> inorderTraversalResult = solution.levelOrderTraversal(levelOrderTree);
    log.info("============LevelOrder===============");
    for (BinaryTreeNode<Integer> binaryTreeNode : inorderTraversalResult) {
      System.out.println(binaryTreeNode.getT());
    }
  }
  
  /**
   * 构造中序遍历二叉树
   */
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
  
  
  /**
   * 构造先序遍历二叉树
   */
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
  
  /**
   * 构造后序遍历二叉树
   */
  @Before
  public void buildPostorderTraversalTestData() {
    
    BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
    BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
    BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
    BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
    BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
    BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);
    BinaryTreeNode<Integer> value7 = new BinaryTreeNode<>(7);
    BinaryTreeNode<Integer> value8 = new BinaryTreeNode<>(8);
    
    postorderTree.setLeft(value3);
    value3.setLeft(value1);
    value3.setRight(value2);
    
    postorderTree.setRight(value8);
    value8.setLeft(value4);
    value8.setRight(value7);
    
    value7.setLeft(value5);
    value7.setRight(value6);
  }
  
  /**
   * 构造广度搜索优先树
   */
  @Before
  public void buildLevelOrderTraversalTestData() {
    BinaryTreeNode<Integer> value1 = new BinaryTreeNode<>(1);
    BinaryTreeNode<Integer> value2 = new BinaryTreeNode<>(2);
    BinaryTreeNode<Integer> value3 = new BinaryTreeNode<>(3);
    BinaryTreeNode<Integer> value4 = new BinaryTreeNode<>(4);
    BinaryTreeNode<Integer> value5 = new BinaryTreeNode<>(5);
    BinaryTreeNode<Integer> value6 = new BinaryTreeNode<>(6);
    BinaryTreeNode<Integer> value7 = new BinaryTreeNode<>(7);
    BinaryTreeNode<Integer> value8 = new BinaryTreeNode<>(8);
    
    levelOrderTree.setLeft(value1);
    levelOrderTree.setRight(value2);
    
    value1.setLeft(value3);
    value1.setRight(value4);
    
    value3.setRight(value7);
    
    value2.setLeft(value5);
    value2.setRight(value6);
    
    value5.setLeft(value8);
  }
}
