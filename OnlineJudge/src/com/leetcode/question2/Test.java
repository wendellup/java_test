package com.leetcode.question2;

public class Test {
	public int findMin(int[] num) {
        if(num.length == 1){
        	return num[0];
        }
        
        int l = num[0];
        int r = num[num.length-1];
        
        if(l < r){
        	return l;
        }
        
        while(l != r){
        	
        }
        
        return 0;
    }
}
