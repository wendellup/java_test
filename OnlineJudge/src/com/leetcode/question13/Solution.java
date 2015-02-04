package com.leetcode.question13;


public class Solution {
	public int romanToInt(String s) {
        int romainInt = 0;
        String[][] romanBaseAry = 
			{{"I","II","III","IV","V","VI","VII","VIII","IX"}
			,{"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"}
			,{"C","CC","CCC","CD","D","DC","DCC","DCCC","CM"}
			,{"M","MM","MMM"}};
        
        
        
        for(int i=romanBaseAry.length-1; i>=0; i--){
        	boolean isZero = true; 
        	for(int j=romanBaseAry[i].length-1; j>=0; j--){
        		String romainBaseStr = romanBaseAry[i][j];
        		if(s.startsWith(romainBaseStr)){
        			romainInt = romainInt*10 + (j+1);
        			s = s.substring(romainBaseStr.length());
        			isZero = false;
        			break;
        		}
        	}
        	if(isZero){
        		romainInt = romainInt*10;
        	}
        }
        
        return romainInt;
    }

	public static void main(String[] args) {
		System.out.println(new Solution().romanToInt("XCIX"));
		System.out.println(new Solution().romanToInt("MM"));
		System.out.println(new Solution().romanToInt("MI"));
	}

}