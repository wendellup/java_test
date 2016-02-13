package com.leetcode.question85_Maximal_Rectangle;

import java.util.Stack;

//步骤:1.转换矩阵,2.求每行的Largest_Rectangle(84)
public class Solution {
	public static void main(String[] args) {
//		char[][] matrix = new char[][]{
//				 {'0','0','1','0'}
//				,{'0','0','0','1'}
//				,{'0','1','1','1'}
//				,{'0','0','1','1'}};
		char[][] matrix = new char[][]{
				 {'0','0','0','0'}
				,{'0','0','1','0'}
				,{'0','0','0','1'}
				,{'0','1','1','1'}};
		System.out.println(new Solution().maximalRectangle(matrix));
	}
	
	public int maximalRectangle(char[][] matrix) {
		if(matrix.length==0){
			return 0;
		}
		int maxArea = 0;
		int[][] intMatrix = new int[matrix.length][matrix[0].length];
		
        //步骤:1.转换矩阵
		//定义矩阵左下角为原点
		for(int i=matrix.length-1; i>=0; i--){
			for(int j=matrix[0].length-1; j>=0; j--){
				if(matrix[i][j]=='1'){
					if(i==matrix.length-1){
						intMatrix[i][j] = 1;
					}else{
						intMatrix[i][j] = intMatrix[i+1][j]+1;
					}
				}
				if(matrix[i][j]=='0'){
					intMatrix[i][j] = 0;
				}
			}
		}
//		System.out.println(intMatrix);
		//步骤:2.求每行的Largest_Rectangle(84)
		for(int i=0; i<matrix.length; i++){
			maxArea = Math.max(maxArea, largestRectangleArea(intMatrix[i]));
		}
		
		return maxArea;
				
    }
	
	public int largestRectangleArea(int[] heights) {
    	int[] newHeights = new int[heights.length+1];
    	for(int i=0; i<heights.length; i++){
    		newHeights[i] = heights[i];
    	}
    	newHeights[heights.length] = 0;
    	
        Stack<Integer> stk = new Stack<Integer>();
        int i = 0;
        int maxArea = 0;
        while(i < newHeights.length){
            if(stk.empty() || newHeights[stk.lastElement()] <= newHeights[i]){
                stk.push(i++);
            }else {
                int t = stk.lastElement();
				stk.pop();
                maxArea = Math.max(maxArea, newHeights[t] * (stk.empty() ? i : i - stk.lastElement() - 1));
            }
        }
        return maxArea;
    }
}	
