package com.leetcode.question36;

import java.util.HashSet;
import java.util.Set;

public class Solution {
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
		
		//第一行的三个方块
		for(int times=0; times<=2; times++){
			Set<Character> set = new HashSet<Character>();
			for(int i=0; i<3; i++){
				for(int j=3*times; j<3*times+3; j++){
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
		//第二行的三个方块
		for(int times=0; times<=2; times++){
			Set<Character> set = new HashSet<Character>();
			for(int i=3; i<6; i++){
				for(int j=3*times; j<3*times+3; j++){
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
		//第三行的三个方块
		for(int times=0; times<=2; times++){
			Set<Character> set = new HashSet<Character>();
			for(int i=6; i<9; i++){
				for(int j=3*times; j<3*times+3; j++){
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
		return true;
    }
	
	public static void main(String[] args) {
		char[][] board = {
				{'.','.','.','.','.','.','.','.','2'},
				{'.','.','.','.','.','.','6','.','.'},
				{'.','.','1','4','.','.','8','.','.'},
				{'.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','3','.','.','.','.'},
				{'5','.','8','6','.','.','.','.','.'},
				{'.','9','.','.','.','.','4','.','.'},
				{'.','.','.','.','5','.','.','.','.'}
				
		};
		String [] strAry ={"........2","......6..","..14..8..",".........",".........","....3....","5.86.....",".9....4..","....5...."};
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
		System.out.println(new Solution().isValidSudoku(board));
//		System.out.println('.'=='.');
//		System.out.println("."==".");
//		System.out.println(".".equals("."));
		
		
	}
}
