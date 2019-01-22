package com.dataStructures.trees;

import com.dataStructures.trees.nodes.TreeNode;
import com.dataStructures.trees.types.TreeTypes;

import java.util.*;

@SuppressWarnings("unused")
public class BinaryTree {

    private TreeNode root;
    private TreeTypes type;
    private int size;

    public BinaryTree(TreeTypes type) {
        this.type = type;
        this.root = null;
        this.size = 0;
    }

    public BinaryTree(int value, TreeTypes type) {
        this(value);
        this.type = type;
    }

    public BinaryTree(int value) {
        this.root = new TreeNode(value);
        this.type = TreeTypes.BINARY_TREE;
        this.size = 1;
    }

    public BinaryTree() {
        this.root = null;
        this.type = TreeTypes.BINARY_TREE;
        this.size = 0;
    }

    private TreeNode addRecursiveBST(TreeNode current, int value) {
        if (current == null) {
            size++;
            return new TreeNode(value);
        }

        if (value < current.value) {
            current.left = addRecursiveBST(current.left, value);
            current.left.parent = current;
        } else if (value > current.value) {
            current.right = addRecursiveBST(current.right, value);
            current.right.parent = current;
        } else {
            return current;
        }

        return current;
    }

    private void addInOrderDT(int value) {
        if (root == null) {
            size++;
            root = new TreeNode(value);
        }

        TreeNode lastNode = findNodeOrLast(null);

        if (lastNode != null) {
            TreeNode node = new TreeNode(value);
            if (lastNode.parent != null && lastNode.equals(lastNode.parent.left) && lastNode.parent.right == null) {
                lastNode.parent.right = node;
                node.parent = lastNode.parent;
            } else if (lastNode.parent != null && lastNode.equals(lastNode.parent.right)) {
                while (lastNode.parent != null && lastNode.equals(lastNode.parent.right)) {
                    lastNode = lastNode.parent;
                }

                if (lastNode.parent == null) {
                    while (lastNode.left != null) {
                        lastNode = lastNode.left;
                    }
                    lastNode.left = node;
                    node.parent = lastNode;
                } else {
                    lastNode = lastNode.parent.right;
                    while (lastNode.left != null) {
                        lastNode = lastNode.left;
                    }
                    lastNode.left = node;
                    node.parent = lastNode;
                }
            } else {
                lastNode.left = node;
                node.parent = lastNode;
            }
            size++;
        }
    }

