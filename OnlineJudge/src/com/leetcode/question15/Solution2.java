package com.leetcode.question15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Solution2 {
	
	public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		Set<List<Integer>> resultSet = new HashSet<List<Integer>>();
		
		if (num.length < 3)
			return result;
	 
		// sort array
		Arrays.sort(num);
	 
		for (int i = 0; i < num.length - 2; i++) {
			// //avoid duplicate solutions
//			if (i == 0 || num[i] > num[i - 1]) {
	 
				int negate = -num[i];
	 
				int start = i + 1;
				int end = num.length - 1;
	 
				while (start < end) {
					//case 1
					if (num[start] + num[end] == negate) {
						ArrayList<Integer> temp = new ArrayList<Integer>();
						temp.add(num[i]);
						temp.add(num[start]);
						temp.add(num[end]);
	 
						resultSet.add(temp);
						start++;
						end--;
						//avoid duplicate solutions
//						while (start < end && num[end] == num[end + 1])
//							end--;
//	 
//						while (start < end && num[start] == num[start - 1])
//							start++;
					//case 2
					} else if (num[start] + num[end] < negate) {
						start++;
					//case 3
					} else {
						end--;
					}
				}
	 
			}
//		}
	 
		result = new ArrayList(resultSet);
    	return result;
	}
    
    public static void main(String[] args) {
    	long beginMills = System.currentTimeMillis();
    	int[] array = {7,-1,14,-12,-8,7,2,-15,8,8,-8,-14,-4,-5,7,9,11,-4,-15,-6,1,-14,4,3,10,-5,2,1,6,11,2,-2,-5,-7,-6,2,-15,11,-6,8,-4,2,1,-1,4,-6,-15,1,5,-15,10,14,9,-8,-6,4,-6,11,12,-15,7,-1,-9,9,-1,0,-4,-1,-12,-2,14,-9,7,0,-3,-4,1,-2,12,14,-10,0,5,14,-1,14,3,8,10,-8,8,-5,-2,6,-11,12,13,-7,-12,8,6,-13,14,-2,-5,-11,1,3,-6};
		System.out.println(new Solution2().threeSum(array));;
		System.out.println("cost:"+(System.currentTimeMillis()-beginMills));
	}
}