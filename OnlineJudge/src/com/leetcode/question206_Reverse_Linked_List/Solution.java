package com.leetcode.question206_Reverse_Linked_List;

public class Solution {
    public ListNode reverseList(ListNode head) {
    	ListNode helper = new ListNode(0);
		helper.next = head;
		if(head==null || head.next==null){
			return head;
		}
		
		ListNode cur = head;
		ListNode pre = head;
		while(cur.next!=null){
			cur = cur.next;
			pre.next = cur.next;
			cur.next = helper.next;
			helper.next = cur;
			
			cur = pre;
		}
		
        return helper.next;
    }
    
    public static void main(String[] args) {
		ListNode ln1 = new ListNode(1,null);
		ListNode ln2 = new ListNode(6,ln1);
		ListNode ln3 = new ListNode(2,ln2);
		ListNode.printListNode(new Solution().reverseList(ln1));
	}
}
