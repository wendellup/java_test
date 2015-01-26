package com.leetcode.question5;

import sun.util.logging.resources.logging;

public class Solution {
	
    public String longestPalindrome(String s) {
    	String longestPalindromeStr = "";
        for(int i=0; i<s.length()-1; i++){
        	char curChar = s.charAt(i);
        	char nextChar = s.charAt(i+1);
        	if(curChar == nextChar){
        		if(longestPalindromeStr.length()<=2){
        			longestPalindromeStr = s.substring(i, i+2);
        		}
        		for(int step=1; i-step>=0&&i+step+1<s.length(); step++){
        			curChar = s.charAt(i-step);
                	nextChar = s.charAt(i+step+1);
                	if(curChar==nextChar){
                		if(longestPalindromeStr.length()<=(1+step)*2){
                			longestPalindromeStr = s.substring(i-step, i+step+2);
                		}
                		continue;
                	}else{
                		break;
                	}
        		}
        	}
        }
        
        for(int i=0; i<s.length()-2; i++){
        	char curChar = s.charAt(i);
        	char nextChar = s.charAt(i+2);
        	if(curChar == nextChar){
        		if(longestPalindromeStr.length()<=3){
        			longestPalindromeStr = s.substring(i, i+3);
        		}
        		for(int step=1; i-step>=0&&i+step+2<s.length(); step++){
        			curChar = s.charAt(i-step);
                	nextChar = s.charAt(i+step+2);
                	if(curChar==nextChar){
                		if(longestPalindromeStr.length()<=(1+step)*2+1){
                			longestPalindromeStr = s.substring(i-step, i+step+3);
                		}
                		continue;
                	}else{
                		break;
                	}
        		}
        	}
        }
        
        if(longestPalindromeStr.length()<=0){
        	return s.substring(s.length()-1, s.length());
        }else{
        	return longestPalindromeStr;
        }
    }
    
    public static void main(String[] args) {
//		String s1 = "xabccba12345654321cddc";
    	String s1 = "aaa";
		System.out.println(new Solution().longestPalindrome(s1));
	}
}