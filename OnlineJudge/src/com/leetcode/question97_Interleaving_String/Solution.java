package com.leetcode.question97_Interleaving_String;

public class Solution {
	// 用dp方式求解,不会超时
	// 参考url:
	// http://www.cnblogs.com/remlostime/archive/2012/11/25/2787297.html
	// http://blog.csdn.net/linhuanmars/article/details/24683159

	public boolean isInterleave(String s1, String s2, String s3) {
		boolean f[][] = new boolean[1024][1024];
		// Start typing your C/C++ solution below
		// DO NOT write int main() function
		if (s1.length() + s2.length() != s3.length())
			return false;

		f[0][0] = true;
		for (int i = 1; i <= s1.length(); i++)
			f[i][0] = f[i - 1][0] && (s3.charAt(i - 1) == s1.charAt(i - 1));

		for (int j = 1; j <= s2.length(); j++)
			f[0][j] = f[0][j - 1] && (s3.charAt(j - 1) == s2.charAt(j - 1));

		for (int i = 1; i <= s1.length(); i++)
			for (int j = 1; j <= s2.length(); j++)
				f[i][j] = (f[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1))
						|| (f[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1));

		return f[s1.length()][s2.length()];
	}
}
