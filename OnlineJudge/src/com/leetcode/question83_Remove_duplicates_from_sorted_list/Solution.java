package com.leetcode.question83_Remove_duplicates_from_sorted_list;

public class Solution {
	public ListNode deleteDuplicates(ListNode head) {
		ListNode pre = head;
		ListNode cur = head;
		
		if(cur==null || cur.next==null){
			return head;
		}
		while(cur.next!=null){
			cur = cur.next;
			if(pre.val!=cur.val){
				pre.next = cur;
				pre = pre.next;
			}
		}
		if(pre.val!=cur.val){
			pre.next = cur;
		}else{
			pre.next = null;
		}
		
		return head;
	}
	
	public static void main(String[] args) {
		ListNode ln1 = new ListNode(2,null);
		ListNode ln2 = new ListNode(1,ln1);
		ListNode ln3 = new ListNode(1,ln2);
		printListNode(ln3);
		printListNode(new Solution().deleteDuplicates(ln3));
		
//		ListNode ln1 = new ListNode(3,null);
//		ListNode ln2 = new ListNode(3,ln1);
//		ListNode ln3 = new ListNode(2,ln2);
//		ListNode ln4 = new ListNode(1,ln3);
//		ListNode ln5 = new ListNode(1,ln4);
//		printListNode(ln5);
//		printListNode(new Solution().deleteDuplicates(ln5));
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

// Definition for singly-linked list.
class ListNode {
	int val;
	ListNode next;

	ListNode(int x, ListNode nextNode) {
		val = x;
		this.next = nextNode;
	}
}
