package com.leetcode.question89_Gray_Code;

import java.util.ArrayList;

/**
 * 参考文章url: http://blog.csdn.net/worldwindjp/article/details/21536103
 * @author yuchao
 *
 */
public class Solution2 {
    public ArrayList<Integer> grayCode(int n) {
        if(n==0) {
            ArrayList<Integer> result = new ArrayList<Integer>();
            result.add(0);
            return result;
        }
        
        ArrayList<Integer> tmp = grayCode(n-1);
        int addNumber = 1 << (n-1);
        ArrayList<Integer> result = new ArrayList<Integer>(tmp);
        for(int i=tmp.size()-1;i>=0;i--) {
            result.add(addNumber + tmp.get(i));
        }
        return result;
    }
    
    public static void main(String[] args) {
		System.out.println(new Solution2().grayCode(3));;
//		System.out.println(convertBinaryToDecimal(100));
	}
}