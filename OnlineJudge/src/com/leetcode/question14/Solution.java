package com.leetcode.question14;


public class Solution {
	public String longestCommonPrefix(String[] strs) {
		if(strs.length==0){
			return "";
		}
        String commonPrefix = "";
        
        String shortestStr = strs[0];
        for(String str : strs){
        	if(str.length()<shortestStr.length()){
        		shortestStr = str;
        	}
        }
        
        for(int i=0; i<shortestStr.length(); i++){
        	String testStr = shortestStr.substring(0, i+1);
        	boolean isMatch = true;
        	for(int idx=0; idx<strs.length; idx++){
        		if(!strs[idx].startsWith(testStr)){
        			isMatch = false;
        			break;
        		}
        	}
        	if(isMatch){
        		commonPrefix = testStr;
        	}else{
        		break;
        	}
        }
        
        return commonPrefix;
    }

	public static void main(String[] args) {
//		String[] strs = {"abbb","abcd","abce"};
		String[] strs = {"aa"};
		System.out.println(new Solution().longestCommonPrefix(strs));
	}

}