package com.leetcode.question73_Set_Matrix_Zeroes;

/**
 * 参考文章url:http://blog.csdn.net/ljiabin/article/details/40423045
 * @author yuchao
 *
 */
public class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i, j;
        
        //先标记第一行和第一列是否有0
        boolean firstRow = false, firstCol = false;
        for (j = 0; j < n; j++) {
            if (0 == matrix[0][j]) {
                firstRow = true;
                break;
            }
        }
        for (i = 0; i < m; i++) {
            if (0 == matrix[i][0]) {
                firstCol = true;
                break;
            }
        }
         
        //从第二行第二列还是遍历，如果遇到0，则把它所在行和列的第一个值设为0   
        for (i = 1; i < m; i++) {
            for (j = 1; j < n; j++) {
                if (0 == matrix[i][j]) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        //把第一列的0所在行都设为0，把第一行的0所在列都设为0
        for (i = 1; i < m; i++) {
            if (0 == matrix[i][0]) {
                for (j = 1; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (j = 1; j < n; j++) {
            if (0 == matrix[0][j]) {
                for (i = 1; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        //根据标记决定第一行和第一列是否全设为0
        if (firstRow) {
            for (j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstCol) {
            for (i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}