package com.leetcode.question82_Remove_duplicates_from_sorted_list_II;

/**
 * 新建一个ListNode来实现
 * @author yuchao
 *
 */
public class Solution {
	public static void printListNode(ListNode node){
		while(node!=null){
			System.out.print(node.val);
			System.out.print("->");
			node = node.next;
		}
		System.out.println();
	}
	
	public ListNode deleteDuplicates(ListNode head) {
		ListNode pre = head;
		ListNode cur = head;
		
		if(cur==null || cur.next==null){
			return head;
		}
		
		ListNode newHead = null;
		ListNode newCur = null;
		boolean isRepeat = false;
		
		while(cur.next!=null){
			cur = cur.next;
			if(pre.val!=cur.val){
				if(!isRepeat){
					if(newHead==null){
						newCur = new ListNode(pre.val);
						newHead = newCur;
					}else{
						ListNode ln = new ListNode(pre.val);
						newCur.next = ln;
						newCur = ln;
					}
				}
				pre = cur;
				isRepeat = false;
			}else{
				isRepeat = true;
			}
		}
		if(!isRepeat){
			
			if(newHead==null){
				newCur = new ListNode(cur.val);
				newHead = newCur;
			}else{
				ListNode ln = new ListNode(cur.val);
				newCur.next = ln;
				newCur = ln;
			}
		}
		
		return newHead;
    }
	
	public static void main(String[] args) {
//		ListNode ln1 = new ListNode(2,null);
//		ListNode ln2 = new ListNode(1,ln1);
//		ListNode ln3 = new ListNode(1,ln2);
//		printListNode(ln3);
//		printListNode(new Solution().deleteDuplicates(ln3));
		
//		ListNode ln1 = new ListNode(3,null);
//		ListNode ln2 = new ListNode(3,ln1);
//		ListNode ln3 = new ListNode(2,ln2);
//		ListNode ln4 = new ListNode(1,ln3);
//		ListNode ln5 = new ListNode(1,ln4);
//		printListNode(ln5);
//		printListNode(new Solution().deleteDuplicates(ln5));
	}
}

//Definition for singly-linked list.
class ListNode {
	int val;
	ListNode next;

//	ListNode(int x, ListNode nextNode) {
//		val = x;
//		this.next = nextNode;
//	}
	ListNode(int x) {
		val = x;
	}
}