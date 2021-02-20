package com.leetcode.algorithms.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 1. 深度优先搜索(DFS)
 * -- Inorder Traversal     (中序遍历) => (left -> top -> right)
 * -- Preorder Traversal    (前序遍历) => (top -> left -> right)
 * -- Postorder Traversal   (后序遍历) => (left -> right -> top)
 * <p>
 * 2. 广度度优先搜索(BFS)
 * -- 层序遍历 levelOrder
 */
public class BinaryTreeTraversal {


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

    private void inorderTraversal(BinaryTreeNode<Integer> node, List<BinaryTreeNode<Integer>> result) {

      if (null == node) {
        return;
      }
      if (node.hasLeftChild()) {
        inorderTraversal(node.getLeft(), result);
      }
      result.add(node);
      if (node.hasRightChild()) {
        inorderTraversal(node.getRight(), result);
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

    /**
     * BFS 广度搜索优先, 层序遍历
     * <p>
     * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/solution/er-cha-shu-de-ceng-ci-bian-li-by-leetcode/
     *
     * @param root
     * @return
     */
    public List<BinaryTreeNode<Integer>> levelOrderTraversal(BinaryTreeNode<Integer> root) {
      List<List<BinaryTreeNode<Integer>>> tmpResult = new CopyOnWriteArrayList<>();
      levelOrderTraversal(root, tmpResult, 0);

      List<BinaryTreeNode<Integer>> result = new ArrayList<>();
      tmpResult
          .forEach(e -> {
            result.addAll(e);
          });
      return result;
    }

    private void levelOrderTraversal(BinaryTreeNode<Integer> node, List<List<BinaryTreeNode<Integer>>> result, int level) {
      if (null == node) {
        return;
      }
      if (result.size() == level) {
        // 新增层级
        result.add(new ArrayList<BinaryTreeNode<Integer>>());
      }
      result.get(level).add(node);
      if (node.hasLeftChild()) {
        levelOrderTraversal(node.getLeft(), result, level + 1);
      }
      if (node.hasRightChild()) {
        levelOrderTraversal(node.getRight(), result, level + 1);
      }
    }
  }
}
