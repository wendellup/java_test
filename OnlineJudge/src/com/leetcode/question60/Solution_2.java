package com.leetcode.question60;

public class Solution_2 {
	
	public String getPermutation(int n, int k) {
		int[] nums = new int[n];
		for(int i=0; i<n; i++){
			nums[i] = i+1;
		}
		for(int i=1; i<k; i++){
			nextPermutation(nums);
		}
		String str = "";
		for(int i=0; i<n; i++){
			str += nums[i];
		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(new Solution_2().getPermutation(9, 66666666));
	}
	
    public void nextPermutation(int[] num) {
        //1.找到最后一个升序位置pos
        int pos = -1;
        for (int i = num.length - 1; i > 0; i--) {
            if (num[i] > num[i - 1]) {
                pos = i - 1;
                break;
            }
        }
        
        //2.如果不存在升序，即这个数是最大的，那么反排这个数组
        if (pos < 0) {
            reverse(num, 0, num.length - 1);
            return;
        }
        
        //3.存在升序，那么找到pos之后最后一个比它大的位置
        for (int i = num.length - 1; i > pos; i--) {
            if (num[i] > num[pos]) {
                int tmp = num[i];
                num[i] = num[pos];
                num[pos] = tmp;
                break;
            }
        }
        
        //4.反排pos之后的数
        reverse(num, pos + 1, num.length - 1);
    }
    
    public void reverse(int[] num, int begin, int end) {
        int l = begin, r = end;
        while (l < r) {
            int tmp = num[l];
            num[l] = num[r];
            num[r] = tmp;
            l++;
            r--;
        }
    }
}
