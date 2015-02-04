package com.leetcode.question11;


public class Solution_TimeOut {
	public int maxArea(int[] height) {
		int arrayLen = height.length;
		int storeArea = 0;
		if(arrayLen<=1){
			return 0;
		}
		//遍历一边height,找出0~x之间最大的值,且记录该值的坐标
		int[] maxIdx = new int[arrayLen];
		int maxNum = 0;
		for(int idx=0; idx<arrayLen; idx++){
			int num = height[idx];
			if(num > maxNum){
				maxNum = num;
				maxIdx[idx] = idx;
			}else{
				if(idx>0){
					maxIdx[idx] = maxIdx[idx-1];
				}else{
					maxIdx[idx] = 0;
				}
			}
		}
		
		for(int i=1; i<arrayLen; i++){
			for(int j=0; j<=maxIdx[i-1]; j++){
				int curArea = getMinNum(height[i], height[j]) * (i-j);
				if(storeArea<curArea){
					storeArea = curArea;
				}
			}
		}
		
		return storeArea;
	}
	
	public int getMinNum(int numA, int numB){
		return numA<numB ? numA : numB;
	}

	public static void main(String[] args) {
		int[] numAry = {2,1,2,6};
		System.out.println(new Solution_TimeOut().maxArea(numAry));

	}

}