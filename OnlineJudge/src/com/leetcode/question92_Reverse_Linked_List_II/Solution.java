package com.leetcode.question92_Reverse_Linked_List_II;


public class Solution {
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode helper = new ListNode(0);
		helper.next = head;
		ListNode cur = head;
		ListNode pre = head;
		while(cur.next!=null){
//			ListNode curNode = pre.next;
			ListNode nextNode = cur.next;
			cur.next = nextNode.next;
			nextNode.next = cur;
			helper.next = nextNode;
			pre = cur;
			cur = cur.next;
		}
		cur.next = helper.next;
		pre.next = null;
		
        return cur;
    }
	
	public static void main(String[] args) {
		ListNode ln1 = new ListNode(1,null);
		ListNode ln2 = new ListNode(3,ln1);
		ListNode ln3 = new ListNode(2,ln2);
		ListNode.printListNode(new Solution().reverseBetween(ln3, 0, 0));
	}
}
