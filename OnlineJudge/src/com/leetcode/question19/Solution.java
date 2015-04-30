package com.leetcode.question19;
public class Solution {
	
    public ListNode removeNthFromEnd(ListNode head, int n) {
    	
    	int nodeLength = getNodeLength(head);
    	
    	n = nodeLength - n;
    	
    	if(n == 0){
    		//如果是第一个节点
    		return head.next;
    	}
    	ListNode preNode = head;
    	ListNode currNode = head.next;
    	int idx = 1;
    	
    	while(idx < n){
    		idx++;
    		preNode = preNode.next;
    		currNode = currNode.next;
    	}
    	
    	//如果是最后一个节点
    	if(currNode == null){
    		preNode = null;
    		return head;
    	}
    	//如果是中间节点
    	currNode = currNode.next;
    	preNode.next = currNode;
    	return head;
    }
    
    private int getNodeLength(ListNode head) {
    	int len = 0;
    	while(head != null){
    		head = head.next;
    		len++;
    	}
		return len;
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
		ln3.next = ln4;
		
		System.out.println(ln0);
		
		System.out.println(new Solution().removeNthFromEnd(ln0, 2));
	}
    
}
