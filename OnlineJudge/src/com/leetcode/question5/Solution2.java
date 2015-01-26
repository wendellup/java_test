package com.leetcode.question5;

import sun.util.logging.resources.logging;

public class Solution2 {
	
    public String longestPalindrome(String s) {
    	String longestPalindromeStr = "";
        for(int i=0; i<s.length()-2; i++){
        	char curChar = s.charAt(i);
        	char nextChar = s.charAt(i+1);
        	char next2Char = s.charAt(i+2);
        	if(curChar == nextChar){
        		if(longestPalindromeStr.length()<=2){
        			longestPalindromeStr = s.substring(i, i+2);
        		}
        		for(int step=1; i-step>=0&&i+step+1<s.length(); step++){
        			char localCurChar = s.charAt(i-step);
        			char localNextChar = s.charAt(i+step+1);
//        			curChar = s.charAt(i-step);
//                	nextChar = s.charAt(i+step+1);
                	if(localCurChar==localNextChar){
                		if(longestPalindromeStr.length()<=(1+step)*2){
                			longestPalindromeStr = s.substring(i-step, i+step+2);
                		}
                		continue;
                	}else{
                		break;
                	}
        		}
        	}
        	if(curChar == next2Char){
        		if(longestPalindromeStr.length()<=3){
        			longestPalindromeStr = s.substring(i, i+3);
        		}
        		for(int step=1; i-step>=0&&i+step+2<s.length(); step++){
//        			curChar = s.charAt(i-step);
//                	nextChar = s.charAt(i+step+2);
                	char localCurChar = s.charAt(i-step);
        			char localNextChar = s.charAt(i+step+2);
                	if(localCurChar==localNextChar){
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
        if(s.length()>=2){
        	char endChar = s.charAt(s.length()-1);
        	char secondEndChar = s.charAt(s.length()-2);
        	if(endChar==secondEndChar && longestPalindromeStr.length()<=1){
        		return s.substring(s.length()-2, s.length());
        	}
        }
        
        if(longestPalindromeStr.length()<=0){
        	return s.substring(s.length()-1, s.length());
        }else{
        	return longestPalindromeStr;
        }
    }
    
    public static void main(String[] args) {
		String s1 = "xabccba12345654321cddc";
//    	String s1 = "azwdocbppbxlnpyyrgdsooosgqliphnlehh";
//    	String s1 = "ocbppbxdsooosg";
//    	String s1 = "aaa";
		System.out.println(new Solution2().longestPalindrome(s1));
	}
}