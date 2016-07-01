package com.leetcode.question92_Reverse_Linked_List_II;

public class ListNode {
	int val;
	ListNode next;

	ListNode(int x, ListNode nextNode) {
		val = x;
		this.next = nextNode;
	}
	ListNode(int x) {
		val = x;
	}
	
	public static void printListNode(ListNode node){
		while(node!=null){
			System.out.print(node.val);
			System.out.print("->");
			node = node.next;
		}
		System.out.println();
	}
}