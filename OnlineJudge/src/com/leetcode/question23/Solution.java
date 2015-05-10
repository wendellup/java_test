package com.leetcode.question23;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		ListNode head = new ListNode(-1);
		ListNode current = head;
		while (list1 != null && list2 != null) {
			if (list1.val < list2.val) {
				current.next = list1;
				list1 = list1.next;
			} else {
				current.next = list2;
				list2 = list2.next;
			}
			current = current.next;
		}
		if (list1 != null) {
			current.next = list1;
		} else {
			current.next = list2;
		}
		return head.next;
	}

	public ListNode mergeKLists(List<ListNode> lists) {
		if (lists == null || lists.size() == 0) {
			return null;
		}
		ListNode head = lists.get(0);
		for (int i = 1; i < lists.size(); i++) {
			head = mergeTwoLists(head, lists.get(i));
		}
		return head;
	}
	
	public static void main(String[] args) {
		ListNode ln0 = new ListNode(0);
		ListNode ln1 = new ListNode(123);
		ListNode ln2 = new ListNode(222);
		ListNode ln3 = new ListNode(3546);
		ListNode ln4 = new ListNode(23546);
		ln0.next = ln1;
		ln1.next = ln2;
		ln2.next = ln3;
		ln3.next = ln4;
		
		ListNode ln10 = new ListNode(6);
		ListNode ln11 = new ListNode(17);
		ListNode ln12 = new ListNode(200);
		ListNode ln13 = new ListNode(323);
		ListNode ln14 = new ListNode(4444);
		ln10.next = ln11;
		ln11.next = ln12;
		ln12.next = ln13;
		ln13.next = ln14;
		
		ListNode ln20 = new ListNode(44);
		ListNode ln21 = new ListNode(55);
		ListNode ln22 = new ListNode(77);
		ln20.next = ln21;
		ln21.next = ln22;
		
		
		List<ListNode> lists = new ArrayList<ListNode>();
		lists.add(ln0);
		lists.add(ln10);
		lists.add(ln20);
		
		System.out.println(new Solution().mergeKLists(lists));
	}
}