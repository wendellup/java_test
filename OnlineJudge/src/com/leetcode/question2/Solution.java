package com.leetcode.question2;
import java.util.ArrayList;
import java.util.List;


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		List<Integer> list1 = convertListNodeToList(l1);
		List<Integer> list2 = convertListNodeToList(l2);
		
		List<Integer> resultList = new ArrayList<Integer>();
		
		int len1 = list1.size();
		int len2 = list2.size();
		int longLen = len1>len2 ? len1 : len2;
		int shortLen = len1>len2 ? len2 : len1;
		
		List<Integer> longList = null;
		List<Integer> shortList = null;
		if(longLen==shortLen){
			longList = list1;
			shortList = list2;
		}else{
			if(longLen == list1.size()){
				longList = list1;
				shortList = list2;
			}else{
				longList = list2;
				shortList = list1;
			}
		}
		
		int carry = 0;
		int j = 0;
		for(int i=0; i<longLen; i++){
			int sum = 0;
			if(j<shortLen){
				sum = longList.get(i) + shortList.get(j) + carry;
			}else{
				sum = longList.get(i) + carry;
			}
			carry = 0;
			if(sum/10 != 0){
				carry = sum/10;
			}
			resultList.add(sum%10);
			j++;
		}
		
		if(carry!=0){
			resultList.add(carry);
		}
		
        return convertListToListNode(resultList);
    }
	
	private static List<Integer> convertListNodeToList(ListNode l){
		if(l == null){
			return new ArrayList<Integer>();
		}
		List<Integer> list = new ArrayList<Integer>();
		ListNode tempNode = l;
		list.add(tempNode.val);
		while((tempNode = tempNode.next) != null){
			list.add(tempNode.val);
		}
		return list;
	}
	
	private static ListNode convertListToListNode(List<Integer> list){
		ListNode preListNode = null;
		ListNode headListNode = null;
		
		for(int i=0; i<list.size(); i++){
			ListNode listNode = new ListNode(list.get(i));
			if(i==0){
				headListNode = listNode;
			}
			if(preListNode!=null){
				preListNode.next = listNode;
			}
			preListNode = listNode;
		}
			
		return headListNode;
	}
	
	private static void printListNode(ListNode listNode){
		if(listNode == null){
			System.out.println("null");
		}
		ListNode tempNode = listNode;
		while(tempNode != null){
			System.out.print(tempNode+",");
			tempNode = tempNode.next;
		}
	}
	
	public static void main(String[] args) {
//		ListNode node1 = new ListNode(1);
//		ListNode node2 = new ListNode(2);
//		ListNode node3 = new ListNode(5);
//		ListNode node4 = new ListNode(4);
//		node1.next = node2;
//		node2.next = node3;
//		node3.next = node4;
//		List<Integer> list = convertListNodeToList(node1);
//		System.out.println(list);
//		ListNode listNodeAfter = convertListToListNode(list);
//		printListNode(listNodeAfter);
//		
//		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
		
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(2);
		list1.add(4);
		list1.add(3);
		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(5);
		list2.add(6);
		list2.add(4);
		
		printListNode(new Solution().addTwoNumbers(
				convertListToListNode(list1), convertListToListNode(list2)));
		
	}
	

}