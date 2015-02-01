package com.leetcode.question10;

public class Solution {
    public boolean isMatch(String s, String p) {
        if (s == null)
            return p == null;
        if (p == null)
            return s == null;
 
        int lenS = s.length();
        int lenP = p.length();
 
        if (lenP == 0)
            return lenS == 0;
 
        if (lenP == 1) {
            if (p.equals(s) || p.equals(".") && s.length() == 1) {
                return true;
            } else
                return false;
        }
        if (p.charAt(1) != '*') {
            if (s.length() > 0
                    && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
                return isMatch(s.substring(1), p.substring(1));
            }
            return false;
        } else {
            while (s.length() > 0
                    && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
                if (isMatch(s, p.substring(2)))
                    return true;
                s = s.substring(1);
            }
            return isMatch(s, p.substring(2));
        }
 
    }
 
    public static void main(String[] args) {
         System.out.println(new Solution().isMatch("aab", "cc*a*b"));
 
    }
 
}