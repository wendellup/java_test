package com.leetcode.question84_Largest_Rectangle_in_Histogram;

public class Solution_TLE {
	public int largestRectangleArea(int[] heights) {
		if(heights.length==1){
			return heights[0];
		}
		int maxArea = 0;
        //横坐标长度
		int length = heights.length;
		
		for(int i=0; i<length; i++){
			for(int height=heights[i]; height>0; height--){
				for(int compareX=i+1; compareX<length; compareX++){
					if(height<=heights[compareX]){
						int curArea = height*(compareX-i+1);
						if(maxArea<curArea){
							maxArea = curArea;
						}
					}else{
						int curArea = height*(compareX-i);
						if(maxArea<curArea){
							maxArea = curArea;
						}
						break;
					}
				}
			}
		}
		
		
		return maxArea;
    }
	
	public static void main(String[] args) {
		long beginMillis = System.currentTimeMillis();
//		int[] heights = new int[]{2,1,5,6,2,3};
//		int[] heights = new int[]{2,2};
		int[] heights = new int[]{2147483647,0,2147483647,0,2147483647,0,2147483647,0,2147483647,0};
		System.out.println(new Solution_TLE().largestRectangleArea(heights));
		System.out.println(System.currentTimeMillis()-beginMillis);
	}
}
