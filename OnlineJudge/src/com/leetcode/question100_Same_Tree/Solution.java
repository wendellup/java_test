package com.leetcode.question100_Same_Tree;

import com.leetcode.question94_Binary_Tree_Inorder_Traversal.TreeNode;

public class Solution {
	public boolean isSameTree(TreeNode p, TreeNode q) {
		return rescureHander(p,q);
    }
	
	public boolean rescureHander(TreeNode p, TreeNode q){
		if(p==null && q==null){
			return true;
		}
		if((p==null && q!=null) || (q==null && p!=null)){
			return false;
		}
		if(p.val != q.val){
			return false;
		}
		boolean leftEq = rescureHander(p.left, q.left);
		if(!leftEq){
			return false;
		}
		boolean rightEq = rescureHander(p.right, q.right);
		if(!rightEq){
			return false;
		}
		return true;
	}
}
