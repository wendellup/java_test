package com.leetcode.question74_Search_a_2D_Matrix;

/**
 * 参考文章:
 * 	http://www.programcreek.com/2013/01/leetcode-search-a-2d-matrix-java/
 * 	http://blog.csdn.net/lanxu_yy/article/details/17261035
 * 	lgm+lgn = lg(m*n)
 * @author yuchao
 *
 */
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null || matrix.length==0 || matrix[0].length==0) 
            return false;
 
        int m = matrix.length;
        int n = matrix[0].length;
 
        int start = 0;
        int end = m*n-1;
 
        while(start<=end){
            int mid=(start+end)/2;
            int midX=mid/n;
            int midY=mid%n;
 
            if(matrix[midX][midY]==target) 
                return true;
 
            if(matrix[midX][midY]<target){
                start=mid+1;
            }else{
                end=mid-1;
            }
        }
 
        return false;
    }
}