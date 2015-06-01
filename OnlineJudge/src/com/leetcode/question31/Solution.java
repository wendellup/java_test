package com.leetcode.question31;

import java.util.Arrays;


public class Solution {
    public void nextPermutation(int[] num) {
		boolean isMax = true;
		int len = num.length;
		int tempMin = num[0];// 判断num数组是否是递减时用到
		int start = 0;// 快速排序的起始位置
		int last;// 记录待对比的元素
		int besti = 0, bestj = 0;// 待交换元素的位置
		for (int i = 1; i < len; i++) {
			if (num[i] > tempMin) {
				isMax = false;
				break;
			} else
				tempMin = num[i];
		}
		// 如果是逆序
		if (isMax) {
			for (int i = 0, j = len - 1; i < j; i++, j--) {
				int temp = num[i];
				num[i] = num[j];
				num[j] = temp;
			}
		} else {
			boolean isFind = false;
			//先逆序替换对应元素,然后再对替换位置之后的元素进行排序
			for(int i=len-2; i>=0; i--){
				for(int j=len-1; j>i; j--){
					int curVal = num[j];
					if(curVal>num[i]){
						besti = i;
						bestj = j;
						isFind = true;
						break;
					}
				}
				if(isFind==true){
					break;
				}
			}
			if(isFind){
				//替换元素
				int temp = num[bestj];
				num[bestj] = num[besti];
				num[besti] = temp;
				//开始排序
				_quickSort(num, besti+1, len-1);
			}
		}
		
//		if(num.length==2 && num[0]<num[1]){
//			int temp = num[0];
//			num[0] = num[1];
//			num[1] = temp;
//		}
	}

	public static void _quickSort(int[] numbers, int low, int high) {
		if (low < high) {
			int middle = getMiddle(numbers, low, high); // 将list数组进行一分为二
			_quickSort(numbers, low, middle - 1); // 对低字表进行递归排序
			_quickSort(numbers, middle + 1, high); // 对高字表进行递归排序
		}
	}

	public static int getMiddle(int[] numbers, int low, int high) {
		int tmp = numbers[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && numbers[high] > tmp) {
				high--;
			}
			numbers[low] = numbers[high]; // 比中轴小的记录移到低端
			while (low < high && numbers[low] <= tmp) {
				low++;
			}
			numbers[high] = numbers[low]; // 比中轴大的记录移到高端
		}
		numbers[low] = tmp; // 中轴记录到尾
		return low; // 返回中轴的位置
	}
	
	public static void main(String[] args) {
//		int[] num = {1,5,3,2};
//		int[] num = {1,2,3,4};
//		int[] num = {4,3,2,1};
		int[] num = {1,3,2,2,1,3,2};
		new Solution().nextPermutation(num);
		System.out.println(Arrays.toString(num));
	}
}