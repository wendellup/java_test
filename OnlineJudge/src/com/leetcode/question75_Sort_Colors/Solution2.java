package com.leetcode.question75_Sort_Colors;

import java.util.Arrays;


public class Solution2 {
	
	
	
	void sortColors(int nums[]) {
		int r = -1;
		int b = nums.length;
		for (int i = 0; i < b; i++) {
			if (nums[i] == 0) {
				swap(nums, ++r, i);
			} else if (nums[i] == 2) {
				swap(nums, --b, i);
				i--;
			}
		}
	}

	void swap(int A[], int x, int y) {
		int tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
	}

}
