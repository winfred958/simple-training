package com.leetcode.algorithms.tree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Before;
import org.junit.Test;

public class TrieTreeTest {

    TrieTree trieTree;

    @Before
    public void buildTree() {
        trieTree = new TrieTree();
        trieTree.addWord("abc");
        trieTree.addWord("abcdefg");
        trieTree.addWord("abcxyz");
        trieTree.addWord("123");
        trieTree.addWord("126");
    }

    @Test
    public void trieTest() {
        TrieTree.TrieNode trie = trieTree.getTrie();

        String jsonString = JSON.toJSONString(trie, SerializerFeature.SortField, SerializerFeature.IgnoreNonFieldGetter);
        System.out.println(jsonString);

    }
}
