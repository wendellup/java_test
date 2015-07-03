package com.leetcode.question42;

import java.util.ArrayList;
import java.util.List;

public class Solution {

	//结题步骤:
	//1.遍历高度数组,找出所有的小凹
	//2.合并可以相邻的可以合并的小凹
	//3.计算每个小凹的存水量,求和后返回
	
	public int trap(int[] height) {
		//长度少于3,围不成凹
		if(height.length<3){
			return 0;
		}
		
		List<List<Integer>> potaintialList = new ArrayList<List<Integer>>();
		boolean beginFlag = false;
		int preVal = height[0];
		List<Integer> list = null;
		int minVal = -1;
		
		
		//1.遍历高度数组,找出所有的小凹
		for(int i=1; i<height.length; i++){
			int curVal = height[i];
			if(beginFlag){
				if(i==(height.length-1)){
					//如果是最后一个元素,先判断是否构成凹,然后遍历结束
					if(height[i]>minVal){
						list.add(curVal);
						potaintialList.add(list);
					}
					
					break;
				}
				int nextVal = height[i+1];
				if(nextVal<curVal && curVal>minVal){
					list.add(curVal);
					potaintialList.add(list);
					beginFlag = false;
				}else{
					list.add(curVal);
					if(curVal<minVal){
						minVal = curVal;
					}
				}
			}else{
				if(preVal>curVal){
					beginFlag = true;
					minVal = curVal;
					list = new ArrayList<Integer>();
					list.add(preVal);
					list.add(curVal);
				}
			}
			preVal = curVal;
		}
		
		//2.合并可以相邻的可以合并的小凹
		/*
		if(potaintialList.size()>=2){
			List<List<Integer>> potaintialListCp = new ArrayList<List<Integer>>();
			
			List<Integer> preList = potaintialList.get(0);
			for(int i=1; i<potaintialList.size(); i++){
				List<Integer> curList = potaintialList.get(i);
				if(preList.get(0)>preList.get(preList.size()-1)
						&& preList.get(preList.size()-1)<curList.get(curList.size()-1)){
					preList.remove(preList.size()-1);
					preList.addAll(curList);
				}else{
					potaintialListCp.add(preList);
					preList = curList;
				}
			}
			potaintialListCp.add(preList);
			potaintialList = potaintialListCp;
			
		}
		*/
		if(potaintialList.size()>=2){
			List<Integer> heightList = new ArrayList<Integer>();
			for(int i=0; i<potaintialList.size(); i++){
				heightList.add(potaintialList.get(i).get(0));
				if(i==potaintialList.size()-1){
					heightList.add(potaintialList.get(i).get(potaintialList.get(i).size()-1));	
				}
			}
			
			for(int k=0; k<heightList.size(); k++){
				List<List<Integer>> potaintialListCp = new ArrayList<List<Integer>>();
				heightList = new ArrayList<Integer>();
				for(int i=0; i<potaintialList.size(); i++){
					heightList.add(potaintialList.get(i).get(0));
					if(i==potaintialList.size()-1){
						heightList.add(potaintialList.get(i).get(potaintialList.get(i).size()-1));	
					}
				}
				List<Integer> preList = potaintialList.get(0);
				int beginHeight = heightList.get(0);
				for(int i=1; i<heightList.size()-1; i++){
					List<Integer> curList = potaintialList.get(i);
					if(heightList.get(i)<beginHeight
							&& heightList.get(i)<=heightList.get(i+1)){
						preList.remove(preList.size()-1);
						preList.addAll(curList);
					}else{
						potaintialListCp.add(preList);
						beginHeight = heightList.get(i);
						preList = potaintialList.get(i);
					}
				}
				
				potaintialListCp.add(preList);
				potaintialList = potaintialListCp;
			}
			
		}
		
		//3.计算每个小凹的存水量,求和后返回
		int retInt = 0;
		for(List<Integer> srcList : potaintialList){
			if(srcList.size()<3){
				continue;
			}
			int beginNum = srcList.get(0);
			int endNum = srcList.get(srcList.size()-1);
			int maxNum = endNum>beginNum?beginNum:endNum;
			for(int i=1; i<srcList.size()-1; i++){
				if(srcList.get(i)<maxNum){
					retInt = retInt+(maxNum-srcList.get(i));
				}
			}
					
		}
		
        return retInt;
    }
	
	public static void main(String[] args) {
		int[] inAry = new int[]{0,1,0,2,1,0,1,3,2,1,2,1,3}; //11
//		int[] inAry = new int[]{5,4,1,2}; //1
//		int[] inAry = new int[]{4,2,0,3,2,4,3,4}; //10
//		int[] inAry = new int[]{6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3}; //83
//		int[] inAry = new int[]{8,8,1,5,6,2,5,3,3,9}; //31
		
		int result = new Solution().trap(inAry);
		System.out.println(result);
		
	}
	
}
