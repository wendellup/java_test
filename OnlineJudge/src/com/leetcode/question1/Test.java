package com.leetcode.question1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
  	Two Sum
	Given an array of integers, find two numbers such that they add up to a specific target number.
	
	The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
	
	You may assume that each input would have exactly one solution.
	
	Input: numbers={2, 7, 11, 15}, target=9
	Output: index1=1, index2=2
	
 * @author yuchao
 *
 */
public class Test {
	/**
	 * 不能用O(n²)的算法,会超时
	 */
	public int[] twoSum2(int[] numbers, int target) {
		int[] retAry = new int[2];
        for(int i=0; i<numbers.length; i++){
        	for(int j=i+1; j<numbers.length; j++){
        		if(numbers[i]+numbers[j]==target){
        			retAry[0] = i+1;
        			retAry[1] = j+1;
        			return retAry;
        		}
        	}
        }
        return retAry;
    }
	
	public int[] twoSum(int[] numbers, int target) {
		int[] retAry = new int[2];
		Map<Integer, List<Integer>> map = new LinkedHashMap<Integer, List<Integer>>();
        for(int i=0; i<numbers.length; i++){
        	List<Integer> list = map.get(numbers[i]);
        	if(list == null){
        		list = new ArrayList<Integer>();
        	}
        	list.add(i);
        	map.put(numbers[i], list);
        }
        for(int i=0; i<numbers.length; i++){
        	int num = numbers[i];
        	int remainNum = target - num;
        	List<Integer> list = map.get(remainNum);
        	if(list==null){
        		continue;
        	}else{
        		int idx = list.get(0);
        		if(idx == i && list.size()>1){
        			idx = list.get(1);
        		}else if(idx == i && list.size()==1){
        			continue;
        		}
        		retAry[0] = i + 1;
        		retAry[1] = idx + 1;
        		break;
        	}
        }
        
        return retAry;
    }
	
	public static void main(String[] args) {
		int[] numbers = new int[]{3,2,4};
		int target = 6;
		int[] retAry = new Test().twoSum(numbers, target);
		for(int i=0; i<retAry.length; i++){
			System.out.println(retAry[i]);
		}
	}
}
