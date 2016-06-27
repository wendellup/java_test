package com.leetcode.question81_Search_in_Rotated_Sorted_Array_II;

public class Solution2 {
	public boolean search(int[] nums, int target) {
		int idx = -1;
		if (nums == null || nums.length == 0)
			return false;
		int l = 0, r = nums.length - 1;
		while (l < r) {
			int m = l + (r - l) / 2;
			if(nums[m]==target){
				return true;
			}
			if (nums[m] > nums[l]) {
				if (target <= nums[m] && target >= nums[l])
					r = m;
				else
					l = m + 1;
			} else if (nums[m] < nums[l]){
				if (target > nums[m] && target <= nums[r])
					l = m + 1;
				else
					r = m;
			} else {
				l++;
//				if(m==l){
//					l = m+1;
//				}else{
//					if (target > nums[m] && target <= nums[r])
//						l = m + 1;
//					else
//						r = m;
//				}
			}
		}
		return false;
//		idx = nums[l] == target ? l : -1;
//		if (idx != -1) {
//			return true;
//		}
//		return false;
	}

	public static void main(String[] args) {
//		int[] nums = new int[] { 1, 3, 1, 1, 1 };
		int[] nums = new int[] { 1, 1, 3, 1};
//		int[] nums = new int[] {3, 1};
		int target = 3;
		System.out.println(new Solution2().search(nums, target));
		;
	}
}
