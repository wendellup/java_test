package com.leetcode.question24;
public class Solution {
	
	public ListNode swapPairs(ListNode head) {
		if(head==null || head.next==null){
			return head;
		}
		ListNode currNode = head;
		ListNode nextNode = head;
		
		int loop = 0;
		int swapVal = 0;
		while(currNode.next!=null){
			loop = (loop+1)%2;
			nextNode = currNode.next;
			if(loop==1){
				swapVal = currNode.val;
				currNode.val = nextNode.val;
				nextNode.val = swapVal;
			}
			currNode = nextNode;
		}
		
        return head;
    }

	public static void main(String[] args) {
		ListNode ln0 = new ListNode(0);
		ListNode ln1 = new ListNode(1);
		ListNode ln2 = new ListNode(2);
		ListNode ln3 = new ListNode(3);
		ListNode ln4 = new ListNode(4);
		
		ln0.next = ln1;
		ln1.next = ln2;
		ln2.next = ln3;
//		ln3.next = ln4;
		
		System.out.println(ln0);
		
		System.out.println(new Solution().swapPairs(ln0));
		
	}
    
}
