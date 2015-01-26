package com.leetcode.question6;


public class Solution {
    public String convert(String s, int nRows) {
    	if(nRows==1){
    		return s;
    	}
    	char[][] charAry = new char[nRows][s.length()];
    	boolean iUp = true;
    	boolean jUp = false;
    	for(int i=0, j=0, l=0; l<s.length(); l++){
    		charAry[i][j] = s.charAt(l);
    		//获取纵坐标位置
    		if(jUp){
    			j++;
    		}
    		//获取横坐标位置
    		if(iUp){
    			i++;
    			if(i==nRows-1){
    				iUp = false;
    				jUp = true;
    			}
    		}else if(!iUp){
    			i--;
    			if(i==0){
    				iUp = true;
    				jUp = false;
    			}
    		}
    	}
    	
    	String retStr = "";
    	
    	for(int i=0; i<nRows; i++){
    		for(int j=0; j<s.length(); j++){
    			if(charAry[i][j]!=0){
    				retStr = retStr+charAry[i][j];
    			}
    		}
    	}
    	
        return retStr;
    }
    
    public static void main(String[] args) {
//    	System.out.println(new Solution().convert("PAYPALISHIRING", 3));;
//    	System.out.println(new Solution().convert("PAYPALISHIRING", 1));;
    	System.out.println(new Solution().convert("", 2));;
	}
}