package com.leetcode.question70;

//归纳的动态规划,数组方式实现
//f(n)=f(n-1)+f(n-2)
public class Solution_3 {
	public int climbStairs(int n) {
		if(n==0){
			return 0;
		}else if(n==1){
			return 1;
		}else if(n==2){
			return 2;
		}
		return handle(n);
    }
	
	private int handle(int n) {
		int[] array = new int[n];
		array[0] = 1;
		array[1] = 2;
		for(int i=2; i<n; i++){
			array[i] = array[i-1]+array[i-2];
		}
		return array[n-1];
	}
	
	public static void main(String[] args) {
		System.out.println(new Solution_3().climbStairs(7));
	}
}
