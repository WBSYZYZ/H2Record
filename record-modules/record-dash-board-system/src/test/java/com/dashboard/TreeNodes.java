package com.dashboard;

public class TreeNodes {
    int val;
    TreeNodes left;
    TreeNodes right;
    TreeNodes() {}
    TreeNodes(int val) { this.val = val; }
    TreeNodes(int val, TreeNodes left, TreeNodes right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
