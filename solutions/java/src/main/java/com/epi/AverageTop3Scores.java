package com.epi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

public class AverageTop3Scores {
  private static class UniqueInteger implements Comparable<UniqueInteger> {
    public Integer value;
    public Long sequence;

    public UniqueInteger(Integer value, Long sequence) {
      this.value = value;
      this.sequence = sequence;
    }

    @Override
    public int compareTo(UniqueInteger o) {
      int result = value.compareTo(o.value);
      if (result == 0) {
        result = sequence.compareTo(o.sequence);
      }
      return result;
    }
  }

  // @include
  public static String findStudentWithTop3AverageScores(InputStream ifs) {
    Map<String, TreeSet<UniqueInteger>> studentScores = new HashMap<String, TreeSet<UniqueInteger>>();
    try {
      long sequence = 0;
      ObjectInputStream ois = new ObjectInputStream(ifs);
      while (true) {
        String name = ois.readUTF();
        int score = ois.readInt();
        TreeSet<UniqueInteger> scores = studentScores.get(name);
        if (scores == null) {
          scores = new TreeSet<UniqueInteger>();
        }
        scores.add(new UniqueInteger(score, sequence++));
        studentScores.put(name, scores);
      }
    } catch (IOException e) {
    }

    String topStudent = "no such student";
    int currentTop3ScoresSum = 0;
    for (Map.Entry<String, TreeSet<UniqueInteger>> scores : studentScores
        .entrySet()) {
      if (scores.getValue().size() >= 3) {
        int currentScoresSum = getTop3ScoresSum(scores.getValue());
        if (currentScoresSum > currentTop3ScoresSum) {
          currentTop3ScoresSum = currentScoresSum;
          topStudent = scores.getKey();
        }
      }
    }
    return topStudent;
  }

  // Returns the sum of top3 scores.
  private static int getTop3ScoresSum(TreeSet<UniqueInteger> scores) {
    Iterator<UniqueInteger> it = scores.iterator();
    int result = 0;
    for (int i = 0; i < 3 && it.hasNext(); i++) {
      result += it.next().value;
    }
    return result;
  }

  // @exclude

  private static String randString(int len) {
    Random r = new Random();
    StringBuilder ret = new StringBuilder(len);
    while (len-- > 0) {
      ret.append((char) (r.nextInt(26) + 'A'));
    }
    return ret.toString();
  }

  public static void main(String[] args) {
    Random r = new Random();
    int n;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    } else {
      n = r.nextInt(10000) + 1;
    }
    try {
      OutputStream ofs = new FileOutputStream("scores.txt");
      ObjectOutputStream oos = new ObjectOutputStream(ofs);
      for (int i = 0; i < n; ++i) {
        int testNum = r.nextInt(21);
        String name = randString(r.nextInt(6) + 5);
        while (testNum-- > 0) {
          oos.writeUTF(name);
          oos.writeInt(r.nextInt(101));
        }
      }
      ofs.close();
    } catch (Exception e) {
      System.out.println("Error creating scores.txt: " + e.getMessage());
    }
    try {
      InputStream ifs = new FileInputStream("scores.txt");
      String name = findStudentWithTop3AverageScores(ifs);
      System.out.println("top student is " + name);
      ifs.close();
    } catch (Exception e) {
      System.out.println("Error reading scores.txt: " + e.getMessage());
    }
  }
}
