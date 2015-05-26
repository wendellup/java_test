package com.leetcode.question30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> retList = new ArrayList<Integer>();
		Map<String, Integer> baseMap = new HashMap<String, Integer>();
		if(words.length==0){
			return retList;
		}
		int wordLen = words[0].length();
		if(wordLen==0){
			return retList;
		}
		
		//初始化map
		for(String word : words){
			if(baseMap.containsKey(word)){
				baseMap.put(word, baseMap.get(word)+1);
			}else{
				baseMap.put(word, 1);
			}
		}
		
		//遍历s
		for(int i=0; i<(s.length()-wordLen*words.length+1); i++){
			Map<String, Integer> currentMap = new HashMap<String, Integer>();
			boolean isOk = true;
			for(int j=i; j<(wordLen*words.length+i); j=j+wordLen){
				String str = s.substring(j, j+wordLen);
				if(!baseMap.containsKey(str)){
					isOk = false;
					break;
				}
				if(currentMap.containsKey(str)){
					currentMap.put(str, currentMap.get(str)+1);
				}else{
					currentMap.put(str, 1);
				}
				if(baseMap.get(str)<currentMap.get(str)){
					isOk = false;
					break;
				}
			}
			if(isOk){
				retList.add(i);
			}
		}
		
        return retList;
    }
	
	public static void main(String[] args) {
		String s = "barfoothefoobarman";
		String[] words = new String[]{"foo", "bar"};
		System.out.println(new Solution().findSubstring(s,words));
	}
}
