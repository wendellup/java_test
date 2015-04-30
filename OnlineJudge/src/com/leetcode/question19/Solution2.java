
package com.leetcode.question19;




/**
 * 思路1：双指针思想，两个指针相隔n-1，每次两个指针向后一步，当后面一个指针没有后继了，
 * 前面一个指针就是要删除的节点。注意：删除节点时需要删除指针的前驱pre；增加dummy head处理删除头节点的特殊情况。
 */
public class Solution2 {
	
	public ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode ln1 = head;
		ListNode ln2 = head;
		ListNode lnPre = null;
		int idx = 1;
		while(ln1.next != null){
			ln1 = ln1.next;
			idx++;
			if(idx > n){
				lnPre = ln2;
				ln2 = ln2.next;
			}
		}
		
		if(idx == n){
			return head.next;
		}else{
			lnPre.next = ln2.next;
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
		ln3.next = ln4;
		
		System.out.println(ln0);
		
		System.out.println(new Solution2().removeNthFromEnd(ln0, 1));
	}
    
}
