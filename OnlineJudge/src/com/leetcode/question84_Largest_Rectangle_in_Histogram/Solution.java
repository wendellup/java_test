package com.leetcode.question84_Largest_Rectangle_in_Histogram;

import java.util.Stack;

//栈解法
//参考文章:http://blog.csdn.net/doc_sgl/article/details/11805519
public class Solution {
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
	
	public static void main(String[] args) {
		long beginMillis = System.currentTimeMillis();
		
//		int[] heights = new int[]{2,1,5,6,2,3};
		int[] heights = new int[]{2,2};
//		int[] heights = new int[]{2147483647,0,2147483647,0,2147483647,0,2147483647,0,2147483647,0};
		System.out.println(new Solution().largestRectangleArea(heights));
	}
}
