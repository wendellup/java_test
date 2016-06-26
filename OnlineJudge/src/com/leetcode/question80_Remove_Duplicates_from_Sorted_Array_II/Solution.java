package com.leetcode.question80_Remove_Duplicates_from_Sorted_Array_II;

import java.util.Arrays;

public class Solution {
	// Manipulate original array
	public static int removeDuplicatesNaive(int[] nums) {
		if (nums.length <= 2)
			return nums.length;
	 
		int currentRepeat = 0;
		int j = 0;
		int i = 1;
//		int pre = nums[j];
	 
		while (i < nums.length) {
			if (nums[i] == nums[j]) {
				if(currentRepeat<1){
					currentRepeat++;
					//填充当前j位置后的数据,否则返回的数组不正确
					nums[j+currentRepeat] = nums[i];
					i++;
				}else{
					i++;
				}
			} else {
				j = j+currentRepeat+1;
				nums[j] = nums[i];
				i++;
				currentRepeat=0;
			}
		}
	 
		//最后一个元素判断
		if(currentRepeat>=1){
			nums[j+1] = nums[nums.length-1];
			return j+2;
		}else{
			return j+1;
		}
	}
	public static void main(String[] args) {
//		int[] array = new int[]{1,1,1,1,2,2,3,5,5};
//		int[] array = new int[]{1,1,1,1,2,2,3};
//		int[] array = new int[]{1,1,1,2,2,2,3,5,5};
		int[] array = new int[]{1,1,1,3,3,3};
		System.out.println(Solution.removeDuplicatesNaive(array));
		System.out.println(Arrays.toString(array));
	}
}

