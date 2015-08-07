package com.leetcode.question48;



public class Solution {
	public void rotate(int[][] matrix) {
        int length = matrix.length;
        for(int i=length, step=0; i>0; i=i-2, step++){
        	change(matrix, step, step, i);
        }
    }
	
	private void change(int[][] matrix, int x, int y, int n){
		for(int i=0; i<n-1; i++){
			
			int n1 = matrix[x][y+i];
			int n2 = matrix[x+i][y+n-1];
			int n3 = matrix[x+n-1][y+n-1-i];
			int n4 = matrix[x+n-1-i][y];
			
			matrix[x+i][y+n-1] = n1;
			matrix[x+n-1][y+n-1-i] = n2;
			matrix[x+n-1-i][y] = n3;
			matrix[x][y+i] = n4;
		}
	}
	
	public static void main(String[] args) {
//		int[][] matrix = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
		int[][] matrix = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
//		int[][] matrix = new int[][]{{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
		new Solution().rotate(matrix);
		printMatrix(matrix);
	}
	
	public static void printMatrix(int[][] matrix){
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[i].length; j++){
				System.out.print(matrix[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
}
