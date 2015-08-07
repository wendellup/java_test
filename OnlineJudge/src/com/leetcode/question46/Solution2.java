package com.leetcode.question46;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (nums == null || nums.length == 0)
			return res;
		ArrayList<Integer> first = new ArrayList<Integer>();
		first.add(nums[0]);
		res.add(first);
		for (int i = 1; i < nums.length; i++) {
			List<List<Integer>> newRes = new ArrayList<List<Integer>>();
			for (int j = 0; j < res.size(); j++) {
				List<Integer> cur = res.get(j);
				for (int k = 0; k < cur.size() + 1; k++) {
					ArrayList<Integer> item = new ArrayList<Integer>(cur);
					item.add(k, nums[i]);
					newRes.add(item);
				}
			}
			res = newRes;
		}
		return res;
	}
	
	public static void main(String[] args) {
		int nums[] = new int[]{0, 1};
		System.out.println(new Solution2().permute(nums));
	}
}
