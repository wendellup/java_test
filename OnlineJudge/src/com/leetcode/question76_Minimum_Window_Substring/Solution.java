package com.leetcode.question76_Minimum_Window_Substring;

/**
 * 参考文章:https://leetcode.com/discuss/90376/o-n-5ms-java-solution-beats-93-18%25
 * @author yuchao
 *
 */
public class Solution {
	public String minWindow(String s, String t) {
//		char[] needToFind = new char[256];
//		char[] hasFound = new char[256];
		int[] needToFind = new int[256];
		int[] hasFound = new int[256];
		int sLen = s.length();
		int tLen = t.length();
		int count = 0;
		int optLen = Integer.MAX_VALUE; // opt stands for optimal
		int optBegin = 0;
		int optEnd = 0;
		for (int i = 0; i < tLen; i++) { // gives a counter for each character
											// in t
			needToFind[t.charAt(i)]++;
		}
		for (int begin = 0, end = 0; end < sLen; end++) {
			if (needToFind[s.charAt(end)] == 0) { // skips irrelevant char
				continue;
			}
			char currEnd = s.charAt(end); // the char at the end
			hasFound[currEnd]++;
			if (hasFound[currEnd] <= needToFind[currEnd]) {
				count++;
			}
			if (count == tLen) { // pauses end, moves beginning to the right as
									// much as possible
				char currBegin = s.charAt(begin); // char at begin
				while (hasFound[currBegin] > needToFind[currBegin]
						|| needToFind[currBegin] == 0) {
					if (hasFound[currBegin] > needToFind[currBegin]) {
						hasFound[currBegin]--;
					}
					begin++;
					currBegin = s.charAt(begin);
				}
				if (optLen > end - begin + 1) { // if current length is smaller,
												// update our optimum solution
					optLen = end - begin + 1;
					optBegin = begin;
					optEnd = end;
				}
			}
		}
		if (count != tLen) {
			return "";
		}
		return s.substring(optBegin, optEnd + 1);
	}
	
	public static void main(String[] args) {
		String S = "AA";
	    String T = "AD";
		
		System.out.println(new Solution().minWindow(S, T));;
	}
}