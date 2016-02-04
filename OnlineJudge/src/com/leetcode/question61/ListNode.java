package com.leetcode.question61;

public class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
	
	@Override
	public String toString() {
		if(next!=null){
			return "->"+val + next;
		}else{
			return "->"+val;
		}
			
//		System.out.print("->"+val);
	}
}