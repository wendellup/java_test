package com.leetcode.question55;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public boolean canJump(int[] nums) {
        List<Integer> canGoIdxList = new ArrayList<Integer>();
        int targetLen = nums.length;
        if(nums.length<=1){
        	return true;
        }
        
        canGoIdxList.add(0);
        int currentIdx = 0;
        while(true){
        	int currentGoWidth = nums[canGoIdxList.get(currentIdx)];
        	if((currentGoWidth+currentIdx+1)>=targetLen){
        		return true;
        	}
        	if(currentGoWidth+currentIdx+1>canGoIdxList.size()) {
        		//根据currentGoWidth向canGoIdxList中添加新增的可以前往的idx
        		for(int i=canGoIdxList.size();i<currentGoWidth+currentIdx+1;i++){
        			canGoIdxList.add(i);
        		}
        	}
        	if(currentIdx==(canGoIdxList.size()-1)){
        		return false;
        	}
        	currentIdx++;
        }
    }
    
    public static void main(String[] args) {
//    	int []nums = new int[]{2,3,1,1,4};
    	int []nums = new int[]{3,2,1,0,4};
    	
		boolean canJump = new Solution().canJump(nums);
		System.out.println(canJump);
	}
    
}