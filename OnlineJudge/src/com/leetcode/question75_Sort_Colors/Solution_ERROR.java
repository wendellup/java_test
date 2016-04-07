package com.leetcode.question75_Sort_Colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution_ERROR {
	public void sortColors(int[] nums) {
//		List<Integer> array0 = new ArrayList<Integer>();
//		List<Integer> array1 = new ArrayList<Integer>();
//		List<Integer> array2 = new ArrayList<Integer>();
//		List<List<Integer>> list = new ArrayList<List<Integer>>();
//		list.add(array0);
//		list.add(array1);
//		list.add(array2);
		System.out.println(Arrays.toString(nums));
		int[] cntAry = new int[3];
		
		for(int i=0; i<nums.length; i++){
			cntAry[nums[i]]++;
		}
		
		
		int curIdx = 0;
		for(int i=0; i<nums.length; i++){
			curIdx = curIdx%3;
			if(cntAry[curIdx]==0){
				curIdx = (curIdx+1)%3;
				if(cntAry[curIdx]==0){
					curIdx = (curIdx+1)%3;
				}
			}
			nums[i] = curIdx;
			cntAry[curIdx]--;
			curIdx++;
		}
		System.out.println(Arrays.toString(nums));
	}
	
	public static void main(String[] args) {
		int[] nums = new int []{0,1,2,0,1,0,2,1,0,0,1,0};
		new Solution_ERROR().sortColors(nums);
	}
}
