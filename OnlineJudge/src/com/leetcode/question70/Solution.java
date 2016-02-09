package com.leetcode.question70;

//超时的动态规划
public class Solution {
	int total = 0;
	
	public int climbStairs(int n) {
		if(n==0){
			return 0;
		}
		handle(n);
        return total;
    }
	
	private void handle(int n) {
		dpHandle(0, 1, n);
		dpHandle(0, 2, n);
	}

	private void dpHandle(int curVal, int nexVal, int n) {
		curVal = curVal+nexVal;
		if(curVal==n){
			total++;
			return;
		}else if(curVal>n){
			return;
		}else{
			dpHandle(curVal, 1, n);
			dpHandle(curVal, 2, n);
		}
		
	}

	public static void main(String[] args) {
		System.out.println(new Solution().climbStairs(7));
	}
}
