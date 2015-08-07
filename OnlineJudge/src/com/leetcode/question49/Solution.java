package com.leetcode.question49;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;



public class Solution {
	   
	    public static void main(String[] args) {  
	   
	    }  
	   
	    public ArrayList<String> anagrams(String[] strs) {  
	        ArrayList<String> ret = new ArrayList<String>();  
	           
	        // 用排序过的string作为key，它的anagram作为ArrayList  
	        Hashtable<String, ArrayList<String>> ht = new Hashtable<String, ArrayList<String>>();  
	           
	        for(int i=0; i<strs.length; i++){  
	            String sorted = sorted(strs[i]);  
	            ArrayList<String> val = ht.get(sorted);  
	            if(val != null){  
	                val.add(strs[i]);  
	            }else{  
	                val = new ArrayList<String>();  
	                val.add(strs[i]);  
	                ht.put(sorted, val);  
	            }  
	        }  
	           
	        // Hashtable的循环方法 keySet   
	        Set<String> set = ht.keySet();  
	           
	        // 把所有anagram添加到ret中  
	        for(String s : set){  
	            ArrayList<String> val = ht.get(s);  
	            if(val.size() > 1){  
	                ret.addAll(val);  
	            }  
	        }  
	           
	        return ret;  
	    }  
	       
	    public String sorted(String a){  
	        char[] achar = a.toCharArray();  
	        Arrays.sort(achar);  
	        return new String(achar);  
	    }  
	       
}
