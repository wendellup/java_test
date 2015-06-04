package com.leetcode.question33;

import java.util.Stack;

public class Solution {
	
	public int search(int[] nums, int target) {
		int idx = -1;
		
		for(int i=0; i<nums.length; i++){
			if(nums[i] == target){
				idx = i;
				break;
			}
		}
		
		return idx;
	}
	
}
