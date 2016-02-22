package com.leetcode.question87_Scramble_String;

//解法一:递归,参考文章:http://www.cnblogs.com/easonliu/p/3696135.html
public class Solution {
	public boolean isScramble(String s1, String s2) {
		int l1 = s1.length();
        int l2 = s2.length();
        if (l1 != l2) return false;
        if (l1 == 1) return s1.equals(s2);
        int[] charAry = new int[26];
//        sort(st1.begin(), st1.end());
//        sort(st2.begin(), st2.end());
        for(int i=0; i<s1.length(); i++){
        	charAry[s1.charAt(i)-'a']++;
        }
        
        for(int i=0; i<s2.length(); i++){
        	charAry[s2.charAt(i)-'a']--;
        }
        
        for(int i=0; i<26; i++){
        	if(charAry[i]!=0){
        		return false;
        	}
        }
        
        String s11, s12, s21, s22;
        boolean res = false;
        for (int i = 1; i < l1 && !res; ++i) {
            s11 = s1.substring(0, i);
            s12 = s1.substring(i, l1);
            s21 = s2.substring(0, i);
            s22 = s2.substring(i, l1);
            res = isScramble(s11, s21) && isScramble(s12, s22);
            if (!res) {
                s21 = s2.substring(0, l1 - i);
                s22 = s2.substring(l1 - i, l1);
                res = isScramble(s11, s22) && isScramble(s12, s21);
            }
        }
        return res;
    }
	
	public static void main(String[] args) {
		String s1 = "great";
		String s2 = "great";
		System.out.println(new Solution().isScramble(s1, s2));
	}
}
