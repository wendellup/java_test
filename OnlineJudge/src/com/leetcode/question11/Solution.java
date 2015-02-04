package com.leetcode.question11;


public class Solution {
	public int maxArea(int[] height) {
		int arrayLen = height.length;
		int storeArea = 0;
		if(arrayLen<=1){
			return 0;
		}
		
		int left = 0;
		int right = arrayLen-1;
		while(left < right){
			int heightLeft = height[left];
			int heightRight = height[right];
			storeArea = getMaxNum(storeArea, getMinNum(heightLeft, heightRight)*(right-left));
			if(heightLeft<heightRight){
				left++;
			}else{
				right--;
			}
		}
		
		return storeArea;
	}
	
	public int getMaxNum(int numA, int numB){
		return numA<numB ? numB : numA;
	}

	public int getMinNum(int numA, int numB){
		return numA<numB ? numA : numB;
	}
	
	public static void main(String[] args) {
		int[] numAry = {1,1};
		System.out.println(new Solution().maxArea(numAry));

	}

}