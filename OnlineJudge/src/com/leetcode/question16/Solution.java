package com.leetcode.question16;

import java.util.Arrays;


public class Solution {
	
	public int threeSumClosest(int[] num, int target) {
//		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		
		// sort array
		
		Arrays.sort(num);
		int close = num[0] + num[1] + num[2];
	 
		for (int i = 0; i < num.length - 2; i++) {
			// //avoid duplicate solutions
			if (i == 0 || num[i] > num[i - 1]) {
//				int negate = -num[i];
	 
				int start = i + 1;
				int end = num.length - 1;
	 
				while (start < end) {
					//case 1
					if (Math.abs(num[start] + num[end] + num[i] - target) < Math.abs(close - target) ) {
						close = num[start] + num[end] + num[i];
						if(close-target==0){
							return close;
						}
					}
						//avoid duplicate solutions
//						while (start < end && num[end] == num[end + 1])
//							end--;
//	 
//						while (start < end && num[start] == num[start - 1])
//							start++;
					//case 2
					if (num[start] + num[end] + num[i] - target < 0) {
						start++;
					//case 3
					} else {
						end--;
					}
				}
	 
			}
		}
	 
		return close;
	}
    
    public static void main(String[] args) {
    	long beginMills = System.currentTimeMillis();
    	int[] array = {1,2,4,8,16,32,64,128};
    	
//    	i=1, begin=4, end=6
    	
		System.out.println(new Solution().threeSumClosest(array, 82));;
		System.out.println("cost:"+(System.currentTimeMillis()-beginMills));
		
	}
}