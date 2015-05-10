package com.leetcode.question22;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

	Set<String> isRepeatValidSet = new HashSet<String>();
	public List<String> generateParenthesis(int n) {
		
		List<String> result = new ArrayList<String>();
		
		dfsGetStr(0, 0, "", n, 2*n, result);
		
        return new ArrayList<String>(result);
    }
	
	private void dfsGetStr(
			int leftParenthesesNum, int rightParentheseNum, String currentStr,int n, int totalSize, List<String> result) {
//		if(isRepeatValidSet.contains(currentStr)){
//			return;
//		}else{
//			isRepeatValidSet.add(currentStr);
//		}
		
		
		int l = leftParenthesesNum;
		int r = rightParentheseNum;
		if(currentStr.length() == totalSize){
			result.add(currentStr);
			return;
		}
		
		for(int i=0; i<(n-l); i++){
			currentStr = currentStr+"(";
//			System.out.println("l---"+",i="+ i + ", (n-l)="+(n-l)+"-->"+currentStr);
			leftParenthesesNum++;
			dfsGetStr(leftParenthesesNum, rightParentheseNum, currentStr, n, totalSize, result);
			for(int j=0; j<(n-r) && rightParentheseNum<=l; j++){
				currentStr = currentStr+")";
				System.out.println("r---"+", i="+ i + ", j="+j+", (n-r)="+(n-r)+"-->"+currentStr);
				rightParentheseNum++;
				dfsGetStr(leftParenthesesNum, rightParentheseNum, currentStr, n, totalSize, result);
			}
		}
		
		/*for(int j=0; (j<(n-r) && l!=0 && rightParentheseNum<=l) || (j<(n-r) && rightParentheseNum<l) ; j++){
			currentStr = currentStr+")";
//			System.out.println("r--->"+currentStr);
			rightParentheseNum++;
			dfsGetStr(leftParenthesesNum, rightParentheseNum, currentStr, n, totalSize, result);
			for(int i=0; i<(n-l); i++){
				currentStr = currentStr+"(";
				leftParenthesesNum++;
				dfsGetStr(leftParenthesesNum, rightParentheseNum, currentStr, n, totalSize, result);
			}
		}*/
		
	}
	
	
	public static void main(String[] args) {
			System.out.println(new Solution().generateParenthesis(2));

	}

}
