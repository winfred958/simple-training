package com.leetcode.algorithms.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class BubbleSort {

    private int[] array = {3, 10, 6, 19, 1};

    @Test
    public void sort() {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (array[i] > array[j]) {
                    array[i] = array[i] ^ array[j];
                    array[j] = array[i] ^ array[j];
                    array[i] = array[i] ^ array[j];
                }
            }
        }

        for (int i = 0; i < array.length; i++) {
            log.info("{}", array[i]);
        }
    }
}
