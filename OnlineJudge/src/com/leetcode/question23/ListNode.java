package com.leetcode.question23;
class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(next!=null){
			return "->"+val + next;
		}else{
			return "->"+val;
		}
	}
}