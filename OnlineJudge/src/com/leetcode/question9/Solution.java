package com.leetcode.question9;



public class Solution {
	
	public boolean isPalindrome(int x) {
		String xStr = x+"";
		int validLen = 0;
		if(xStr.length()%2==0){
			validLen = xStr.length()/2;
		}else{
			validLen = (xStr.length()+1)/2;
		}
		boolean isPalindrome = true;
		for(int i=0; i<validLen; i++){
			char ascChar = xStr.charAt(i);
			char descChar = xStr.charAt(xStr.length()-1-i);
			if(ascChar!=descChar){
				isPalindrome = false;
				break;
			}
		}
        return isPalindrome;
    }
    
    public static void main(String[] args) {
    	System.out.println(new Solution().isPalindrome(11011));;
	}
}