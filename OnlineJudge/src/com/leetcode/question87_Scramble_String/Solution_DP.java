package com.leetcode.question87_Scramble_String;

//方法二:三维数组的动态规划,参考文章:http://blog.csdn.net/linhuanmars/article/details/24506703
public class Solution_DP {
	   
	public boolean isScramble(String s1, String s2) {
	    if(s1==null || s2==null || s1.length()!=s2.length())
	        return false;
	    if(s1.length()==0)
	        return true;
	    boolean[][][] res = new boolean[s1.length()][s2.length()][s1.length()+1];
	    for(int i=0;i<s1.length();i++)
	    {
	        for(int j=0;j<s2.length();j++)
	        {
	            res[i][j][1] = s1.charAt(i)==s2.charAt(j);
	        }
	    }
	    for(int len=2;len<=s1.length();len++)
	    {
	        for(int i=0;i<s1.length()-len+1;i++)
	        {
	            for(int j=0;j<s2.length()-len+1;j++)
	            {
	                for(int k=1;k<len;k++)
	                {
	                    res[i][j][len] |= res[i][j][k]&&res[i+k][j+k][len-k] || res[i][j+len-k][k]&&res[i+k][j][len-k];
	                }
	            }
	        }
	    }
	    return res[0][0][s1.length()];
	}
	    
	    public static void main(String[] args) {
			String s1 = "great";
			String s2 = "gerat";
			System.out.println(new Solution_DP().isScramble(s1, s2));
		}
}
