package com.epi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LongestSubarrayWithDistinctEntries {
  // @include
  public static int longestSubarrayWithDistinctEntries(List<Integer> A) {
    // Records the last occurrences of each entry.
    Map<Integer, Integer> lastOccurrence = new HashMap<Integer, Integer>();
    int startingIdx = 0, ans = 0;
    for (int i = 0; i < A.size(); ++i) {
      Integer res = lastOccurrence.put(A.get(i), i);
      if (res != null) { // A[i] appeared before. Check its validity.
        if (res >= startingIdx) {
          ans = Math.max(ans, i - startingIdx);
          startingIdx = res + 1;
        }
      }
    }
    ans = Math.max(ans, A.size() - startingIdx);
    return ans;
  }

  // @exclude

  // O(n^2) checking solution.
  private static int checkAns(List<Integer> A) {
    int len = 0;
    for (int i = 0; i < A.size(); ++i) {
      Set<Integer> table = new HashSet<Integer>();
      int j;
      for (j = i; A.size() - i > len && j < A.size(); ++j) {
        if (!table.add(A.get(j))) {
          break;
        }
      }
      len = Math.max(len, j - i);
    }
    return len;
  }

  public static void main(String[] args) {
    Random r = new Random();
    int n;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    } else {
      n = r.nextInt(1001);
    }
    System.out.println("n = " + n);
    for (int times = 0; times < 1000; ++times) {
      List<Integer> A = new ArrayList<Integer>();
      for (int i = 0; i < n; i++) {
        A.add(r.nextInt(1001));
      }
      // System.out.println(A);
      int ans = longestSubarrayWithDistinctEntries(A);
      int goldenAns = checkAns(A);
      System.out.println(ans + " " + goldenAns);
      assert (ans == goldenAns);
    }
  }
}
