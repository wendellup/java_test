package com.leetcode.question27;

import java.util.Arrays;


public class Solution {
    public int removeElement(int[] nums, int val) {
    	if(nums.length==0){
			return 0;
		}
		int retNum = 0;
		int currNum = nums[0];
		int newCurNumIdx = 0;
		for(int i=0; i<nums.length; i++){
			currNum = nums[i];
			if(val==currNum){
				continue;
			}else{
				nums[newCurNumIdx] = currNum;
				newCurNumIdx++;
				retNum++;
			}
		}
		
        return retNum;
    }
    
    public static void main(String[] args) {
		int[] nums = {1,1,2,3,3,3};
//		int[] nums = {1,2};
		System.out.println(new Solution().removeElement(nums,2));
		System.out.println(Arrays.toString(nums));
	}
}