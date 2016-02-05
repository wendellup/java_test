package com.leetcode.question64;
public class Solution {
    public static int minSteps = 0;

    public static int minPathSum(int[][] obstacleGrid) {
    	//方法1,用dfs会超时
        int btx, bty, edx, edy;

        btx = bty = 0;
        edy = obstacleGrid[0].length - 1;
        edx = obstacleGrid.length - 1;


        dfsMatrix(btx, bty, edx, edy, obstacleGrid,0);

        return minSteps;
    }

    public static boolean checkOverBoundary(int x, int y, int edx, int edy) {
        boolean flag = false;

        if (x < 0 || x > edx || y < 0 || y > edy) {
            flag = true;
        }

        return flag;
    }

    public static void dfsMatrix(int btx, int bty, int edx, int edy, int[][] obstacleGrid, int currentStep) {
        currentStep = currentStep + obstacleGrid[btx][bty];
        if (btx == edx && bty == edy) {
            if(minSteps==0){
                minSteps = currentStep;
            }else if(currentStep<minSteps){
                minSteps = currentStep;
            }
        } else {
            // go right
            int rx = btx;
            int ry = bty + 1;

            if (!checkOverBoundary(rx, ry, edx, edy)) {
                dfsMatrix(rx, ry, edx, edy, obstacleGrid,currentStep);
            }

            // go down
            int dx = btx + 1;
            int dy = bty;

            if (!checkOverBoundary(dx, dy, edx, edy)) {
                dfsMatrix(dx, dy, edx, edy, obstacleGrid,currentStep);
            }
        }
    }
}