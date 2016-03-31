package com.leetcode.question71_Simplify_Path;

import java.util.LinkedList;

/**
 * 参考文章:http://blog.csdn.net/linhuanmars/article/details/23972563
 * @author yuchao
 *
 */
public class Solution {
	public String simplifyPath(String path) {
		if (path == null || path.length() == 0) {
			return "";
		}
		LinkedList<String> stack = new LinkedList<String>();
		StringBuilder res = new StringBuilder();
		int i = 0;

		while (i < path.length()) {
			int index = i;
			StringBuilder temp = new StringBuilder();
			while (i < path.length() && path.charAt(i) != '/') {
				temp.append(path.charAt(i));
				i++;
			}
			if (index != i) {
				String str = temp.toString();
				if (str.equals("..")) {
					if (!stack.isEmpty())
						stack.pop();
				} else if (!str.equals(".")) {
					stack.push(str);
				}
			}
			i++;
		}
		if (!stack.isEmpty()) {
			String[] strs = stack.toArray(new String[stack.size()]);
			for (int j = strs.length - 1; j >= 0; j--) {
				res.append("/" + strs[j]);
			}
		}
		if (res.length() == 0)
			return "/";
		return res.toString();
	}
	
	public static void main(String[] args) {
		String path = "/a/./b/../../c/";
		String finalStr = new Solution().simplifyPath(path);
		System.out.println(finalStr);
	}
}
