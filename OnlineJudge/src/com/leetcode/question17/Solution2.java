package com.leetcode.question17;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
	public List<String> letterCombinations(String digits) {
		List<String> result = new ArrayList<String>();
		if (digits.isEmpty()) {
			result.add("");
			return result;
		}

		String[] map = new String[10];
		map[0] = "";
		map[1] = "";
		map[2] = "abc";
		map[3] = "def";
		map[4] = "ghi";
		map[5] = "jkl";
		map[6] = "mno";
		map[7] = "pqrs";
		map[8] = "tuv";
		map[9] = "wxyz";

		int[] number = new int[digits.length()]; // 存储digits中每个字符在循环中的编号，初始为0

		int k = digits.length() - 1;
		while (k >= 0) {
			k = digits.length() - 1;
			char[] charTemp = new char[digits.length()];
			for (int i = 0; i < digits.length(); i++) {
				charTemp[i] = map[digits.charAt(i) - '0'].charAt(number[i]);
			}
			result.add(new String(charTemp));
			while (k >= 0) {
				if (number[k] < (map[digits.charAt(k) - '0'].length() - 1)) {
					number[k]++;
					break;
				} else {
					number[k] = 0;
					k--;
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println(new Solution2().letterCombinations("23"));
		;
	}
}
