package com.leetcode.question95_Unique_Binary_Search_Trees_II;

import java.util.ArrayList;

import com.leetcode.question94_Binary_Tree_Inorder_Traversal.TreeNode;

public class Solution {
	public ArrayList<TreeNode> generateTrees(int n) {
		if(n==0){
			return new ArrayList<TreeNode>();
		}
		return helper(1, n);
	}

	private ArrayList<TreeNode> helper(int left, int right) {
		ArrayList<TreeNode> res = new ArrayList<TreeNode>();
		if (left > right) {
			res.add(null);
			return res;
		}
		for (int i = left; i <= right; i++) {
			ArrayList<TreeNode> leftList = helper(left, i - 1);
			ArrayList<TreeNode> rightList = helper(i + 1, right);
			for (int j = 0; j < leftList.size(); j++) {
				for (int k = 0; k < rightList.size(); k++) {
					TreeNode root = new TreeNode(i);
					root.left = leftList.get(j);
					root.right = rightList.get(k);
					res.add(root);
				}
			}
		}
		return res;
	}
}
