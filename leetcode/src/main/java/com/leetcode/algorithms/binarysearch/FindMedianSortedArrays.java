package com.leetcode.algorithms.binarysearch;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * <p>
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * <p>
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * <p>
 * 示例 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * 则中位数是 2.0
 * <p>
 * 示例 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * 则中位数是 (2 + 3)/2 = 2.5
 */
public class FindMedianSortedArrays {
  
  
  public static void main(String[] args) {
    Solution1 solution = new Solution1();
    
    int[] nums1 = {1};
    int[] nums2 = {2, 3, 4};
    
    double median = solution.findMedianSortedArrays(nums1, nums2);
    System.out.println(median);
  }
  
  
  static class Solution1 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      int[] mergeArray = mergeArray(nums1, nums2);
      Double median = getMedian(mergeArray);
      return median;
    }
    
    public Double getMedian(int[] array) {
      if (null == array) {
        return null;
      }
      if (array.length == 0) {
        return null;
      }
      int length = array.length;
      if (length % 2 == 0) {
        int left = array[(length - 1) / 2];
        int right = array[(length + 1) / 2];
        return ((double) left + (double) right) / 2;
      } else {
        return (double) array[length / 2];
      }
      
    }
    
    public int[] mergeArray(int[] nums1, int[] nums2) {
      
      int length1 = null == nums1 ? 0 : nums1.length;
      int length2 = null == nums2 ? 0 : nums2.length;
      
      if (length1 == 0) {
        return nums2;
      }
      
      if (length2 == 0) {
        return nums1;
      }
      
      int mergeLength = length1 + length2;
      int[] tmpArray = new int[mergeLength];
      int index1 = 0;
      int index2 = 0;
      
      int value1 = 0;
      int value2 = 0;
      
      for (int i = 0; i < mergeLength; i++) {
        // 1. 分别从两个数组中获取, 如果length==0 或 index >=length, 跳过
        
        if (index1 < length1) {
          value1 = nums1[index1];
        } else {
          // 1完
          tmpArray[i] = nums2[index2];
          index2++;
          continue;
        }
        
        if (index2 < length2) {
          value2 = nums2[index2];
        } else {
          // 2完
          tmpArray[i] = nums1[index1];
          index1++;
          continue;
        }
        // 2. 对比, 放入tmp, 放入成功的下标+1
        if (value1 < value2) {
          tmpArray[i] = value1;
          index1++;
        } else if (value1 >= value2) {
          tmpArray[i] = value2;
          index2++;
        }
      }
      
      return tmpArray;
    }
  }
  
  static class Solution2 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      Deque<Integer> deque1 = buildDeque(nums1);
      Deque<Integer> deque2 = buildDeque(nums2);
      
      deque1.pollFirst();
      
      return 0.0;
    }
    
    private Deque<Integer> buildDeque(int[] nums1) {
      Deque<Integer> deque1 = new LinkedList<Integer>();
      if (null == nums1 || nums1.length == 0) {
        return deque1;
      }
      Arrays.stream(nums1).forEach(e -> {
        deque1.add(e);
      });
      return deque1;
    }
    
  }
}
