package com.epi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class TransitiveClosure {
  // @include
  public static class GraphVertex {
    public int visitTime = -1;
    public ArrayList<GraphVertex> edges = new ArrayList<GraphVertex>();
    public ArrayList<GraphVertex> extendedContacts = new ArrayList<GraphVertex>();
  }

  public static void transitiveClosure(List<GraphVertex> G) {
    // Build extended contacts for each vertex.
    for (int i = 0; i < G.size(); ++i) {
      G.get(i).visitTime = i;
      DFS(G.get(i), i, G.get(i).extendedContacts);
    }
  }

  private static void DFS(GraphVertex cur, int time, List<GraphVertex> contacts) {
    for (GraphVertex next : cur.edges) {
      if (next.visitTime != time) {
        next.visitTime = time;
        contacts.add(next);
        DFS(next, time, contacts);
      }
    }
  }

  // @exclude

  public static void main(String[] args) {
    Random r = new Random();
    ArrayList<GraphVertex> G = new ArrayList<GraphVertex>();
    int n;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    } else {
      n = r.nextInt(1000);
    }
    for (int i = 0; i < n; i++) {
      G.add(new GraphVertex());
    }
    System.out.println(G.size());
    int m = r.nextInt(n * (n - 1) >> 1) + 1;
    boolean[][] isEdgeExist = new boolean[n][n];

    /*
     * // Make the graph become connected for(int i = 1; i < n; ++i) { G.get(i -
     * 1).edges.add(G.get(i)); G.get(i).edges.add(G.get(i - 1)); isEdgeExist[i -
     * 1][i] = isEdgeExist[i][i - 1] = true; }
     */

    // Generate edges randomly
    while (m-- > 0) {
      int a, b;
      do {
        a = r.nextInt(n);
        b = r.nextInt(n);
      } while (a == b || isEdgeExist[a][b] == true);
      isEdgeExist[a][b] = isEdgeExist[b][a] = true;
      G.get(a).edges.add(G.get(b));
      G.get(b).edges.add(G.get(a));
    }

    transitiveClosure(G);

    /*
     * for (int i = 0; i < G.size(); ++i) { System.out.print(i + "\n\t");
     * System.out.println(G.get(i).extended_contacts); } for (int i = 0; i <
     * G.size(); ++i) { System.out.println(i);
     * System.out.println(G.get(i).edges); }
     */
  }
}
