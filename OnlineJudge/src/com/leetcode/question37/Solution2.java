package com.leetcode.question37;

public class Solution2 {
	
	public void solveSudoku(char[][] board) {
		if (board == null || board.length != 9 || board[0].length != 9)
			return;
	
		fillBord(board);
		
	}
	
	
	private boolean fillBord(char[][] board) {
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if('.'==board[i][j]){
					for(int k=1; k<=9; k++){
						board[i][j] = (char)('0'+k);
						if(isValid(board, i, j) && fillBord(board)){
							return true;
						}
					}
					board[i][j] = '.';
					return false;
				}
			}
		}
		return true;
		
	}


	private boolean isValid(char[][] board, int i, int j) {
		for (int k = 0; k < 9; k++) {
			if (k != j && board[i][k] == board[i][j])
				return false;
		}
		for (int k = 0; k < 9; k++) {
			if (k != i && board[k][j] == board[i][j])
				return false;
		}
		for (int row = i / 3 * 3; row < i / 3 * 3 + 3; row++) {
			for (int col = j / 3 * 3; col < j / 3 * 3 + 3; col++) {
				if ((row != i || col != j) && board[row][col] == board[i][j])
					return false;
			}
		}
		return true;
	}
}
