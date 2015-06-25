package com.leetcode.question41;

public class Solution {
	public int firstMissingPositive(int[] nums) {
		if(nums.length==0){
			return 1;
		}
		int[] sortAry = new int[nums.length+1];
		for(int i=0; i<nums.length; i++){
			int num = nums[i];
			if(num>0 && num<=nums.length){
				sortAry[num]++;
			}
		}
		for(int i=1; i<=nums.length; i++){
			if(sortAry[i]==0){
				return i;
			}
		}
		
        return nums.length+1;
    }
	
	public static void main(String[] args) {
		int[] aryInt = new int[]{1,2,0};
//		int[] aryInt = new int[]{-1};
//		int[] aryInt = new int[]{2};
//		int[] aryInt = new int[]{1};
		
		System.out.println(new Solution().firstMissingPositive(aryInt));
	}
}
