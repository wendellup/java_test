package com.leetcode.question79_Word_Search;

/**
 * 感觉leetcode这题验证抽风,Solution没问题,只好不用全局变量,改成solution2,可以通过
 * @author yuchao
 *
 */
public class Solution2 {
//	public static int rows = 0;
//	public static int columns = 0;
    public boolean exist(char[][] board, String word) {
    	int rows = board.length;
    	if(rows<=0){
    		return false;
    	}
    	int columns = board[0].length;
    	if(columns<=0){
    		return false;
    	}
//    	if(word.length()<=0){
//    		return false;
//    	}
//    	if(rows*columns<word.length()){
//    		return false;
//    	}
//    	System.out.println(rows+","+columns);
    	//标记矩阵,用于表示board对应的下标节点是否被使用
    	int[][] flagMatrix = new int[rows][columns];
    	for(int i=0; i<rows; i++){
    		for(int j=0; j<columns; j++){
    			//找起始位置
    			if(board[i][j] == word.charAt(0)){
    				boolean isFind = backtrack(flagMatrix, board, word, 0, i, j);
    				if(isFind){
    					return true;
    				}
    				flagMatrix = new int[rows][columns];//重新初始化,找下一个起始位置
    			}
    		}
    	}
    	
        return false;
    }
    
    private boolean backtrack(int[][] flagMatrix, char[][] board, String word, int currentWordIndx, int curX, int curY){
    	boolean isFind = false;
//    	if(isFind){
//    		return;
//    	}
    	if(flagMatrix[curX][curY] == 1){
    		return false;
    	}
    	//标记当前找到的节点
    	flagMatrix[curX][curY] = 1;
    	//判断是否已经全部找完
    	if(currentWordIndx == word.length()-1){
//    		isFind = true;
    		return true;
    	}
    	//从四个方向找下一个节点,找到则继续backtrack
    		//向上
    	if(curX!=0 && board[curX-1][curY] == word.charAt(currentWordIndx+1)){
    		isFind = backtrack(flagMatrix, board, word, currentWordIndx+1, curX-1, curY);
    		if(isFind){
    			return true;
    		}
    	}
    		//向右
    	if(curY!=board[0].length-1 && board[curX][curY+1] == word.charAt(currentWordIndx+1)){
    		isFind = backtrack(flagMatrix, board, word, currentWordIndx+1, curX, curY+1);
    		if(isFind){
    			return true;
    		}
    	}
    		//向下
    	if(curX!=board.length-1 && board[curX+1][curY] == word.charAt(currentWordIndx+1)){
    		isFind = backtrack(flagMatrix, board, word, currentWordIndx+1, curX+1, curY);
    		if(isFind){
    			return true;
    		}
    	}
    		//向左
    	if(curY!=0 && board[curX][curY-1] == word.charAt(currentWordIndx+1)){
    		isFind = backtrack(flagMatrix, board, word, currentWordIndx+1, curX, curY-1);
    		if(isFind){
    			return true;
    		}
    	}
    	
    	//否则退回上一节点进行backtrack
    	flagMatrix[curX][curY] = 0;
    	return isFind;
    	
	}
    
    public static void main(String[] args) {
    	char[][] board = {
    			{'A','B','C','E'},
    			{'S','F','C','S'},
    			{'A','D','E','E'}};
//    	char[][] board = {{'a'}};
//    	String[] strAry = new String[]{"ABCE","SFCS","ADEE"};
//    	char[][] boardStr = new String[]{"ABCE","SFCS","ADEE"};
//		System.out.println(new Solution2().exist(board, "AB"));
//		System.out.println(new Solution2().exist(board, "SEE"));
//		System.out.println(new Solution2().exist(board, "ABCB"));
    	
    	char[][] board2 = {
    			{'A','B','C','E'},
    			{'S','F','E','S'},
    			{'A','D','E','E'}};
    	System.out.println(new Solution2().exist(board2, "ABCESEEEFS"));
	}
}