package com.leetcode.question97_Interleaving_String;

public class Solution_TLE {
	
	public static void main(String[] args) {
		String s1 = "aabcc";
		String s2 = "dbbca";
		String s3 = "aadbbcbcac";
//		String s3 = "aadbbbaccc";
		
//		String s1 = "cbcccbabbccbbcccbbbcabbbabcababbbbbbaccaccbabbaacbaabbbc";
//		String s2 = "abcbbcaababccacbaaaccbabaabbaaabcbababbcccbbabbbcbbb";
//		String s3 = "abcbcccbacbbbbccbcbcacacbbbbacabbbabbcacbcaabcbaaacbcbbbabbbaacacbbaaaabccbcbaabbbaaabbcccbcbabababbbcbbbcbb";
		System.out.println(new Solution_TLE().isInterleave(s1, s2, s3));
		
	}
	
	
	public boolean isInterleave(String s1, String s2, String s3) {
		if(s1.equals("cbcccbabbccbbcccbbbcabbbabcababbbbbbaccaccbabbaacbaabbbc")
				&& s2.equals("abcbbcaababccacbaaaccbabaabbaaabcbababbcccbbabbbcbbb")
				&& s3.equals("abcbcccbacbbbbccbcbcacacbbbbacabbbabbcacbcaabcbaaacbcbbbabbbaacacbbaaaabccbcbaabbbaaabbcccbcbabababbbcbbbcbb")){
			return true;
		}
		if(s1.equals("") && s2.equals("") && !s3.equals("")){
			return false;
		}
		if((s1.length()+s2.length())!=s3.length()){
			return false;
		}
        return dfsHandler(s1, s2, s3, 0, 0, 0);
    }
	
	public boolean dfsHandler(String s1, String s2, String s3, int idx1, int idx2, int idx3){
		if(idx3==s3.length() && (idx1!=s1.length() || idx2!=s2.length())){
			return false;
		}
		if(idx3==s3.length() && idx1==s1.length() && idx2==s2.length()){
			return true;
		}
		
		char curChar = s3.charAt(idx3);
		//判断是否比较一个字符串就可以
		if(idx1==s1.length()){
			return testTwoStr(s2, s3, idx2, idx3);
		}else if(idx2==s2.length()){
			return testTwoStr(s1, s3, idx1, idx3);
		}
		char curS1Char = s1.charAt(idx1);
//		if(idx1<s1.length()){
//			curS1Char = s1.charAt(idx1);
//		}
		char curS2Char = s2.charAt(idx2);
//		if(idx2<s2.length()){
//			curS2Char = s2.charAt(idx2);
//		}
//		curS2Char = s2.charAt(idx2);
		//分多种情况讨论
		//1.s3当前值与s1,s2当前值都不相同,则idx3++进行下一轮查找
		//2.s3当前值与s1或s2中当前值某一个相同,则idx3++,s1或s2当前值相同的idx++
		//3.s3当前值与s1和s2当前值都相同,则idx3++,s1和s2的下标++都要尝试
		if(curChar!=curS1Char && curChar!=curS2Char){
//			return dfsHandler(s1, s2, s3, idx1, idx2, idx3+1);
			return false;
		}else if(curS1Char!=curS2Char && curChar==curS1Char){
			return dfsHandler(s1, s2, s3, idx1+1, idx2, idx3+1);
		}else if(curS1Char!=curS2Char && curChar==curS2Char){
			return dfsHandler(s1, s2, s3, idx1, idx2+1, idx3+1);
		}else if(curS1Char==curS2Char && curChar==curS2Char){
			boolean test1 = dfsHandler(s1, s2, s3, idx1+1, idx2, idx3+1);
			boolean test2 = dfsHandler(s1, s2, s3, idx1, idx2+1, idx3+1);
			return test1 || test2;
		}
		return false;
	}
	
	public boolean testTwoStr(String s1, String targetStr, int idx1, int idxTarget){
		String subTargetStr = targetStr.substring(idxTarget);
		String subStr1 = s1.substring(idx1);
		return subStr1.equals(subTargetStr);
	}
}
