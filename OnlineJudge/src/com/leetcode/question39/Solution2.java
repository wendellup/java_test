package com.leetcode.question39;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution2 {
//	Set<List<Integer>> retSet = new HashSet<List<Integer>>();
	List<List<Integer>> retSet = new ArrayList<List<Integer>>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
    	iterCandidate(candidates, target, new ArrayList<Integer>());
        return new ArrayList<List<Integer>>(retSet);
    }
    
    private void iterCandidate(int[] candidates, int paramTarget
    		,List<Integer> midList){
    	
    	for(int i=0; i<candidates.length; i++){
    		List<Integer> newList = new ArrayList<Integer>(midList);
    		newList.add(candidates[i]);
    		if(candidates[i]==paramTarget){
//    			Collections.sort(newList);
    			System.out.println("---------------------");
    			System.out.println("candidates:"+Arrays.toString(candidates)+",paramTarget:"+paramTarget+",midList:"+midList);
    			System.out.println(newList);
    			System.out.println("---------------------");
    			retSet.add(newList);
    			
    		}
    		if(candidates[i]>=paramTarget){
    			return;
    		}
    		iterCandidate(candidates, paramTarget-candidates[i],newList);
    		
    		if(i!=(candidates.length-1)){
    			System.out.println("***********i!=(candidates.length-1)**********i:"
    					+i+",candidates:"+Arrays.toString(candidates)
    					+",(candidates.length-1):"+(candidates.length-1));
    			int[] nextCandidates = new int[candidates.length-i-1];
    			System.arraycopy(candidates, i+1, nextCandidates, 0, candidates.length-i-1);
    			iterCandidate(nextCandidates, paramTarget-candidates[i]
    					,newList);
    		}
    	}
    		
    		
    		
//    	}
    	
    }
    
    public static void main(String[] args) {
    	/*
    	List<Integer> midList = new ArrayList<Integer>();
    	midList.add(1);
    	midList.add(2);
    	List<Integer> newList = new ArrayList<Integer>(midList);
    	newList.add(3);
    	System.out.println(midList);
    	System.out.println(newList);
    	*/
    	
//    	int[] candidates = new int[]{2,3,6,7};
//    	int target = 7;
//    	[8,7,4,3], 11
    	
//    	int[] candidates = new int[]{3,4,7,8};
//    	int target = 11;
    	
    	int[] candidates = new int[]{1,2};
    	int target = 3;
    	
    	List<List<Integer>> retList = new Solution2().combinationSum(candidates, target);
    	System.out.println(retList);
	}
}
