package com.leetcode.question64;
public class Solution_dp {
	
	//方法3:dp不会超时
	public static int minPathSum(int[][] obstacleGrid) {
		
    	int f[][] = new int[obstacleGrid.length][obstacleGrid[0].length];
        if (obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
            return 0;
            
        f[0][0] = obstacleGrid[0][0];
        
        for(int i = 1; i < obstacleGrid.length; i++)
            f[i][0] = f[i-1][0] + obstacleGrid[i][0];
            
        for(int i = 1; i < obstacleGrid[0].length; i++)
            f[0][i] = f[0][i-1] + obstacleGrid[0][i];
            
        for(int i = 1; i < obstacleGrid.length; i++)
            for(int j = 1; j < obstacleGrid[0].length; j++)
                f[i][j] = Math.min(f[i-1][j], f[i][j-1]) + obstacleGrid[i][j];
                
        return f[obstacleGrid.length-1][obstacleGrid[0].length-1];
    }
}