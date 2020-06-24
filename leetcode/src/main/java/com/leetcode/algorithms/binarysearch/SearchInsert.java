package com.leetcode.algorithms.binarysearch;

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * <p>
 * 你可以假设数组中无重复元素。
 * <p>
 * 思路:
 * - 二分查找
 */
public class SearchInsert {
  public static void main(String[] args) {
    int[] nums = {1, 3, 5, 6};
    int target = 7;
    
    Solution solution = new Solution();
    int index = solution.searchInsert(nums, target);
    
    System.out.println(index);
  }
  
  static class Solution {
    public int searchInsert(int[] nums, int target) {
      int low = 0;
      int high = nums.length - 1;
      int targetIndex = -1;
      while (low <= high) {
        // 无符号右移, 等价于除以2
        int mid = (low + high) >>> 1;
        int midValue = nums[mid];
        if (midValue < target) {
          low = mid + 1;
        } else if (midValue > target) {
          high = mid - 1;
        } else {
          targetIndex = mid;
          break;
        }
      }
      
      // 如果 targetIndex == -1, insertIndex = low
      return targetIndex == -1 ? low : targetIndex;
    }
  }
}
