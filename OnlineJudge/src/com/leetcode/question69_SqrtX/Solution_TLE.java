package com.leetcode.question69_SqrtX;
public class Solution_TLE {
    public int mySqrt(int x) {
    	int halfX = x/2;
    	for(int i=0; i<=halfX; i++){
    		int Val = i*i;
    		if(Val==x){
    			return i;
    		}
    	}
    	return x;
    }
    
    public static void main(String[] args) {
    	System.out.println("xxx");
		System.out.println(new Solution_TLE().mySqrt(1341234234));
	}
}