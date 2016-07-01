package com.leetcode.question89_Gray_Code;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	public List<Integer> grayCode(int n) {
		List<Integer> grayCode = new ArrayList<Integer>();
		List<Long> grayCodeSwap = new ArrayList<Long>();
		grayCode.add(0);
		if(n==0){
			return grayCode;
		}
		grayCode.add(1);
		if(n==1){
			return grayCode;
		}
		grayCode = new ArrayList<Integer>();
		grayCodeSwap.add(0L);
		grayCodeSwap.add(1L);
		for(int i=2; i<=n; i++){
			int currentLen = grayCodeSwap.size();
//			for(int j=0; j<currentLen; j++){
//				grayCodeSwap.set(j, grayCodeSwap.get(j));
//			}
			for(int j=currentLen-1; j>=0; j--){
				grayCodeSwap.add((long)(Math.pow(10,i-1))+grayCodeSwap.get(j));
			}
		}
		convertGrayListToDecimal(grayCodeSwap, grayCode);
		//需要将二进制对应的数字串转换成十进制再返回
		return grayCode;
	}
	public static void main(String[] args) {
		System.out.println(new Solution().grayCode(12));;
//		System.out.println(convertBinaryToDecimal(100));
	}
	
	public static void convertGrayListToDecimal(List<Long> toConvertList, List<Integer> grayCode){
		for(int i=0; i<toConvertList.size(); i++){
			long curGrayCode = toConvertList.get(i);
//			toConvertList.set(i, convertBinaryToDecimal(curGrayCode));
			grayCode.add(convertBinaryToDecimal(curGrayCode));
		}
	}
	
	public static int convertBinaryToDecimal(long binary){
		long decimalVal = 0;
		int base = 1;
		while(binary/10!=0){
			long remainder = binary%2;
			decimalVal = remainder*base + decimalVal;
			base = base*2;
			binary = binary/10;
		}
		long remainder = binary%2;
		decimalVal = remainder*base+ decimalVal;
		return (int)decimalVal;
	}
}
