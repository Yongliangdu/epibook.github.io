package com.epi;

import java.util.ArrayList;

/**
 * @author translated from c++ by Blazheev Alexander
 */
// @include
public class TournamentTree {
  private static class TreeNode {
    public double cap; // leaf: remaining capacity in the box.
    // non-leaf: max remaining capacity in the subtree.
    public ArrayList<Integer> items = new ArrayList<Integer>(); // stores the
                                                                // items in the
                                                                // leaf node.

    public TreeNode(double cap) {
      this.cap = cap;
    }
  }

  // Stores the complete binary tree. For tree_[i],
  // left subtree is tree_[2i + 1], and right subtree is tree_[2i + 2].
  private ArrayList<TreeNode> tree;

  // Recursively inserts item in tournament tree.
  private void insertHelper(int idx, int item, double cap) {
    int left = (idx << 1) + 1, right = (idx << 1) + 2;
    if (left < tree.size()) { // tree_[idx] is an internal node.
      insertHelper(tree.get(left).cap >= cap ? left : right, item, cap);
      tree.get(idx).cap = Math.max(tree.get(left).cap, tree.get(right).cap);
    } else { // tree_[idx] is a leaf node.
      tree.get(idx).cap -= cap;
      tree.get(idx).items.add(item);
    }
  }

  // n items, and each box has unit_cap.
  public TournamentTree(int n, double unitCap) {
    // Complete binary tree with n leafs has 2n - 1 nodes.
    int count = (n << 1) - 1;
    tree = new ArrayList<TreeNode>(count);
    for (int i = 0; i < count; i++) {
      tree.add(new TreeNode(unitCap));
    }
  }

  public void insert(int item, double itemCap) {
    insertHelper(0, item, itemCap);
  }

  // @exclude
  private void printLeaf() {
    for (int i = 0; i < tree.size(); ++i) {
      System.out.println("i = " + i + ", cap = " + tree.get(i).cap);
      for (int item : tree.get(i).items) {
        System.out.print(item + " ");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    // following is the example in the book.
    TournamentTree t = new TournamentTree(6, 1.0);
    t.insert(0, 0.60);
    t.insert(1, 0.60);
    t.insert(2, 0.55);
    t.insert(3, 0.80);
    t.insert(4, 0.50);
    t.insert(5, 0.45);
    // Due to the precision error of floating point number, Item 5 will be
    // inserted into 5-th box. However, if we are not using floating point
    // number, everything is fine.
    t.printLeaf();
  }
  // @include
}
// @exclude
