package com.leetcode.question166;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**	
	Fraction to Recurring Decimal
	
	Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.

	If the fractional part is repeating, enclose the repeating part in parentheses.
	
	For example,
	
	Given numerator = 1, denominator = 2, return "0.5".
	Given numerator = 2, denominator = 1, return "2".
	Given numerator = 2, denominator = 3, return "0.(6)".

 * 
 * @author yuchao
 *
 */
public class Solution {

	public static String fractionToDecimal(int numerator, int denominator) {
		boolean isNegtive = numerator * denominator < 0;
		numerator = Math.abs(numerator);
		denominator = Math.abs(denominator);
		StringBuilder sb = new StringBuilder("");
		
		Map<Integer, Integer> remainderMap = new HashMap<Integer, Integer>();
		List<Integer> quotientList = new ArrayList<Integer>();
		
		
		while(true){
			//让分子大于等于分母先
			while(numerator<denominator){
				quotientList.add(0);
				int remainder = numerator%denominator;
				remainderMap.put(remainder, quotientList.size()-1);
				numerator=numerator*10;
			}
			int remainder = numerator%denominator;
			if(remainder == 0){
				//整除了, 如2/1, 1/10
				int quotient = numerator/denominator;
				quotientList.add(quotient);
				break;
				
			}
		}
		
		for(int i=0; i<quotientList.size(); i++){
			if(i==1){
				//加小数点
				sb.append(".");
			}
			sb.append(quotientList.get(i)+"");
		}
		
		//加正负号
		if(isNegtive){
			sb = new StringBuilder("-").append(sb);
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(fractionToDecimal(-135,100));;
	}
	
}

