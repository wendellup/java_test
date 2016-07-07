package com.leetcode.question92_Reverse_Linked_List_II;

/**
 * 解题思路,先写出Reverse_Linked_List的算法,然后进行修改
 * @author yuchao
 *
 */
public class Solution {
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode helper = new ListNode(0);
		helper.next = head;
		if(head==null || head.next==null || m==n){
			return head;
		}
		
		ListNode curNode = helper;
		ListNode reverseHelper = new ListNode(0);
		//找到开始节点
		for(int i=0; i<m-1; i++){
			curNode = curNode.next;
		}
		ListNode lastNode = curNode; //保存旋转前的最后一个节点
		reverseHelper.next = curNode.next;
		
		// 需要旋转的个数
		int reverseNum = n-m;
		ListNode cur = curNode.next;
		ListNode pre = curNode.next;
		while(cur.next!=null && reverseNum>0){
			cur = cur.next;
			pre.next = cur.next;
			cur.next = reverseHelper.next;
			reverseHelper.next = cur;
			cur = pre;
			reverseNum--;
		}
		lastNode.next = reverseHelper.next;
		
        return helper.next;
    }
	
	public static void main(String[] args) {
		ListNode ln1 = new ListNode(8,null);
		ListNode ln2 = new ListNode(7,ln1);
		ListNode ln3 = new ListNode(6,ln2);
		ListNode.printListNode(new Solution().reverseBetween(null, 1, 2));
	}
}
