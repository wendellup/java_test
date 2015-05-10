package com.leetcode.question22;

import java.util.ArrayList;
import java.util.List;

public class Solution_Repeat {
	public List<String> generateParenthesis(int n) {
		List<String> list = new ArrayList<String>();
		
		dfsGenerate(n, n, "", list);
		
		return list;
		
	}
	
	private void dfsGenerate(int left, int right, String currentStr, List<String> list){
		if(left==0 && right==0){
			list.add(currentStr);
			return;
		}
		
		if(left>right){
			return;
		}
		
		if(left>0){
			currentStr = currentStr + "(";
			dfsGenerate(left-1, right, currentStr, list);
		}
		if(right>0){
			currentStr = currentStr + ")";
			dfsGenerate(left, right-1, currentStr, list);
		}
	}
	
	public static void main(String args[]) {
		Solution_Repeat gp = new Solution_Repeat();
		System.out.println(gp.generateParenthesis(3));
	}
}
