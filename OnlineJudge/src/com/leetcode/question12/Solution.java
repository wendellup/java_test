package com.leetcode.question12;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	public String intToRoman(int num) {
		String[][] romanBaseAry = 
			{{"I","II","III","IV","V","VI","VII","VIII","IX"}
			,{"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"}
			,{"C","CC","CCC","CD","D","DC","DCC","DCCC","CM"}
			,{"M","MM","MMM"}};
		
		String romanStr = "";
		List<Integer> descList = new ArrayList<Integer>();
		while(num>0){
			int residue = num%10;
			descList.add(residue);
			num = num/10;
		}
		for(int idx = descList.size()-1
				; idx>=0
				; idx--){
			int ascNum = descList.get(idx);
			if(ascNum!=0){
				romanStr = romanStr + romanBaseAry[idx][ascNum-1];
			}
		}
		
		return romanStr;
	}

	public static void main(String[] args) {
		System.out.println(new Solution().intToRoman(1001));
	}

}