package com.leetcode.question7;

import java.util.ArrayList;
import java.util.List;


public class Solution {
	public int reverse(int x) {
		long xL = new Long(x);
		List<Long> list = new ArrayList<Long>();
		boolean positive = true;
		if(xL<0){
			positive = false;
			xL = -xL;
		}
		
		long residue = xL%10;
		list.add(residue);
		while((xL = xL/10)!=0){
			residue = xL%10;
			list.add(residue);
		}
		
		long ret = 0;
		for(Long i : list){
			ret = ret*10+i;
		}
		if(!positive){
			ret = -ret;
		}
		if(ret>=2147483647 || ret <=-2147483647){
			return 0;
		}
        return new Long(ret).intValue();
    }
    
    public static void main(String[] args) {
    	System.out.println(new Solution().reverse(2147483643));;
	}
}