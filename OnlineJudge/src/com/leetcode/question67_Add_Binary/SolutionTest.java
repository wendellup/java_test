package com.leetcode.question67_Add_Binary;

public class SolutionTest {
	public String addBinary(String a, String b) {
		// 转换成10进制加减再返回,会报转换错误
		long aLong = Long.parseLong(a, 2);
		long bLong = Long.parseLong(b, 2);
		long cntInt = aLong + bLong;
		return Long.toString(cntInt, 2);
	}

	public static void main(String[] args) {

		System.out.println(new SolutionTest().addBinary("10","11"));
	}
}