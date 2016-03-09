package com.leetcode.question66_Plus_One;

import java.util.Arrays;

public class Solution2 {
    public int[] plusOne(int[] digits) {
    	int length = digits.length;
    	if(length==0){
    		return new int[]{1};
    	}
    	
    	boolean isCarray = false;
    	if(digits[digits.length-1]<9){
    		digits[digits.length-1] = digits[digits.length-1]+1;
    	}else{
    		for(int i=digits.length-1; i>=0; i--){
    			if(digits[i]==9){
    				digits[i]=0;
    				if(i==0){
    					isCarray = true;
    				}
    			}else{
    				digits[i]=digits[i]+1;
    				break;
    			}
    		}
    	}
    	
    	if(isCarray){
    		int[] handleAry = new int[length+1];
        	for(int i=0; i<length; i++){
        		handleAry[i+1] = digits[i];
        	}
        	handleAry[0] = 1;
        	return handleAry;
    	}else{
    		return digits;
    	}
    }
    
    public static void main(String[] args) {
    	int[] digitals = new int[]{1,9,9};
		System.out.println(Arrays.toString(new Solution2().plusOne(digitals)));
		System.out.println(digitals);;
	}
}