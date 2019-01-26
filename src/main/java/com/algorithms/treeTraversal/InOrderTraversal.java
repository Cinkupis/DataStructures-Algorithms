package com.algorithms.treeTraversal;

import com.datastructures.trees.nodes.TreeNode;

@SuppressWarnings("unused")
public class InOrderTraversal extends abstractTraversal {
    public void traverse(TreeNode node) {
        if (node != null) {
            traverse(node.left);
            visit(node);
            System.out.print(" => ");
            traverse(node.right);
        }
    }
}
