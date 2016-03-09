package com.leetcode.question67_Add_Binary;
public class Solution {
//    public String addBinary(String a, String b) {
    	//转换成10进制加减再返回,会报转换错误
//    	long aLong = Long.parseLong(a, 2);
//    	long bLong = Long.parseLong(b, 2);
//    	long cntInt = aLong + bLong;
//    	return Long.toString(cntInt, 2);
//    }
    
	public String addBinary(String a, String b) {
	    if(a==null || a.length()==0)
	        return b;
	    if(b==null || b.length()==0)
	        return a;
	    int i=a.length()-1;
	    int j=b.length()-1;
	    int carry = 0;
	    StringBuilder res = new StringBuilder();
	    while(i>=0&&j>=0)
	    {
	        int digit = (int)(a.charAt(i)-'0'+b.charAt(j)-'0')+carry;
	        carry = digit/2;
	        digit %= 2;
	        res.append(digit);
	        i--;
	        j--;
	    }
	    while(i>=0)
	    {
	        int digit = (int)(a.charAt(i)-'0')+carry;
	        carry = digit/2;
	        digit %= 2;
	        res.append(digit);
	        i--;
	    }
	    while(j>=0)
	    {
	        int digit = (int)(b.charAt(j)-'0')+carry;
	        carry = digit/2;
	        digit %= 2;
	        res.append(digit);
	        j--;
	    }      
	    if(carry>0)
	    {
	        res.append(carry);
	    }
	    return res.reverse().toString();
	}
	
    public static void main(String[] args) {
    	
		System.out.println(new Solution().addBinary(
				"10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101"
				, 
				"110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011"
				));
	}
}