package com.leetcode.question52;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    List<List<String>> resultList = new ArrayList<List<String>>();
//	public List<List<String>> solveNQueens(int n) {
//		int[][] matrix = new int[n][n];
//		solveProcess(n, matrix, 0);
//		return resultList;
//    }
	public int totalNQueens(int n) {
		int[][] matrix = new int[n][n];
		solveProcess(n, matrix, 0);
		return resultList.size();
    }
	

	private void solveProcess(int n, int[][] matrix, int curX){
		if(curX==n){
			//找到解,将解丢入结果集
			reverseMatrixToStrList(matrix);
			return;
		}
		
			for(int y=0; y<n; y++){
				if(matrix[curX][y]==0){
					int[][] matrixCp = copyMatrix(matrix, n);
					//将某个节点及该与该节点联通的节点都置位
					boolean isValid = setMatrixPointWithCheck(matrixCp, curX, y, n);
					if(isValid==false){
						continue;
					}
					//处理下一个节点
						solveProcess(n, matrixCp, curX+1);
				}
			}
//		}
	}
	
	private void reverseMatrixToStrList(int[][] matrix) {
		List<String> strList = new ArrayList<String>();
		for(int i=0; i<matrix.length; i++){
			StringBuilder sb = new StringBuilder();
//			String str = "";
			for(int j=0; j<matrix[0].length; j++){
				if(matrix[i][j]==0 || matrix[i][j]==1){
					sb.append(".");
//					str += ".";
				}else{
					sb.append("Q");
//					str += "Q";
				}
			}
			strList.add(sb.toString());
//			strList.add(str);
		}
//		System.out.println(strList);
		resultList.add(strList);
	}

	private boolean setMatrixPointWithCheck(int[][] matrixCp, int x, int y, int n) {
		matrixCp[x][y]=2;
		//横向置位
		for(int i=0; i<n; i++){
			if(i==y){
				continue;
			}
			if(matrixCp[x][i]==0){
				matrixCp[x][i] = 1;
			}else if(matrixCp[x][i]==2){
				return false;
			}
		}
		//纵向置位
		for(int i=0; i<n; i++){
			if(i==x){
				continue;
			}
			if(matrixCp[i][y]==0){
				matrixCp[i][y] = 1;
			}else if(matrixCp[i][y]==2){
				return false;
			}
		}
		//左下置位
		for(int i=x+1, j=y-1; i<n&&j>=0; i++,j--){
			if(i==x&&j==y){
				continue;
			}
			if(matrixCp[i][j]==0){
				matrixCp[i][j] = 1;
			}else if(matrixCp[i][j]==2){
				return false;
			}
			
		}
		//右上置位
		for(int i=x-1, j=y+1; i>=0&&j<n; i--,j++){
			if(i==x&&j==y){
				continue;
			}
			if(matrixCp[i][j]==0){
				matrixCp[i][j] = 1;
			}else if(matrixCp[i][j]==2){
				return false;
			}
		}
		//左上置位
		for(int i=x-1, j=y-1; i>=0&&j>=0; i--,j--){
			if(i==x&&j==y){
				continue;
			}
			if(matrixCp[i][j]==0){
				matrixCp[i][j] = 1;
			}else if(matrixCp[i][j]==2){
				return false;
			}
		}
		//右下置位
		for(int i=x+1, j=y+1; i<n&&j<n; i++,j++){
			if(i==x&&j==y){
				continue;
			}
			if(matrixCp[i][j]==0){
				matrixCp[i][j] = 1;
			}else if(matrixCp[i][j]==2){
				return false;
			}
		}
		return true;
	}

	private static int[][] copyMatrix(int[][] intAry, int n){
		int[][] copyArray=new int[n][];
		for(int i=0;i<n;i++){
			copyArray[i]=new int[n];
			System.arraycopy(intAry[i], 0, copyArray[i], 0, n);
		}
		return copyArray;
		
	}

	public static void main(String[] args) {
		long beganMillis = System.currentTimeMillis();
		System.out.println(new Solution().totalNQueens(9));;
		System.out.println(System.currentTimeMillis() - beganMillis);
	}
	
	private static void printAry(int[][] intAry){
		for(int i=0; i<intAry.length; i++){
			for(int j=0; j<intAry[0].length; j++){
				System.out.print(intAry[i][j]+",");
			}
			System.out.println();
		}
	}
}