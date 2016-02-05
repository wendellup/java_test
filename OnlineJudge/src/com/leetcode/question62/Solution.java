package com.leetcode.question62;

import java.math.BigDecimal;

public class Solution {
	public static void main(String[] args) {
		System.out.println(getFactorial(4));
		System.out.println(new Solution().uniquePaths(10,10));
	}
	
    public int uniquePaths(int m, int n) {
    	//方法1,组合方程
//    	BigDecimal bd1 = getFactorial(m+n-2);
//    	BigDecimal bd2 = getFactorial(m-1);
//    	BigDecimal bd3 = getFactorial(n-1);
//    	BigDecimal bd4 = bd2.multiply(bd3);
//    	BigDecimal result = bd1.divide(bd4);
//    	System.out.println(result.intValue());
    	
    	//方法2,归纳法
    	int[][] ary = new int[m][n];
    	for(int i=0; i<m; ++i){  
            for(int j=0; j<n; ++j){  
            	ary[i][j]=1;  
            }  
        } 
        for(int i=1; i<m; ++i){  
            for(int j=1; j<n; ++j){  
            	ary[i][j]=ary[i-1][j]+ary[i][j-1];  
            }  
        }  
        System.out.println(ary[m-1][n-1]);
        return ary[m-1][n-1]; 
    }
    
    //递归会超时
    public static BigDecimal getFactorial(int n){
    	BigDecimal resultNum = new BigDecimal(1);
    	for(int i=1; i<=n; i++){
    		resultNum = resultNum.multiply(new BigDecimal(i));
    	}
    	return resultNum;
    }
}