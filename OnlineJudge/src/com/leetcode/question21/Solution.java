package com.leetcode.question21;


public class Solution {
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		// Note: The Solution object is instantiated only once and is reused by
		// each test case.
		ListNode p, q, r, head;
		if (l1 == null && l2 == null) {
			return null;
		}
		if (l1 == null) {
			return l2;
		}
		if (l2 == null) {
			return l1;
		}
		p = l1;
		q = l2;
		head = null;
		if (p.val <= q.val) {
			head = p;
			p = p.next;
		} else {
			head = q;
			q = q.next;
		}
		r = head;
		while (p != null && q != null) {
			if (p.val <= q.val) {
				r.next = p;
				r = p;
				p = p.next;
			} else {
				r.next = q;
				r = q;

				q = q.next;
			}
		}
		if (p == null) {
			r.next = q;
		} else {
			r.next = p;
		}
		return head;
	}
	
	public static void main(String[] args) {
		ListNode ln0 = new ListNode(0);
		ListNode ln1 = new ListNode(1);
		ListNode ln2 = new ListNode(2);
		ListNode ln3 = new ListNode(3);
		ListNode ln4 = new ListNode(4);
		
		ln0.next = ln2;
		ln2.next = ln3;
		ln3.next = ln4;
		
		System.out.println(new Solution().mergeTwoLists(ln0, ln1));
	}
}