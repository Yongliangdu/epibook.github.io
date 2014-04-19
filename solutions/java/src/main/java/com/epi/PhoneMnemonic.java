// Copyright (c) 2013 Elements of Programming Interviews. All rights reserved.
package com.epi;

import java.util.Random;

public class PhoneMnemonic {
  // @include
  static final String[] M = new String[] { "0", "1", "ABC", "DEF", "GHI",
      "JKL", "MNO", "PQRS", "TUV", "WXYZ" };

  static void phoneMnemonicHelper(String num, int d, char[] ans) {
    if (d == num.length()) { // get enough characters and output answer.
      System.out.println(ans);
    } else {
      for (char c : M[num.charAt(d) - '0'].toCharArray()) { // try all
                                                            // combinations.
        ans[d] = c;
        phoneMnemonicHelper(num, d + 1, ans);
      }
    }
  }

  static void phoneMnemonic(String num) {
    char[] ans = new char[num.length()];
    phoneMnemonicHelper(num, 0, ans);
  }

  // @exclude

  static String randString(int len) {
    Random gen = new Random();
    StringBuilder ret = new StringBuilder();
    while (len-- > 0) {
      ret.append(gen.nextInt(10));
    }
    return ret.toString();
  }

  public static void main(String[] args) {
    String num;
    if (args.length == 1) {
      num = args[0];
    } else {
      num = randString(10);
    }
    phoneMnemonic(num);
    System.out.println("number = " + num);
  }

}
