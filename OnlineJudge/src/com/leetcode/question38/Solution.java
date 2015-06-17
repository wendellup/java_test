package com.leetcode.question38;

public class Solution {
//	1, 11, 21, 1211, 111221
    public String countAndSay(int n) {
    	String str = "1";
    	for(int i=1; i<n; i++){
    		str = getStrCnt(str);
    	}
        return str;
    }
    
    public static String getStrCnt(String str){
    	StringBuilder sb = new StringBuilder();
//    	String retStr = "";
    	char preChar = '.';
    	int cnt = 0;
    	for(int i=0; i<str.length(); i++){
    		char c = str.charAt(i);
    		if(preChar==c || preChar=='.'){
    			cnt++;
    		}else{
    			sb.append(cnt);
    			sb.append(preChar);
    			cnt = 1;
    		}
    		preChar = c;
    	}
    	
    	sb.append(cnt);
		sb.append(preChar);
    	return sb.toString();
    }
    
    public static void main(String[] args) {
		System.out.println(new Solution().countAndSay(1));
		System.out.println(new Solution().countAndSay(2));
		System.out.println(new Solution().countAndSay(3));
		System.out.println(new Solution().countAndSay(4));
		System.out.println(new Solution().countAndSay(5));
//    	System.out.println(Solution.getStrCnt("1"));
	}
}