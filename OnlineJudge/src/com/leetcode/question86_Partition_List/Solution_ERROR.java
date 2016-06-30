package com.leetcode.question86_Partition_List;


/**
 * 理解错误。。。。。。。。。
 * @author yuchao
 * 考虑的情况比较多:
 * 	a. [3, 1] 2
 *  b. [1, 1] 2
 *  c. [1, 2, 3] 3  
 *  d. [2, 3, 1] 3 -> [2, 1, 3]
 *  
 *
 */
public class Solution_ERROR {
	public static void printListNode(ListNode node){
		while(node!=null){
			System.out.print(node.val);
			System.out.print("->");
			node = node.next;
		}
		System.out.println();
	}
	
	public ListNode partition(ListNode head, int x) {
		if(head==null || head.next==null){
			return head;
		}
		ListNode curNode = head;
		ListNode xNode = null;
		ListNode preNode = head;
		// 第一次遍历,若list中所有元素都比x小,则返回原链表
		while (curNode != null) {
			if(curNode.val<x){
				curNode = curNode.next;
			}else{
				break;
			}
		}
		if(curNode==null){
			return head;
		}
		curNode = head;
		// 第二次遍历,找到大小等于x的位置
		while(curNode!=null){
			if(curNode.val==x){
				xNode = curNode;
				break;
			}
			curNode = curNode.next;
		}	
		if(xNode==null){
			curNode = head;
			// 第三次遍历,找到比x节点小的位置
			while(curNode!=null){
				if(curNode.val==x){
					xNode = curNode;
					break;
				}else if(curNode.val<x){
					xNode = preNode;
					break;
				}
				preNode = curNode; 
				curNode = curNode.next;
			}
		}
		// 没找到x节点,或x节点为最后一个节点,直接返回当前链表
		if(xNode==null || xNode.next==null){
			return head;
		}
		// 从x节点开始处理链表排序
		curNode = xNode.next;
		while(curNode!=null){
			if(curNode.val>=x){
				xNode = curNode;
				curNode = curNode.next;
			}else{
				xNode.next = curNode.next;
				printListNode(head);
				insert(head, curNode);
				printListNode(head);
				curNode = xNode.next;
			}
		}
        return head;
    }
	
	// 将insertNode插入xNode之前的正确位置
	public ListNode insert(ListNode head, ListNode insertNode){
		//判断insertNode是否插入在第一个位置
		if(insertNode.val<head.val){
			insertNode.next = head;
			head = insertNode;
		}else{
			ListNode curNode = head;
			ListNode preNode = head;
			while(curNode.val<insertNode.val && curNode.next!=null){
				preNode = curNode;
				curNode = curNode.next;
			}
			insertNode.next = preNode.next;
			preNode.next = insertNode;
		}
		printListNode(head);
		return head;
	}
	
	public static void main(String[] args) {
		ListNode ln1 = new ListNode(1,null);
		ListNode ln2 = new ListNode(3,ln1);
		ListNode ln3 = new ListNode(2,ln2);
//		printListNode(ln3);
//		printListNode(new Solution_ERROR().partition(ln3, 3));
		
		new Solution_ERROR().partition(ln3, 3);
		
//		ListNode ln1 = new ListNode(2,null);
//		ListNode ln2 = new ListNode(5,ln1);
//		ListNode ln3 = new ListNode(2,ln2);
//		ListNode ln4 = new ListNode(3,ln3);
//		ListNode ln5 = new ListNode(4,ln4);
//		ListNode ln6 = new ListNode(3,ln5);
//		ListNode ln7 = new ListNode(1,ln6);
//		printListNode(ln7);
//		printListNode(new Solution().partition(ln7, 3));
	}
}


