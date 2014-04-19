package com.epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class Arbitrage {
  // @include
  public static boolean isArbitrageExist(List<? extends List<Double>> G) {
    // Transform each edge in G.
    for (List<Double> edgeList : G) {
      for (int i = 0; i < edgeList.size(); i++) {
        edgeList.set(i, -Math.log10(edgeList.get(i)));
      }
    }

    // Use Bellman-Ford to find negative weight cycle.
    return bellmanFord(G, 0);
  }

  private static boolean bellmanFord(List<? extends List<Double>> G, int source) {
    double[] disToSource = new double[G.size()];
    Arrays.fill(disToSource, Double.MAX_VALUE);
    disToSource[source] = 0;

    for (int times = 1; times < G.size(); ++times) {
      boolean haveUpdate = false;
      for (int i = 0; i < G.size(); ++i) {
        for (int j = 0; j < G.get(i).size(); ++j) {
          if (disToSource[i] != Double.MAX_VALUE
              && disToSource[j] > disToSource[i] + G.get(i).get(j)) {
            haveUpdate = true;
            disToSource[j] = disToSource[i] + G.get(i).get(j);
          }
        }
      }

      // No update in this iteration means no negative cycle.
      if (!haveUpdate) {
        return false;
      }
    }

    // Detect cycle if there is any further update.
    for (int i = 0; i < G.size(); ++i) {
      for (int j = 0; j < G.get(i).size(); ++j) {
        if (disToSource[i] != Double.MAX_VALUE
            && disToSource[j] > disToSource[i] + G.get(i).get(j)) {
          return true;
        }
      }
    }
    return false;
  }

  // @exclude

  public static void main(String[] args) {
    Random r = new Random();
    int n, m;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
      m = r.nextInt(n * (n - 1) / 2) + 1;
    } else if (args.length == 2) {
      n = Integer.parseInt(args[0]);
      m = Integer.parseInt(args[1]);
    } else {
      n = r.nextInt(100) + 1;
      m = r.nextInt(n * (n - 1) / 2) + 1;
    }
    ArrayList<ArrayList<Double>> G = new ArrayList<ArrayList<Double>>();
    for (int i = 0; i < n; i++) {
      ArrayList<Double> newList = new ArrayList<Double>();
      for (int j = 0; j < n; j++) {
        newList.add(0.0);
      }
      G.add(newList);
    }
    // Assume the input is a complete graph.
    for (int i = 0; i < G.size(); ++i) {
      G.get(i).set(i, 1.0);
      for (int j = i + 1; j < G.size(); ++j) {
        G.get(i).set(j, r.nextDouble());
        G.get(j).set(i, 1.0 / G.get(i).get(j));
      }
    }
    boolean res = isArbitrageExist(G);
    System.out.println(res);

    ArrayList<ArrayList<Double>> g = new ArrayList<ArrayList<Double>>();
    for (int i = 0; i < 3; i++) {
      ArrayList<Double> newList = new ArrayList<Double>();
      for (int j = 0; j < 3; j++) {
        newList.add(0.0);
      }
      g.add(newList);
    }
    g.get(0).set(1, 2.0);
    g.get(1).set(0, 0.5);
    g.get(0).set(2, 1.0);
    g.get(2).set(0, 1.0);
    g.get(1).set(2, 4.0);
    g.get(2).set(1, 0.25);
    res = isArbitrageExist(g);
    assert res;
    System.out.println(res);
  }
}
