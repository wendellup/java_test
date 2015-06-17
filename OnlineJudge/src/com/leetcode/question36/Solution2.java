package com.leetcode.question36;

import java.util.HashSet;
import java.util.Set;

public class Solution2 {
	public boolean isValidSudoku(char[][] board) {
		int len = board.length;
		if(len!=9){
			return false;
		}
		for(int i=0; i<len; i++){
			int hI = board[i].length;
			if(hI!=9){
				return false;
			}
		}
		//验证横列9条
		for(int i=0; i<9; i++){
			Set<Character> set = new HashSet<Character>();
			for(int j=0; j<9; j++){
				if(!('.'==(board[i][j]))){
					if(set.contains(board[i][j])){
						return false;
					}else{
						set.add(board[i][j]);
					}
				}
			}
		}
		//验证竖列9条
		for(int j=0; j<9; j++){
			Set<Character> set = new HashSet<Character>();
			for(int i=0; i<9; i++){
				if(!('.'==(board[i][j]))){
					if(set.contains(board[i][j])){
						return false;
					}else{
						set.add(board[i][j]);
					}
				}
			}
		}
		
		//验证3*3个方块
		for(int lineStart=0; lineStart<9; lineStart=lineStart +3){
			for(int rowStart=0; rowStart<9; rowStart=rowStart+3){
				Set<Character> set = new HashSet<Character>();
				for(int i=lineStart; i<lineStart+3; i++){
					for(int j=rowStart; j<rowStart+3; j++){
						System.out.println("valid:board["+i+"]["+j+"]");
						if(!('.'==(board[i][j]))){
							if(set.contains(board[i][j])){
								return false;
							}else{
								set.add(board[i][j]);
							}
						}
					}
				}
				System.out.println("--------------------");
			}
		}
		return true;
    }
	
	public static void main(String[] args) {
		char[][] board = {
				{'.','.','.','.','5','.','.','1','.'},
				{'.','4','.','3','.','.','.','.','.'},
				{'.','.','.','.','.','3','.','.','1'},
				{'8','.','.','.','.','.','.','2','.'},
				{'.','.','2','.','7','.','.','.','.'},
				{'.','1','5','.','.','.','.','.','.'},
				{'.','.','.','.','.','2','.','.','.'},
				{'.','2','.','9','.','.','.','.','.'},
				{'.','.','4','.','.','.','.','.','.'}
				
		};
		String [] strAry ={"....5..1.",".4.3.....",".....3..1","8......2.","..2.7....",".15......",".....2...",".2.9.....","..4......"};
		for(int i=0; i<strAry.length; i++){
			String str = strAry[i];
			String outStr = "";
			for(int j=0; j<str.length(); j++){
				if("".equals(outStr)){
					outStr = "'"+str.charAt(j)+"'";
				}else{
					outStr = outStr+",'"+str.charAt(j)+"'";
				}
			}
			System.out.println(outStr);
		}
		System.out.println(new Solution2().isValidSudoku(board));
//		System.out.println('.'=='.');
//		System.out.println("."==".");
//		System.out.println(".".equals("."));
		
		
	}
}
