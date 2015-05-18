package com.leetcode.question26;

import java.util.Arrays;

public class Solution {
	
	public int removeDuplicates(int[] nums) {
		if(nums.length==0){
			return 0;
		}
		int retNum = 1;
		int preNum = nums[0];
		int currNum = nums[0];
		int preNumIdx = 0;
		for(int i=0; i<nums.length; i++){
			currNum = nums[i];
			if(preNum==currNum){
				continue;
			}else{
				preNum = currNum;
				preNumIdx++;
				nums[preNumIdx] = currNum;
				retNum++;
			}
		}
		
        return retNum;
    }
	
	public static void main(String[] args) {
//		int[] nums = {1,1,2,3,3,3};
		int[] nums = {1,2,3,3,4};
		System.out.println(new Solution().removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
	}
}
