package com.leetcode.question88_Merge_Sorted_Array;


public class Test {
	public static void printListNode(ListNode node){
		while(node!=null){
			System.out.print(node.val);
			System.out.print("->");
			node = node.next;
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		ListNode ln1 = new ListNode(1,null);
		ListNode ln2 = new ListNode(3,ln1);
		ListNode ln3 = new ListNode(2,ln2);
		printListNode(ln3);
		new Test().changeListNode(ln3);
		printListNode(ln3);
	}
	
	public void changeListNode(ListNode node){
		node=null;
//		ListNode ln1 = new ListNode(666,null);
//		node.next = ln1;
	}
	
}
