package com.leetcode.question35;

public class CopyOfSolution {
	public int searchInsert(int[] nums, int target) {
		int idx = 0;
		if(target<=nums[0]){
			return 0;
		}else if(target>nums[nums.length-1]){
			return nums.length;
		}
		
		for(int i=0; i<=nums.length-1; i++){
			if(nums[i]<target){
				continue;
			}else{
				idx = i;
				break;
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
