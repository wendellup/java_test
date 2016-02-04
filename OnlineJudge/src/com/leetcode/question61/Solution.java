package com.leetcode.question61;

import java.util.List;

public class Solution {
	
	public static void main(String[] args) {
		ListNode listNodeHead = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		listNodeHead.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		System.out.println(listNodeHead);
		System.out.println(new Solution().rotateRight(listNodeHead, 10));
	}
	
	
    public ListNode rotateRight(ListNode head, int k) {
    	if(head==null){
    		return null;
    	}
    	//获取节点数
        int nodeNum = 1;
        ListNode curNode = head;
        while(curNode.next!=null){
        	nodeNum++;
        	curNode = curNode.next;
        }
        
        //开始旋转
        k = k%nodeNum;
        if(k==0){
        	return head;
        }
        curNode = head;
        for(int i=1; i<(nodeNum-k); i++){
        	curNode = curNode.next;
        }
        //新的头结点
        ListNode newHeadNode = curNode.next;
        //新的尾结点
        ListNode newEndNode = curNode;
        while(curNode.next!=null){
        	curNode = curNode.next;
        }
        curNode.next = head;
        newEndNode.next = null;
        return newHeadNode;
    }
}