package com.leetcode.question69_SqrtX;
public class Solution {
    public int sqrt(int x) {
        if(x<=1) {
            return x;
        }
        
        int begin = 0;
        int end   = x;
        int middle = 0;
        while(begin<=end) {
            middle = begin + (end - begin)/2;
            //不要写成middle*middle==x，会溢出
            if(middle==x/middle) {
                return middle;
            } else {
                if (middle<x/middle) {
                    begin = middle + 1;
                } else {
                    end = middle - 1;
                }
            }
            
        } 
        //结束条件end一定<begin，所以返回end
        return end;
    }
    
    public static void main(String[] args) {
		System.out.println(new Solution().sqrt(8192));
	}
}