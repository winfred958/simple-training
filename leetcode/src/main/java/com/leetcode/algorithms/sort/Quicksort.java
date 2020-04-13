package com.leetcode.algorithms.sort;


/**
 * 快速排序
 * 就是给基准数据找正确索引的位置
 */
public class Quicksort {

    static class Solution {
        public int[] quickSort(int[] array, int start, int end) {
            // 1. 选一个数作为基准数据
            int standard = array[start];

            int low = start;
            int high = end;
            while (low < high) {
                while ((low < high) && (array[high] > standard)) {
                    high--;
                }
                while ((low < high) && (array[low] < standard)) {
                    low++;
                }
                if (array[high] < standard) {
                    // 交换 low 与 high
                }

            }


            return null;
        }
    }
}
