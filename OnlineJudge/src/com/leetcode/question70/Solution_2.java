package com.leetcode.question70;

//归纳的动态规划,依然超时
//f(n)=f(n-1)+f(n-2)
public class Solution_2 {
	public int climbStairs(int n) {
		return handle(n);
    }
	
	private int handle(int n) {
		if(n==0){
			return 0;
		}
		if(n==1){
			return 1;
		}
		if(n==2){
			return 2;
		}
		return handle(n-1)+handle(n-2);
	}


	public static void main(String[] args) {
		System.out.println(new Solution_2().climbStairs(7));
	}
}
