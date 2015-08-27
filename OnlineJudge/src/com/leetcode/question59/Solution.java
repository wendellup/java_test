package com.leetcode.question59;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	public int[][] generateMatrix(int n) {
		if(n==1){
			return new int[][]{{1}};
		}
        int[][] retAry = new int[n][n];
        int num = 1;
        for(int i=0; i<n; i++){
        	for(int j=0; j<n; j++){
        		num++;
        		retAry[i][j] = num; 
        	}
        }
        
        spiralOrder(retAry);
        
        
        return retAry;
    }
	
	List<Integer> list = new ArrayList<Integer>();
	int num = 0;
	public List<Integer> spiralOrder(int[][] matrix) {
		if(matrix.length==0){
			return list;
		}
		
		//如果是单行或者单列矩阵,则直接输出
//		if(matrix.length==1 || matrix[0].length==1){
//			for(int i=0; i<matrix.length; i++){
//				for(int j=0; j<matrix[i].length; j++){
//					list.add(matrix[i][j]);
//				}
//			}
//		}else{
			int[][] markMatrix = new int[matrix.length][matrix[0].length];
			//其它情况,从左到右,从上到下,从右到左,从下到上依次遍历
			int direction = 0; //方向标示, 0:从左到右,1:从上到下,2:从右到左,3:从下到上
			Point point = new Point(0, 0);
			while(true){
				direction = walkOneDirection(direction, matrix,markMatrix, point);
				if(direction==-1){
					break;
				}
			}
			
//		}
			
		
		return list;
	}

	private int walkOneDirection(int direction, int[][] matrix, int[][] markMatix, Point point) {
//		int point.point.x = point.getX();
//		int point.y = point.getY();
		if(direction==0){
			//0:从左到右
			int i=point.y;
			for(; i<matrix[point.x].length; i++){
				if(markMatix[point.x][i]==0){
					list.add(matrix[point.x][i]);
					markMatix[point.x][i]=-1;
					matrix[point.x][i] = ++num;
				}else{
					point.y = i-1;
					point.x = point.x+1;
					break;
				}
			}
			if(i == matrix[point.x].length){
				point.x = point.x+1;
				point.y = i-1;
			}
			if(markMatix[point.x][point.y]==0){
				return ++direction%4;
			}
		}else if(direction==1){
			//1:从上到下
			int i=point.x;
			for(; i<matrix.length; i++){
				if(markMatix[i][point.y]==0){
					list.add(matrix[i][point.y]);
					markMatix[i][point.y]=-1;
					matrix[i][point.y] = ++num;
				}else{
					point.x = i-1;
					point.y = point.y-1;
					break;
				}
			}
			if(i == matrix.length){
				//第一次走,走到边界
				point.y = point.y-1;
				point.x = i-1;
			}
			if(markMatix[point.x][point.y]==0){
				return ++direction%4;
			}
		}else if(direction==2){
			//2:从右到左
			int i=point.y;
			for(; i>=0; i--){
				if(markMatix[point.x][i]==0){
					list.add(matrix[point.x][i]);
					markMatix[point.x][i]=-1;
					matrix[point.x][i] = ++num;
				}else{
					point.x = point.x-1;
					point.y = i+1;
					break;
				}
			}
			if(i == -1){
				point.x = point.x-1;
				point.y = i+1;
			}
			if(markMatix[point.x][point.y]==0){
				return ++direction%4;
			}
		}else if(direction==3){
			//3:从下到上
			for(int i=point.x; i>=0; i--){
				if(markMatix[i][point.y]==0){
					list.add(matrix[i][point.y]);
					markMatix[i][point.y]=-1;
					matrix[i][point.y] = ++num;
				}else{
					point.x = i+1;
					point.y = point.y+1;
					break;
				}
			}
			if(markMatix[point.x][point.y]==0){
				return ++direction%4;
			}
		}
		
		
		return -1;
	}
	
	public static void main(String[] args) {
//		Point point2 = new Solution(). new Point(0, 0);
//		point2.x++;
//		System.out.println(point2.x);
		
//		int[][] matrix = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
//		int[][] matrix = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
//		int[][] matrix = new int[][]{{1,2,3,4}};
//		int[][] matrix = new int[][]{{1},{2}};
//		int[][] matrix = new int[][]{{1,2,3},{4,5,6}};
		int n=1;
		int[][] matrix = new Solution().generateMatrix(n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				System.out.print(matrix[i][j]+",");
			}
			System.out.println();
		}
		
		
	}
	
	class Point {
		public Integer x;
		public Integer y;
		public Point(Integer x, Integer y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
}

