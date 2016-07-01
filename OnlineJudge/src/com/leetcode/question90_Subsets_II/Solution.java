package com.leetcode.question90_Subsets_II;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		Set<List<Integer>> listTemp = new HashSet<List<Integer>>();
	    Arrays.sort(nums);
	    backtrack(listTemp, new ArrayList<Integer>(), nums, 0);
	    list = new ArrayList<List<Integer>>(listTemp);
	    return list;
	}

	private void backtrack(Set<List<Integer>> listTemp , List<Integer> tempList, int [] nums, int start){
		listTemp.add(new ArrayList<Integer>(tempList));
	    for(int i = start; i < nums.length; i++){
	        tempList.add(nums[i]);
	        backtrack(listTemp, tempList, nums, i + 1);
	        tempList.remove(tempList.size() - 1);
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(new Solution().subsetsWithDup(new int[]{3,2,1,1}));;
	}
}
