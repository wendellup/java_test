package com.leetcode.question43;

import java.math.BigDecimal;

public class Solution {
	public String multiply(String num1, String num2) {
		BigDecimal retVal = new BigDecimal(0L);
        BigDecimal num2Val = new BigDecimal(num2); 
        
        BigDecimal multiple = new BigDecimal(1);
        
        for(int i=num1.length()-1; i>=0; i--){
        	int curNum = Integer.parseInt(num1.charAt(i)+"");
        	BigDecimal curVal = num2Val.multiply(new BigDecimal(curNum)).multiply(multiple);
        	retVal = retVal.add(curVal);
//        	retVal += multiple * curNum * num2Val;
        	multiple = multiple.multiply(new BigDecimal(10));
        }
        
        return retVal+"";
    }
	
	public static void main(String[] args) {
		System.out.println(new Solution().multiply("121", "0"));
		System.out.println(new Solution().multiply("11", "11"));
		System.out.println(new Solution().multiply("12345", "12345"));
//		System.out.println(new Solution().multiply("5423396", "5424012638"));
//		System.out.println(new Solution().multiply("401716832807512840963", "167141802233061013023557397451289113296441069"));
		System.out.println(new Solution().multiply("60974249908865105026646412538664653190280198809433017", "500238825698990292381312765074025160144624723742"));
		
//		"30501687172287445993560048081057096686019986405658336826483685740920318317486606305094807387278589614"
		
		
	}
	
}
