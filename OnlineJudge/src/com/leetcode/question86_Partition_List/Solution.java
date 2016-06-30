package com.leetcode.question86_Partition_List;

/**
 * 
 * 参考文章url : http://blog.csdn.net/linhuanmars/article/details/24446871
 * 
 */
public class Solution {
	public ListNode partition(ListNode head, int x) {
		if (head == null)
			return null;
		ListNode helper = new ListNode(0);
		helper.next = head;
		ListNode walker = helper;
		ListNode runner = helper;
		while (runner.next != null) {
			if (runner.next.val < x) {
				if (walker != runner) {
					ListNode next = runner.next.next;
					runner.next.next = walker.next;
					walker.next = runner.next;
					runner.next = next;
				} else
					runner = runner.next;
				walker = walker.next;
			} else {
				runner = runner.next;
			}
		}
		return helper.next;
	}

	public static void main(String[] args) {
		ListNode ln1 = new ListNode(1, null);
		ListNode ln2 = new ListNode(3, ln1);
		ListNode ln3 = new ListNode(2, ln2);
		printListNode(ln3);
		printListNode(new Solution().partition(ln3, 3));

		// ListNode ln1 = new ListNode(2,null);
		// ListNode ln2 = new ListNode(5,ln1);
		// ListNode ln3 = new ListNode(2,ln2);
		// ListNode ln4 = new ListNode(3,ln3);
		// ListNode ln5 = new ListNode(4,ln4);
		// ListNode ln6 = new ListNode(3,ln5);
		// ListNode ln7 = new ListNode(1,ln6);
		// printListNode(ln7);
		// printListNode(new Solution().partition(ln7, 3));
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
