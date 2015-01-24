package com.leetcode.question4;
public class Solution {
    public double findMedianSortedArrays(int A[], int B[]) {
        
        int lenA = A.length;
        int lenB = B.length;
        if(lenA+lenB == 1){
            if(lenA==1){
                return A[0];
            }else{
                return B[0];
            }
        }
        
        if(lenA+lenB == 0){
            return 0;
        }
        int midIdx = 0;
        int midSum = 0;
        int odds = (lenA+lenB)%2;
        if(odds==0){
            midIdx = (lenA+lenB)/2;
        }else{
            midIdx = (lenA+lenB-1)/2;
        }
        
        boolean aOver = false;
        boolean bOver = false;
        
        if(lenA==0){
        	aOver = true;
        }
        if(lenB==0){
        	bOver = true;
        }
        
        for(int i=0,j=0; (i+j)<=midIdx; ){
        	int curVal = 0;
        	if(aOver){
        		curVal = B[j];
        	}else if(bOver){
        		curVal = A[i];
        	}else if(A[i]<B[j]){
        		curVal = A[i];
        	}else{
        		curVal = B[j];
        	}
        	
            if(odds==0 && (i+j)==(midIdx-1)){
                 midSum = midSum + curVal;
            }
            
            if((i+j)==(midIdx)){
            	midSum = midSum + curVal;
            }
            
            
            if(aOver){
        		j++;
        	}else if(bOver){
        		i++;
        	}else if(A[i]<B[j]){
        		if(i<lenA-1){
        			i++;
        		}else{
        			i++;
        			aOver = true;
        		}
        	}else{
        		if(j<lenB-1){
        			j++;
        		}else{
        			j++;
        			bOver = true;
        		}
        	}
        }
        
        if(odds==0){
            return midSum/2.0;
        }else{
            return midSum;
        }
    }
    
    public static void main(String[] args) {
    	int[] A = {1,2,3,4};
    	int[] B = {5,6};
		System.out.println(new Solution().findMedianSortedArrays(A, B));
	}
}