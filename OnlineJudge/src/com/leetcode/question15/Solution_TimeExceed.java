package com.leetcode.question15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Solution_TimeExceed {
	
    public List<List<Integer>> threeSum(int[] num) {
    	List<Integer> baseList = new ArrayList<Integer>();
    	List<List<Integer>> resultList = new ArrayList<List<Integer>>();
    	Set<List<Integer>> resultSet = new HashSet<List<Integer>>();
    	
    	for(int i : num){
    		baseList.add(i);
    	}
    	Collections.sort(baseList);
    	for(int i=0; i<baseList.size(); i++){
    		int numI = baseList.get(i);
    		if(numI>0){
    			break;
    		}
    		for(int j=i+1; j<baseList.size(); j++){
    			int numJ = baseList.get(j);
    			if(numI+numJ>0){
    				break;
    			}
    			for(int k=j+1; k<baseList.size(); k++){
    				int numK = baseList.get(k);
        			if(numI + numJ + numK == 0){
        				List<Integer> okPair = new ArrayList<Integer>();
        				okPair.add(numI);
        				okPair.add(numJ);
        				okPair.add(numK);
        				resultSet.add(okPair);
        				break;
        			}
        			if(numI+numJ+numK>0){
        				break;
        			}
            	}
        	}
    	}
    	resultList = new ArrayList(resultSet);
    	return resultList;
    }
    
    public static void main(String[] args) {
    	long beginMills = System.currentTimeMillis();
    	int[] array = {7,-1,14,-12,-8,7,2,-15,8,8,-8,-14,-4,-5,7,9,11,-4,-15,-6,1,-14,4,3,10,-5,2,1,6,11,2,-2,-5,-7,-6,2,-15,11,-6,8,-4,2,1,-1,4,-6,-15,1,5,-15,10,14,9,-8,-6,4,-6,11,12,-15,7,-1,-9,9,-1,0,-4,-1,-12,-2,14,-9,7,0,-3,-4,1,-2,12,14,-10,0,5,14,-1,14,3,8,10,-8,8,-5,-2,6,-11,12,13,-7,-12,8,6,-13,14,-2,-5,-11,1,3,-6};
		System.out.println(new Solution_TimeExceed().threeSum(array));;
		System.out.println("cost:"+(System.currentTimeMillis()-beginMills));
	}
}