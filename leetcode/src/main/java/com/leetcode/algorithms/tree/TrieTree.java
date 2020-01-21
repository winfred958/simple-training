package com.leetcode.algorithms.tree;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 前缀树
 *
 * @author kevin
 */
public class TrieTree {

    private TrieNode trie;

    /**
     * build trie use word
     *
     * @param word
     */
    public void addWord(String word) {
        char[] chars = word.toCharArray();
        if (null == trie) {
            trie = new TrieNode();
            trie.setParent(null);
        }
        addChars(trie, chars, 0);
    }

    private void addChars(TrieNode trie, char[] chars, int index) {
        if (index >= chars.length) {
            return;
        }
        String key = String.valueOf(chars[index]);
        Map<String, TrieNode> currentNode = trie.getChild();
        if (null == currentNode) {
            currentNode = new ConcurrentSkipListMap<>();
            trie.setChild(currentNode);
        }
        if (currentNode.containsKey(key)) {
            // word 节点存在, 遍历下一个
        } else {
            // 新的 word 节点
            TrieNode child = new TrieNode();
            child.setParent(child);
            currentNode.put(key, child);
        }
        addChars(currentNode.get(key), chars, index + 1);
    }

    /**
     * word in trie ?
     *
     * @param word
     * @return
     */
    public boolean containsWord(String word) {
        return containsWord(this.trie, word.toCharArray(), 0);
    }

    private boolean containsWord(TrieNode node, char[] chars, int index) {
        if (null == node) {
            return false;
        }

        if (index >= chars.length) {
            return true;
        }
        String key = String.valueOf(chars[index]);
        Map<String, TrieNode> child = node.getChild();
        if (null != child && child.containsKey(key)) {
            // 写一个 node 和 index + 1, 对比
            return containsWord(child.get(key), chars, index + 1);
        } else {
            return false;
        }
    }


    public TrieNode getTrie() {
        return trie;
    }

    public void setTrie(TrieNode trie) {
        this.trie = trie;
    }

    static class TrieNode {

        private Map<String, TrieNode> child;
        private TrieNode parent;

        public Map<String, TrieNode> getChild() {
            return child;
        }

        public void setChild(Map<String, TrieNode> child) {
            this.child = child;
        }

        public TrieNode getParent() {
            return parent;
        }

        public void setParent(TrieNode parent) {
            this.parent = parent;
        }

        public void addChildNode(String key, TrieNode node) {
            if (this.child == null) {
                child = new ConcurrentSkipListMap<>();
            }
            if (!child.containsKey(key)) {
                child.put(key, node);
            }
        }

        public boolean constanceKey(String key) {
            return child == null ? false : child.containsKey(key);
        }
    }
}
