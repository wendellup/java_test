package com.leetcode.question81_Search_in_Rotated_Sorted_Array_II;
public class Solution {
    public boolean search(int[] nums, int target) {
    	int idx = -1;
		
		for(int i=0; i<nums.length; i++){
			if(nums[i] == target){
				idx = i;
				break;
			}
		}
		if(idx!=-1){
			return true;
		}
		return false;
    }
}
