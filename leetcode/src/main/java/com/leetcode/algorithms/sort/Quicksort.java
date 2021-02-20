package com.leetcode.algorithms.sort;


/**
 * @author winfred958
 * <p>
 * 快速排序
 * 就是给基准数据找正确索引的位置
 */
public class Quicksort {

  static class SolutionOne {
    public static void quickSort(int[] array, int start, int end) {
      if (start > end) {
        return;
      }
      int index = pivotIndex(array, start, end);
      quickSort(array, start, index - 1);
      quickSort(array, index + 1, end);
    }

    /**
     * @param arr
     * @param low
     * @param high
     * @return 新基准数据的 index
     */
    private static int pivotIndex(int[] arr, int low, int high) {
      // 基准数据
      int pivotValue = arr[low];
      while (low < high) {
        while (low < high && arr[high] >= pivotValue) {
          high--;
        }
        arr[low] = arr[high];
        while (low < high && arr[low] <= pivotValue) {
          low++;
        }
        arr[high] = arr[low];
      }
      arr[low] = pivotValue;
      return low;
    }
  }

  public static void main(String[] args) {
    int[] arr = new int[]{99, 10, 20, 0, -1};
    SolutionOne.quickSort(arr, 0, arr.length - 1);
    for (int value : arr) {
      System.out.println(value);
    }
  }
}
