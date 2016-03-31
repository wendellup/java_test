package com.leetcode.question69_SqrtX;
import java.util.HashSet;
import java.util.Set;

public class Solution2 {
	private Set<Long> doneSet = new HashSet<Long>();
	private long exceedToTarget;
	
    public int mySqrt(int x) {
    	if(x==1){
    		return 1;
    	}
    	
    	return new Long(halfHandle(x, x)).intValue();
    }
    
    private long halfHandle(long x, int target) {
    	long halfX = x/2; 
    	if(doneSet.contains(halfX)){
    		return exceedToTarget;
    	}
    	doneSet.add(halfX);
    	long nowMulptiply = halfX*halfX;
    	if(nowMulptiply>exceedToTarget && nowMulptiply<target){
    		exceedToTarget = halfX;
    	}
    	if(nowMulptiply==target){
    		return halfX;
    	}else if(nowMulptiply<target){
    		return halfHandle(x+halfX, target); 
    	}else if(nowMulptiply>target){
    		return halfHandle(halfX, target);
    	}
    	
		return 0;
	}
}