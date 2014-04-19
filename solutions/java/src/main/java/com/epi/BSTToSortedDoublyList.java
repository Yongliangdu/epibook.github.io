package com.epi;

import com.epi.BinaryTreePrototypeTemplate.BinaryTree;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class BSTToSortedDoublyList {
  // @include
  // Transform a BST into a circular sorted doubly linked list in-place,
  // return the head of the list.
  public static <T> BinaryTree<T> bstToDoublyLinkedList(BinaryTree<T> n) {
    // Empty subtree.
    if (n == null) {
      return null;
    }

    // Recursively build the list from left and right subtrees.
    BinaryTree<T> lHead = bstToDoublyLinkedList(n.getLeft());
    BinaryTree<T> rHead = bstToDoublyLinkedList(n.getRight());

    // Append n to the list from left subtree.
    BinaryTree<T> lTail = null;
    if (lHead != null) {
      lTail = lHead.getLeft();
      lTail.setRight(n);
      n.setLeft(lTail);
      lTail = n;
    } else {
      lHead = lTail = n;
    }

    // Append the list from right subtree to n.
    BinaryTree<T> rTail = null;
    if (rHead != null) {
      rTail = rHead.getLeft();
      lTail.setRight(rHead);
      rHead.setLeft(lTail);
    } else {
      rTail = lTail;
    }
    rTail.setRight(lHead);
    lHead.setLeft(rTail);

    return lHead;
  }

  // @exclude

  public static void main(String[] args) {
    // 3
    // 2 5
    // 1 4 6
    BinaryTree<Integer> root = new BinaryTree<Integer>(3);
    root.setLeft(new BinaryTree<Integer>(2));
    root.getLeft().setLeft(new BinaryTree<Integer>(1));
    root.setRight(new BinaryTree<Integer>(5));
    root.getRight().setLeft(new BinaryTree<Integer>(4));
    root.getRight().setRight(new BinaryTree<Integer>(6));
    BinaryTree<Integer> L = bstToDoublyLinkedList(root);
    BinaryTree<Integer> curr = L;
    int pre = Integer.MIN_VALUE;
    do {
      assert (pre <= curr.getData());
      System.out.println(curr.getData());
      pre = curr.getData();
      curr = curr.getRight();
    } while (curr != L);
  }
}
