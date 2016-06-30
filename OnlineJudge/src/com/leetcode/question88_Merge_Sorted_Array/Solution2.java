package com.leetcode.question88_Merge_Sorted_Array;

import java.util.Arrays;

/**
 * 不用额外的数组来实现,维护三个指针,分别指向nums1最后一个元素,nums2最后一个元素,新数组的最后一个元素(nums1),
 * 时间复杂度O(m+n),空间复杂度O(1)
 * 参考文章url: http://blog.csdn.net/linhuanmars/article/details/19712333
 * 
 * @author yuchao
 */
public class Solution2 {
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		int idx = m+n-1;
		int mRun = m-1;
		int nRun = n-1;
		while(mRun>=0 && nRun>=0){
			if(nums1[mRun]>=nums2[nRun]){
				nums1[idx] = nums1[mRun];
				mRun--;
			}else{
				nums1[idx] = nums2[nRun];
				nRun--;
			}
			idx--;
		}
		while(nRun>=0){
			nums1[idx] = nums2[nRun];
			idx--;
			nRun--;
		}
    }
	
	public static void main(String[] args) {
//		int[] ary1 = new int[]{1,2,3,6,6,0,0,0,0,0};
//		int[] ary2 = new int[]{2,4,4,5,6};
//		new Solution().merge(ary1, 5, ary2, 5);
		
		int[] ary1 = new int[]{0};
		int[] ary2 = new int[]{1};
		new Solution2().merge(ary1, 0, ary2, 1);
		
		System.out.println(Arrays.toString(ary1));
		
	}
}
