package com.leetcode.question98_Validate_Binary_Search_Tree;

import com.leetcode.question94_Binary_Tree_Inorder_Traversal.TreeNode;

//参考url:http://blog.csdn.net/linhuanmars/article/details/23810735
public class Solution {
	public boolean isValidBST(TreeNode root) {
//		return firstRescureHandler(root);
		return rescureHandler(root, Long.MAX_VALUE, Long.MIN_VALUE);
	}

	
	private boolean rescureHandler(TreeNode root, long maxVal, long minVal) {
		if (root == null) {
			return true;
		}
		if (root.val>=maxVal || root.val<=minVal) {
			return false;
		}else{
			return rescureHandler(root.left, root.val, minVal)&&
					rescureHandler(root.right, maxVal, root.val);
		}
	}
	
	public static void main(String[] args) {
		TreeNode node1 = new TreeNode(3);
//		TreeNode node2 = new TreeNode(1);
//		TreeNode node3 = new TreeNode(5);
//		TreeNode node4 = new TreeNode(0);
//		TreeNode node5 = new TreeNode(2);
//		TreeNode node6 = new TreeNode(4);
//		TreeNode node7 = new TreeNode(6);
//		TreeNode node8 = new TreeNode(3);
//		node1.left = node2;
//		node1.right = node3;
//		node2.left = node4;
//		node2.right = node5;
//		node3.left = node6;
//		node3.right = node7;
//		node6.left = node8;
		System.out.println(new Solution().isValidBST(node1));
	}
}
