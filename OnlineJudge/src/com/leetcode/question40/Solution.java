package com.leetcode.question40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
	Set<List<Integer>> retSet = new HashSet<List<Integer>>();
//	List<List<Integer>> retSet = new ArrayList<List<Integer>>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
    	iterCandidate(candidates, target, new ArrayList<Integer>());
        return new ArrayList<List<Integer>>(retSet);
    }
    
    private void iterCandidate(int[] candidates, int paramTarget
    		,List<Integer> midList){
    	int i=0;
//    	if(midList.size()!=0){
//    		int lastVal = midList.get(midList.size()-1);
//    		for(int x=0; x<candidates.length; x++){
//    			if(candidates[x] == lastVal){
//    				i = x;
//    				break;
//    			}
//    		}
//    	}
    	for(; i<candidates.length; i++){
    		List<Integer> newList = new ArrayList<Integer>(midList);
    		newList.add(candidates[i]);
    		if(candidates[i]==paramTarget){
    			Collections.sort(newList);
    			retSet.add(newList);
    			
    		}
    		if(candidates[i]>=paramTarget){
    			return;
    		}
    		
    		if(i!=(candidates.length-1)){
    			int[] nextCandidates = new int[candidates.length-i-1];
    			System.arraycopy(candidates, i+1, nextCandidates, 0, candidates.length-i-1);
    			iterCandidate(nextCandidates, paramTarget-candidates[i]
    					,newList);
    		}
    	}
    	
    }
    public static void main(String[] args) {
		int[] arrayInt = new int[]{10,1,2,7,6,1,5};
		int target = 8;
		System.out.println(new Solution().combinationSum2(arrayInt, target));
		
	}
}
