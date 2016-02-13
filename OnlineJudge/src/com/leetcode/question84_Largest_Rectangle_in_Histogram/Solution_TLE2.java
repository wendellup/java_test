package com.leetcode.question84_Largest_Rectangle_in_Histogram;

//优化后的遍历,O(n^2),依然超时
public class Solution_TLE2 {
	public int largestRectangleArea(int[] heights) {
		int end = heights.length;
    	int begin = 0;
		int largestarea = 0;
		for(int i = begin; i < end; ++i)
		{
			int area;
			int high = heights[i];
			for(int j = i; j < end; ++j){
				if(heights[j] < high) high = heights[j];
				area = (j - i + 1)*high;
				if(area > largestarea) largestarea = area;
			}
		}
		return largestarea;
    }	
}
