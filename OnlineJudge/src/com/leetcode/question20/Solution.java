package com.leetcode.question20;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
	Valid Parentheses 
	Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
	The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 */
public class Solution {
	
	static Map<Character, Character> charMap = new HashMap<Character, Character>();
	static{
		charMap.put(')', '(');
		charMap.put('}', '{');
		charMap.put(']', '[');
		
	}
	
    public boolean isValid(String s) {
    	
    	
    	Stack<Character> charStack = new Stack<Character>();
    	
    	for(int i=0; i<s.length(); i++){
    		char c = s.charAt(i);
    		if('('==c || '{'==c || '['==c){
    			charStack.push(c);
    		}else{
    			if(charStack.size()==0){
    	    		return false;
    	    	}
    			char popChar = charStack.pop();
    			if(popChar != charMap.get(c)){
    				return false;
    			}
    		}
    	}
    	
    	if(charStack.size()==0){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static void main(String[] args) {
		boolean isValid = new Solution().isValid("([])");
		System.out.println(isValid);
	}
}