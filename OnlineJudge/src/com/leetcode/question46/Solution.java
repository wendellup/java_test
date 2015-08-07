package com.leetcode.question46;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> retList = new ArrayList<List<Integer>>();
        if(nums==null || nums.length==0){
        	return retList;
        }
        boolean[] numsUsedFlag = new boolean[nums.length];
        
        List<Integer> solveList = new ArrayList<Integer>();
        
        handler(nums, numsUsedFlag, retList, solveList);
        
        return retList;
    }

	private void handler(int[] nums, boolean[] numsUsedFlag, List<List<Integer>> retList,
			List<Integer> solveList) {
		if(solveList.size() == nums.length){
			retList.add(new ArrayList<Integer>(solveList));
		}

		for(int i=0; i<nums.length; i++){
			if(numsUsedFlag[i] == false){
				numsUsedFlag[i] = true;
				solveList.add(nums[i]);
				handler(nums, numsUsedFlag, retList, solveList);
				solveList.remove(solveList.size()-1);
				numsUsedFlag[i] = false;
			}
		}
	}
	
	public static void main(String[] args) {
		int nums[] = new int[]{1, 2, 2};
		System.out.println(new Solution().permute(nums));
	}
}
