package com.leetcode.question51;

import java.util.ArrayList;
import java.util.List;

public class Snippet {
	public static void main(String[] args) {
		long beginMillis = System.currentTimeMillis();
		List<List<String>> resultList =new Snippet().solveNQueens(4);
//		for(List<String> oneResult : resultList){
//			for(String str : oneResult){
//				System.out.println(str);
//			}
//			System.out.println("-------");
//		}
		System.out.println(System.currentTimeMillis() - beginMillis);
		
	}
	
	public List<List<String>> solveNQueens(int n) {
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String[]> oldResult = solveNQueensOld(n);
		for(String[] strAry : oldResult){
			List<String> oneResult = new ArrayList<String>();
			for(String str : strAry){
				oneResult.add(str);
			}
			resultList.add(oneResult);
		}
		return resultList;
    }
	
	public ArrayList<String[]> solveNQueensOld(int n) {
	        ArrayList<String[]> res = new ArrayList<String[]>();
	        if(n<=0)
	            return res;
	            
	        int [] columnVal = new int[n];
	        
	        DFS_helper(n,res,0,columnVal);
	        return res;
	    }
	    
	    public void DFS_helper(int nQueens, ArrayList<String[]> res, int row, int[] columnVal){
	        if(row == nQueens){
	            String[] unit = new String[nQueens];
	            for(int i = 0; i < nQueens; i++){
	                StringBuilder s = new StringBuilder();
	                for(int j = 0; j < nQueens; j++){
	                    if(j == columnVal[i])
	                        s.append("Q");
	                    else
	                        s.append(".");
	                }
	                
	                unit[i] = s.toString();
	            }
	            
	            res.add(unit);
	        }else{
	            for(int i = 0; i < nQueens; i++){
	                columnVal[row] = i;//(row,columnVal[row)==>(row,i)
	                
	                if(isValid(row,columnVal))
	                    DFS_helper(nQueens, res, row+1, columnVal);
	            }
	        }
	    }
	    
	    public boolean isValid(int row, int [] columnVal){
	        for(int i = 0; i < row; i++){
	            if(columnVal[row] == columnVal[i]
	               ||Math.abs(columnVal[row]-columnVal[i]) == row-i)
	               return false;
	        }
	        return true;
	    }
}

