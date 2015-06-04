package com.leetcode.question35;

public class Solution {
	public int searchInsert(int[] nums, int target) {
		int idx = 0;
		if(target<=nums[0]){
			return 0;
		}else if(target>nums[nums.length-1]){
			return nums.length;
		}
		
		int l = 0;
		int r = nums.length-1;
		
		while(l<=r){
			int mid = (l+r)/2;
			int validMidIdx = isMidRightIdx(nums, mid, target);
			if(validMidIdx>0){
				return validMidIdx;
			}
			if(nums[mid]>=target){
				r = mid-1;
			}else{
				l = mid+1;
			}
			
		}
		
		
        return idx;
    }

	private int isMidRightIdx(int[] nums, int mid, int target) {
		if(nums[mid]>=target && nums[mid-1]<target){
			return mid;
		}else if(nums[mid]<target && nums[mid+1]>=target){
			return mid+1;
		}else{
			return -1;
		}
	}
}
