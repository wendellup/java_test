package com.leetcode.question53;

public class Solution_分治 {
	public int maxSubArray(int[] A) {
        int[] sum = new int[A.length];
        
        int max = A[0];
        sum[0] = A[0];
 
        for (int i = 1; i < A.length; i++) {
            sum[i] = Math.max(A[i], sum[i - 1] + A[i]);
            max = Math.max(max, sum[i]);
        }
 
        return max;
    }
}