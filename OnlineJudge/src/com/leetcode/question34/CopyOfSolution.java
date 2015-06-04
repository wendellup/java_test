package com.leetcode.question34;

import java.util.Arrays;

public class CopyOfSolution {
	public int[] searchRange(int[] nums, int target) {
		int[] retAry = new int[]{-1,-1};
		int l = 0;
		int r = nums.length - 1;
		
		
		int mid = (l+r)/2;
		while(nums[mid] != target){
			if(nums[mid]<target){
				l = mid+1;
			}else{
				r = mid-1;
			}
			if(l>r){
				return retAry;
			}
			mid = (l+r)/2;
//			if((l==r || l==(r-1))){
//				if(nums[l] != target && nums[r] != target){
//					return new int[]{-1, -1};
//				}else if(nums[l] == target){
//					mid = l;
//					break;
//				}else{
//					mid = r;
//					break;
//				}
//			}
		}
		
		//查找左边界和右边界
		for(int hitL=mid; hitL>=0; hitL--){
			if(nums[hitL]==target){
				if(hitL == 0){
					retAry[0] = 0;
					break;
				}else{
					continue;
				}
			}else{
				retAry[0] = hitL+1;
				break;
			}
		}
		for(int hitR=mid; hitR<=nums.length-1; hitR++){
			if(nums[hitR]==target){
				if(hitR == nums.length-1){
					retAry[1] = nums.length-1;
					break;
				}else{
					continue;
				}
			}else{
				retAry[1] = hitR-1;
				break;
			}
		}
		return retAry;
	}
	
	public static void main(String[] args) {
//		int[] nums = {5, 7, 7, 8, 8, 10};
//		int target = 8;
		
//		int[] nums = {5, 7, 7, 8, 8, 10};
//		int target = 6;
		
		int[] nums = {1, 4};
		int target = 4;
		
		int[] retAry = new CopyOfSolution().searchRange(nums, target);
		System.out.println(Arrays.toString(retAry));
	}
}
