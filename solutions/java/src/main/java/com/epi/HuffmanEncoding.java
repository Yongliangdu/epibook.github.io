package com.epi;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import com.epi.utils.Ref;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class HuffmanEncoding {
  private static final double[] ENGLISH_FREQ = { 8.167, 1.492, 2.782, 4.253,
      12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749,
      7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
      1.974, 0.074 };

  // @include
  public static class Symbol {
    public char c;
    public double prob;
    public String code;

    public String toString() {
      return c + " " + prob + " " + code;
    }
  }

  public static class BinaryTree implements Comparable<BinaryTree> {
    public double prob;
    public Ref<Symbol> s;
    public BinaryTree left, right;

    public BinaryTree(double prob, Ref<Symbol> s, BinaryTree left,
        BinaryTree right) {
      this.prob = prob;
      this.s = s;
      this.left = left;
      this.right = right;
    }

    @Override
    public int compareTo(BinaryTree o) {
      return Double.compare(o.prob, prob);
    }
  }

  public static void huffmanEncoding(List<Ref<Symbol>> symbols) {
    // Initially assign each symbol into min->heap.
    PriorityQueue<BinaryTree> minHeap = new PriorityQueue<BinaryTree>();
    for (Ref<Symbol> s : symbols) {
      minHeap.add(new BinaryTree(s.value.prob, s, null, null));
    }

    // Keep combining two nodes until there is one node left.
    while (minHeap.size() > 1) {
      BinaryTree l = minHeap.remove();
      BinaryTree r = minHeap.remove();
      minHeap.add(new BinaryTree(l.prob + r.prob, null, l, r));
    }

    // Traverse the binary tree and assign code.
    assignHuffmanCode(minHeap.peek(), "");
  }

  // Traverse tree and assign code.
  private static void assignHuffmanCode(BinaryTree r, String s) {
    if (r != null) {
      // This node (i.e.,leaf) contains symbol.
      if (r.s != null) {
        r.s.value.code = s;
      } else { // non-leaf node.
        assignHuffmanCode(r.left, s + "0");
        assignHuffmanCode(r.right, s + "1");
      }
    }
  }

  // @exclude

  public static void main(String[] args) {
    int n;
    Random r = new Random();
    if (args.length == 1) {
      if (!"huffman".equals(args[0])) {
        n = Integer.parseInt(args[0]);
      } else {
        n = 26;
      }
    } else {
      n = r.nextInt(255) + 1;
    }
    ArrayList<Ref<Symbol>> symbols = new ArrayList<Ref<Symbol>>();
    int sum = 0;
    if (args.length == 0 || (!"huffman".equals(args[0]))) {
      for (int i = 0; i < n; ++i) {
        Symbol t = new Symbol();
        t.c = (char) i;
        t.prob = r.nextInt(100001);
        sum += t.prob;
        symbols.add(new Ref<Symbol>(t));
      }
      for (int i = 0; i < n; ++i) {
        symbols.get(i).value.prob /= sum;
      }
    } else {
      for (int i = 0; i < n; ++i) {
        Symbol t = new Symbol();
        t.c = (char) ('a' + i);
        t.prob = ENGLISH_FREQ[i];
        symbols.add(new Ref<Symbol>(t));
      }
    }
    huffmanEncoding(symbols);
    double avg = 0.0;
    for (int i = 0; i < symbols.size(); ++i) {
      System.out.println(symbols.get(i).value.c);
      avg += symbols.get(i).value.prob / 100
          * symbols.get(i).value.code.length();
    }
    System.out.println("average huffman code length = " + avg);
  }
}
