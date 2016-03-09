package com.leetcode.question66_Plus_One;

import java.util.Arrays;

public class Solution {
    public int[] plusOne(int[] digits) {
    	int length = digits.length;
    	if(length==0){
    		return new int[]{1};
    	}
    	
    	//扩充1位
    	int[] handleAry = new int[length+1];
    	for(int i=0; i<length; i++){
    		handleAry[i+1] = digits[i];
    	}
    	handleAry[0] = 0;
    	
    	if(handleAry[handleAry.length-1]<9){
    		handleAry[handleAry.length-1] = handleAry[handleAry.length-1]+1;
    	}else{
    		for(int i=handleAry.length-1; i>=0; i--){
    			if(handleAry[i]==9){
    				handleAry[i]=0;
    			}else{
    				handleAry[i]=handleAry[i]+1;
    				break;
    			}
    		}
    	}
    	
    	if(handleAry[0]>0){
    		return handleAry;
    	}else{
    		
    		for(int i=1; i<handleAry.length; i++){
    			digits[i-1] = handleAry[i];
        	}
    		return digits;
    	}
    }
    
    public static void main(String[] args) {
    	int[] digitals = new int[]{9,9,9};
		System.out.println(Arrays.toString(new Solution().plusOne(digitals)));
		System.out.println(digitals);;
	}
}