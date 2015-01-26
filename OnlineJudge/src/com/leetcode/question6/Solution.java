package com.leetcode.question6;


public class Solution {
    public String convert(String s, int nRows) {
    	int sLen = s.length()/nRows + 1;
    	char[][] charAry = new char[nRows][sLen];
    	for(int i=0, j=0, l=0; i<s.length(); l++){
    		charAry[i][j] = s.charAt(l);
    		i=i++%nRows;
    		
    		
    	}
    	
        return "";
    }
}