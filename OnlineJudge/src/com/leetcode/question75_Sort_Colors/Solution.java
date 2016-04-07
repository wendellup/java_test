package com.leetcode.question75_Sort_Colors;

import java.util.Arrays;

public class Solution {
	public void sortColors(int[] nums) {
		System.out.println(Arrays.toString(nums));
		int[] cntAry = new int[3];
		
		for(int i=0; i<nums.length; i++){
			cntAry[nums[i]]++;
		}
		
		
		int curIdx = 0;
		for(int i=0; i<nums.length; i++){
			if(cntAry[curIdx]==0){
				while(curIdx<3){
						curIdx++;
						if(cntAry[curIdx]!=0){
							break;
						}
				}
			}
//			
//			curIdx = curIdx%3;
//			if(cntAry[curIdx]==0){
//				curIdx = (curIdx+1)%3;
//				if(cntAry[curIdx]==0){
//					curIdx = (curIdx+1)%3;
//				}
//			}
			nums[i] = curIdx;
			cntAry[curIdx]--;
		}
		System.out.println(Arrays.toString(nums));
	}
	
	public static void main(String[] args) {
		int[] nums = new int []{0,1,2,0,1,0,2,1,0,0,1,0};
		new Solution().sortColors(nums);
	}
}
