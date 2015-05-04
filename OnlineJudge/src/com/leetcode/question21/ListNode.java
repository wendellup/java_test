package com.leetcode.question21;

public class ListNode {
	public int val;
	public ListNode next;

	public ListNode(int x) {
		val = x;
		next = null;
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