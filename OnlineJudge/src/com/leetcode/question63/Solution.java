package com.leetcode.question63;

public class Solution {
	public static void main(String[] args) {
		// System.out.println(getFactorial(4));
		// int[][] ary = new int[][]{
		// {0,0,0}
		// ,{0,1,0}
		// ,{0,0,0}};
		int[][] ary = new int[][] { { 0 } };
		// int[][] ary = new int[][]{
		// {0,0}
		// ,{0,1}
		// ,{0,0}
		// };
		System.out.println(new Solution().uniquePathsWithObstacles(ary));

	}

	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		// 如果第一个节点,最后一个节点就是obstacle
		if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1
				|| obstacleGrid == null) {
			return 0;
		}
		boolean hasObstacle = false;

		int[][] ary = new int[m][n];
		for (int i = 0; i < m; ++i) {
			if (hasObstacle == true) {
				break;
			}
			for (int j = 0; j < n; ++j) {
				if (obstacleGrid[i][j] == 1) {
					// ary[i][j]=0;
					hasObstacle = true;
					break;
					// }else{
					// ary[i][j]=1;
				}
			}
		}

		for (int i = 0; i < n; i++) {
			if (obstacleGrid[0][i] != 1) {
				ary[0][i] = 1;
			} else {
				break;
			}
		}

		for (int i = 0; i < m; i++) {
			if (obstacleGrid[i][0] != 1) {
				ary[i][0] = 1;
			} else {
				break;
			}
		}

		// 如果只有一行或者一列,且有obstacle,则返回0
		if ((m == 1 || n == 1) && hasObstacle) {
			return 0;
		}

		for (int i = 1; i < m; ++i) {
			for (int j = 1; j < n; ++j) {
				if (obstacleGrid[i][j] == 1) {
					ary[i][j] = 0;
				} else {
					ary[i][j] = ary[i - 1][j] + ary[i][j - 1];
				}
			}
		}
		return ary[m - 1][n - 1];
	}
}