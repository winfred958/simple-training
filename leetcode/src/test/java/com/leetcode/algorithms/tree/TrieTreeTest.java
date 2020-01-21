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
        trieTree.addWord("goo");
        trieTree.addWord("google");
        trieTree.addWord("good");
        trieTree.addWord("goods");
        trieTree.addWord("goose");
        trieTree.addWord("goodness");
        trieTree.addWord("gift");
        trieTree.addWord("apple");
        trieTree.addWord("app");
        trieTree.addWord("appreciate");
        trieTree.addWord("appropriate");
        trieTree.addWord("approach");
    }

    @Test
    public void trieTest() {
        TrieTree.TrieNode trie = trieTree.getTrie();

        String jsonString = JSON.toJSONString(trie, SerializerFeature.SortField, SerializerFeature.IgnoreNonFieldGetter);
        System.out.println(jsonString);


        boolean good = trieTree.containsWord("good");
        boolean goose = trieTree.containsWord("goody");

        System.out.println(good);
        System.out.println(goose);
    }
}
