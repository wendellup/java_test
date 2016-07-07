package com.leetcode.question94_Binary_Tree_Inorder_Traversal;

import java.util.ArrayList;
import java.util.List;


/**
 * 中序遍历树节点
 * @author yuchao
 *
 */
public class Solution {
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> result = new ArrayList<Integer>();
		rescurHandler(root, result);
		return result;
    }
	
	public void rescurHandler(TreeNode root, List<Integer> result){
		if(root==null){
			return;
		}
		if(root.left!=null){
			rescurHandler(root.left, result);
			result.add(root.val);
			rescurHandler(root.right, result);
		}else{
			result.add(root.val);
			rescurHandler(root.right, result);
		}
	}
	
	public static void main(String[] args) {
//		TreeNode root = new TreeNode(1);
//		TreeNode node1 = new TreeNode(2);
//		TreeNode node2 = new TreeNode(3);
//		root.left = null;
//		root.right = node1;
//		node1.left = node2;
		
		TreeNode root = new TreeNode(3);
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.left = node1;
		root.right = node2;
				
		System.out.println(new Solution().inorderTraversal(root));
	}
}

