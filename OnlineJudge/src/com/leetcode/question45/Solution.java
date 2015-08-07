package com.leetcode.question45;

/**
 * 通过维护一个数组,判断到达当前索引所需的最小步骤.
 * TLE....
 * @author yuchao
 *
 */
public class Solution {
	public int jump(int[] nums) {
		if(nums.length<=2){
			return 1;
		}
		int[] numsSteps = new int[nums.length];
		for(int i=1; i<nums.length; i++){
			numsSteps[i] = -1;
		}
		
		for(int i=0; i<nums.length; i++){
			int currentCanGoLength = nums[i]+i;
			for(int j=i+1; j<=(currentCanGoLength<nums.length-1?currentCanGoLength:nums.length-1)
					; j++){
				int preNumsSteps = numsSteps[j];
				if(preNumsSteps==-1 || preNumsSteps>numsSteps[i]+1){
					numsSteps[j] = numsSteps[i]+1;
				}
				if(numsSteps[numsSteps.length-1]!=-1){
					return numsSteps[numsSteps.length-1];
				}
			}
		}
		
        return numsSteps[numsSteps.length-1];
    }
	
	public static void main(String[] args) {
		int []ary = new int[]{1,2,3};
		int []ary2 = new int[]{};
		int ret = new Solution().jump(ary);
		System.out.println(ret);
	}
}
