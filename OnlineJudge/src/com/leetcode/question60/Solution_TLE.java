package com.leetcode.question60;

import java.util.ArrayList;
import java.util.List;


public class Solution_TLE {
	
	
	
	List<String> strList = new ArrayList<String>();
	public String getPermutation(int n, int k) {
		boolean[] mark = new boolean[n];
		int[] numAry = new int[n];
//		StringBuilder sb = new StringBuilder();
		for(int i=0; i<n; i++){
			numAry[i] = i+1;
		}
		for(int i=0; i<numAry.length; i++){
			dfs(numAry, mark, i, new StringBuilder(""));
//			dfs(numAry, mark, i, "");
		}
		
		System.out.println(strList);
        return strList.get(k-1);
        		
        		
    }
	
	private StringBuilder dfs(int[] numAry, boolean[] mark, int curr, StringBuilder sb) {
		mark[curr]=true;
		sb = sb.append(numAry[curr]);
		if(sb.length()==numAry.length){
			strList.add(sb.toString());
		}else{
			for(int i=0; i<numAry.length; i++){
				if(!mark[i]){
					sb = dfs(numAry, mark, i, sb);
				}
			}
		}
		mark[curr]=false;
		sb = new StringBuilder(sb.substring(0, sb.length()-1));
		return sb;
	}

//	private String dfs(int[] numAry, boolean[] mark, int curr, String str) {
//		mark[curr]=true;
//		str = str + numAry[curr];
//		if(str.length()==numAry.length){
//			strList.add(str);
//		}else{
//			for(int i=0; i<numAry.length; i++){
//				if(!mark[i]){
//					dfs(numAry, mark, i, str);
//				}
//			}
//			
//		}
//		mark[curr]=false;
//		str = str.substring(0, str.length()-1);
//		return str;
//	}
	
	public static void main(String[] args) {
		long beginMills = System.currentTimeMillis();
		new Solution_TLE().getPermutation(1, 1);
		System.out.println(System.currentTimeMillis() - beginMills);
	}
}

