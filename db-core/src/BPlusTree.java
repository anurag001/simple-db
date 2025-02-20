package src;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BPlusTree {
    private int t; // Minimum degree of the tree
    private BPlusTreeNode root;

    public BPlusTree(int order) {
        this.t = order;
        this.root = new BPlusTreeNode(true); // Root starts as a leaf node
    }

    public void insert(int key) {
        BPlusTreeNode splitNode = new BPlusTreeNode(false);
        int[] newKey = new int[1];

        if (insertRecursive(root, key, splitNode, newKey)) {
            // Root split, create a new root
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.keys.add(newKey[0]);
            newRoot.children.add(root);
            newRoot.children.add(splitNode);
            root = newRoot;
        }
    }

    private boolean insertRecursive(BPlusTreeNode node, int key, BPlusTreeNode splitNode, int[] newKey) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) i++;

        if (node.isLeaf) {
            node.keys.add(i, key);
        } else {
            BPlusTreeNode child = node.children.get(i);
            BPlusTreeNode newChildNode = new BPlusTreeNode(false);
            int[] childNewKey = new int[1];

            if (insertRecursive(child, key, newChildNode, childNewKey)) {
                node.keys.add(i, childNewKey[0]);
                node.children.add(i + 1, newChildNode);
            }
        }

        if (node.keys.size() >= 2 * t) {
            BPlusTreeNode newNode = split(node);
            newKey[0] = node.keys.remove(node.keys.size() - 1);
            splitNode.keys = newNode.keys;
            splitNode.children = newNode.children;
            splitNode.isLeaf = newNode.isLeaf;
            splitNode.nextLeaf = newNode.nextLeaf;
            return true;
        }
        return false;
    }

    private BPlusTreeNode split(BPlusTreeNode node) {
        int mid = node.keys.size() / 2;
        BPlusTreeNode newNode = new BPlusTreeNode(node.isLeaf);
        newNode.keys.addAll(node.keys.subList(mid, node.keys.size()));
        node.keys.subList(mid, node.keys.size()).clear();

        if (!node.isLeaf) {
            newNode.children.addAll(node.children.subList(mid + 1, node.children.size()));
            node.children.subList(mid + 1, node.children.size()).clear();
        } else {
            newNode.nextLeaf = node.nextLeaf;
            node.nextLeaf = newNode;
        }
        return newNode;
    }

    // **Binary Search for Efficient Searching in Leaf Nodes**
    private boolean binarySearch(List<Integer> arr, int key) {
        int left = 0, right = arr.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid) == key) return true;
            if (arr.get(mid) < key) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(BPlusTreeNode node, int key) {
        int i = 0;
        while (i < node.keys.size() && key >= node.keys.get(i)) {
            i++;
        }

        if (node.isLeaf) {
            return binarySearch(node.keys, key); // Use Binary Search
        }
        if(i >= node.children.size()){
            i = node.children.size() - 1;
        }
        return search(node.children.get(i), key);
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        printRecursive(root, 0);
    }

    private void printRecursive(BPlusTreeNode node, int level) {
        System.out.println("Level " + level + ": " + node.keys);
        if (!node.isLeaf) {
            for (BPlusTreeNode child : node.children) {
                printRecursive(child, level + 1);
            }
        }
    }
}