    private boolean containsNodeRecursiveBST(TreeNode current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.value) {
            return true;
        }
        return value < current.value
                ? containsNodeRecursiveBST(current.left, value)
                : containsNodeRecursiveBST(current.right, value);
    }

    private boolean containsNodeInOrderDT(TreeNode current) {
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            TreeNode next = nodes.remove();
            if (next.equals(current)) {
                return true;
            }

            if (next.left != null) {
                nodes.add(next.left);
            }
            if (next.right != null) {
                nodes.add(next.right);
            }
        }
        return false;
    }

    private int findSmallestValueDT() {
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        int smallestValue = root.value;

        while (!nodes.isEmpty()) {
            TreeNode next = nodes.remove();
            if (next.value < smallestValue) {
                smallestValue = next.value;
            }

            if (next.left != null) {
                nodes.add(next.left);
            }
            if (next.right != null) {
                nodes.add(next.right);
            }
        }
        return smallestValue;
    }

    private int findSmallestValueBST(TreeNode current) {
        return current.left == null ? current.value : findSmallestValueBST(current.left);
    }

    private TreeNode deleteRecursiveBST(TreeNode current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            size--;
            if (current.hasNoChildren()) {
                return null;
            }
            if (current.hasOneChild()) {
                if (current.right == null) {
                    return current.left;
                }

                if (current.left == null) {
                    return current.right;
                }
            }
            if (current.hasTwoChildren()) {
                int smallestValue = findSmallestValueBST(current.right);
                current.value = smallestValue;
                current.right = deleteRecursiveBST(current.right, smallestValue);
                return current;
            }
        }
        if (value < current.value) {
            current.left = deleteRecursiveBST(current.left, value);
            return current;
        }
        current.right = deleteRecursiveBST(current.right, value);
        return current;
    }

    private TreeNode findNodeOrLast(Integer value) {
        if (root == null) {
            throw new NoSuchElementException();
        }

        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        TreeNode nodeToFind = null;

        while (!nodes.isEmpty()) {
            nodeToFind = nodes.remove();
            if (value != null && nodeToFind.value == value) {
                return nodeToFind;
            }

            if (nodeToFind.left != null) {
                nodes.add(nodeToFind.left);
            }
            if (nodeToFind.right != null) {
                nodes.add(nodeToFind.right);
            }
        }
        return nodeToFind;
    }

    private void deleteDT(int value) {
        if (root == null) {
            throw new NoSuchElementException();
        }

        TreeNode deletedNode = findNodeOrLast(value);
        TreeNode replacementNodeLast = findNodeOrLast(null);

        if (deletedNode == null || deletedNode.value != value) {
            throw new NoSuchElementException();
        }

        size--;
        if (deletedNode == root && replacementNodeLast == root) {
            root = null;
            return;
        }

        TreeNode left = deletedNode.left;
        TreeNode right = deletedNode.right;
        TreeNode parent = deletedNode.parent;

        deletedNode.value = replacementNodeLast.value;
        deletedNode.parent = parent;
        deletedNode.left = left;
        deletedNode.right = right;

        if (replacementNodeLast.parent.right == replacementNodeLast) {
            replacementNodeLast.parent.right = null;
        } else {
            replacementNodeLast.parent.left = null;
        }
    }

    private int checkHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = checkHeight(node.left);
        if (leftHeight == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        int rightHeight = checkHeight(node.right);
        if (rightHeight == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        int heightDifference = Math.abs(leftHeight - rightHeight);
        if (heightDifference > 1) {
            return Integer.MIN_VALUE;
        } else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    private int numOfPathsWithSumFromNode(TreeNode node, int targetSum, int currentSum) {
        if (node == null) {
            return 0;
        }

        currentSum += node.value;

        int totalPaths = 0;
        if (currentSum == targetSum) {
            totalPaths++;
        }

        totalPaths += numOfPathsWithSumFromNode(node.left, targetSum, currentSum);
        totalPaths += numOfPathsWithSumFromNode(node.right, targetSum, currentSum);

        return totalPaths;
    }

    private void incrementHashTable(HashMap<Integer, Integer> hashTable, int key, int delta) {
        int newCount = hashTable.getOrDefault(key, 0) + delta;
        if (newCount == 0) {
            hashTable.remove(key);
        } else {
            hashTable.put(key, newCount);
        }
    }

    private int countPathsWithSumON(TreeNode node, int targetSum, int runningSum, HashMap<Integer, Integer> pathCount) {
        if (node == null) {
            return 0;
        }

        runningSum += node.value;
        int sum = runningSum - targetSum;
        int totalPaths = pathCount.getOrDefault(sum, 0);

        if (runningSum == targetSum) {
            totalPaths++;
        }

        incrementHashTable(pathCount, runningSum, 1);
        totalPaths += countPathsWithSumON(node.left, targetSum, runningSum, pathCount);
        totalPaths += countPathsWithSumON(node.right, targetSum, runningSum, pathCount);
        incrementHashTable(pathCount, runningSum, -1);

        return totalPaths;
    }

    public void setType(TreeTypes type) {
        this.type = type;
    }

    public void add(int value) {
        switch (type) {
            case BINARY_TREE:
                addInOrderDT(value);
                break;
            case BINARY_SEARCH_TREE:
                root = addRecursiveBST(root, value);
                break;
            default:
                addInOrderDT(value);
                break;
        }
    }

    public boolean containsNode(int value) {
        switch (type) {
            case BINARY_TREE:
                return containsNodeInOrderDT(new TreeNode(value));
            case BINARY_SEARCH_TREE:
                return containsNodeRecursiveBST(root, value);
            default:
                return false;
        }
    }

    public int findSmallestValue() {
        switch (type) {
            case BINARY_TREE:
                return findSmallestValueDT();
            case BINARY_SEARCH_TREE:
                return findSmallestValueBST(root);
            default:
                throw new NoSuchElementException();
        }
    }

    public void delete(int value) {
        switch (type) {
            case BINARY_TREE:
                deleteDT(value);
                break;
            case BINARY_SEARCH_TREE:
                root = deleteRecursiveBST(root, value);
                break;
            default:
                deleteDT(value);
                break;
        }
    }

    public boolean isBalanced() {
        if (type == TreeTypes.BINARY_TREE) {
            return true;
        }
        return checkHeight(this.root) != Integer.MIN_VALUE;
    }

    public int numOfPathsWithSumONLogN(TreeNode node, int targetSum) {
        if (root == null) {
            return 0;
        }

        int pathsFromRoot = numOfPathsWithSumFromNode(node, targetSum, 0);
        int pathsOnLeft = numOfPathsWithSumONLogN(node.left, targetSum);
        int pathsOnRight = numOfPathsWithSumONLogN(node.right, targetSum);

        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }

    public int countPathsWithSumON(TreeNode node, int targetSum) {
        return countPathsWithSumON(node, targetSum, 0, new HashMap<Integer, Integer>());
    }

    public void recreateAllPossibleBSTInputs() {

    }

    public void arrayToMinimalTreeBST(int[] input) {

    }

    public boolean checkSubTree(TreeNode anotherRoot) {
        return false;
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree(10, TreeTypes.BINARY_TREE);
        binaryTree.add(4);
        binaryTree.add(12);
        binaryTree.add(2);
        binaryTree.add(6);
        binaryTree.add(11);
        //binaryTree.add(13);

        System.out.print("Start");
        System.out.println("\n" + binaryTree.isBalanced());
        System.out.println(binaryTree.containsNode(15));
        System.out.println(binaryTree.containsNode(12));
        System.out.println(binaryTree.findSmallestValue());
        System.out.print("Start");
    }
}
