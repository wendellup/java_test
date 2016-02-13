package com.leetcode.question72_Edit_Distance;
//解法参考网址:http://blog.csdn.net/beiyetengqing/article/details/8105180
public class Solution {
	public static void main(String[] args) {
		System.out.println(minDistance("a", "ab"));
	}
	
	private static int min(int a, int b, int c){
	    int mid = a;
	    if(a<=b){
	        mid = a;
	    }else{
	        mid = b;
	    }
	    if(mid>c){
	        return c;
	    }else{
	        return mid;
	    }
	}
	
    public static int minDistance(String word1, String word2) {
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();
        
        int[][] distance = new int[word1.length() + 1][word2.length() + 1];
        
        for (int i = 0; i <= word1.length(); i++) {
            distance[i][0] = i;
        }
        
        for (int i = 0; i <= word2.length(); i++) {
            distance[0][i] = i;
        }
        
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    distance[i][j] = distance[i-1][j-1];
                } else {
                    distance[i][j] = min(distance[i-1][j], distance[i][j-1], distance[i-1][j-1]) + 1;
                }
            }
        }
        return distance[word1.length()][word2.length()];
    }
}