package com.leetcode.question93_Restore_IP_Address;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	public static void main(String[] args) {
//		String s = "25525011135";
//		String s = "0000";
		String s = "010010";
//		String s = "1111";
		System.out.println(new Solution().restoreIpAddresses(s));
	}
	
	public List<String> restoreIpAddresses(String s) {
		List<String> result = new ArrayList<String>();
		dfsHandle(result,"", s, 4);
        return result;
    }
	
	public void dfsHandle(List<String> result, String handledStr, String toHandlerStr, int toFindNum){
		// 剩余待查找字符串长度和ip地址不匹配
		if(toHandlerStr.length()<toFindNum || toHandlerStr.length()>3*toFindNum){
			return;
		}
		if(toFindNum==1){
			if(toHandlerStr.startsWith("0")&&toHandlerStr.length()>1){
				return;
			}
			int val = Integer.valueOf(toHandlerStr);
			if(val>=0 && val<=255){
				handledStr = handledStr + "."+toHandlerStr;
				result.add(handledStr);
				return;
			}
		}else{
			for(int i=1; i<=3&&i<=toHandlerStr.length(); i++){
				String tempStr = toHandlerStr.substring(0, i);
				String nextHandledStr = null;
				int val = Integer.valueOf(tempStr);
				if(val>0 && val<=255){
					if(toFindNum==4){
						nextHandledStr = tempStr;
					}else{
						nextHandledStr = handledStr + "."+val;
					}
					dfsHandle(result, nextHandledStr, toHandlerStr.substring(i), toFindNum-1);
					
				}else if(val==0){
					if(toFindNum==4){
						nextHandledStr = tempStr;
					}else{
						nextHandledStr = handledStr + "."+val;
					}
					dfsHandle(result, nextHandledStr, toHandlerStr.substring(i), toFindNum-1);
					break;
				}
			}
		}
		
	}
}
