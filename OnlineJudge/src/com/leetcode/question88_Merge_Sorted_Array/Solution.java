package com.leetcode.question88_Merge_Sorted_Array;

import java.util.Arrays;

/**
 * // 先用第三个数组来存放排序后的值,然后再写回nums1
 * 
 * @author yuchao
 *
 */
public class Solution {
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		int[] swapAry = new int[m+n];
		int mRun = 0;
		int nRun = 0;
		int swapRun = 0;
		while(mRun<m && nRun<n){
			int mVal = nums1[mRun];
			int nVal = nums2[nRun];
			if(mVal<=nVal){
				swapAry[swapRun] = mVal;
				mRun++;
			}else{
				swapAry[swapRun] = nVal;
				nRun++;
			}
			swapRun++;
		}
		if(mRun==m){
			while(nRun<n){
				swapAry[swapRun] = nums2[nRun];
				nRun++;
				swapRun++;
			}
		}
		if(nRun==n){
			while(mRun<m){
				swapAry[swapRun] = nums1[mRun];
				mRun++;
				swapRun++;
			}
		}
		for(int i=0; i<(m+n); i++){
			nums1[i] = swapAry[i];
		}
    }
	
	public static void main(String[] args) {
//		int[] ary1 = new int[]{1,2,3,6,6,0,0,0,0,0};
//		int[] ary2 = new int[]{2,4,4,5,6};
//		new Solution().merge(ary1, 5, ary2, 5);
		
		int[] ary1 = new int[]{1,2,3,0,0,0};
		int[] ary2 = new int[]{2,4,6};
		new Solution().merge(ary1, 3, ary2, 3);
		
		System.out.println(Arrays.toString(ary1));
		
	}
}
