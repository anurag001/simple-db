package src;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BPlusTreeNode {
    boolean isLeaf;
    List<Integer> keys;
    List<BPlusTreeNode> children;
    BPlusTreeNode nextLeaf; // Used for linked list structure in leaves

    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.nextLeaf = null;
    }
}