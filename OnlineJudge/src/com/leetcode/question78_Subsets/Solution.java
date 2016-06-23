package com.leetcode.question78_Subsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/discuss/104328/backtracking-permutations-combination-palindrome-partitioning
 * 解法:回朔法
 */
public class Solution {
	public List<List<Integer>> subsets(int[] nums) {
	    List<List<Integer>> list = new ArrayList<List<Integer>>();
	    Arrays.sort(nums);
	    backtrack(list, new ArrayList<Integer>(), nums, 0);
	    return list;
	}

	private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
	    list.add(new ArrayList<Integer>(tempList));
	    for(int i = start; i < nums.length; i++){
	        tempList.add(nums[i]);
	        backtrack(list, tempList, nums, i + 1);
	        tempList.remove(tempList.size() - 1);
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(new Solution().subsets(new int[]{3,2,1}));;
	}
}