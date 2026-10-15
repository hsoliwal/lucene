// SPDX-FileCopyrightText: 2026 Hitesh Soliwal and contributors
// SPDX-License-Identifier: Apache-2.0
package org.apache.lucene.analysis;

import java.util.Random;
import org.apache.lucene.tests.util.LuceneTestCase;

/** Exact UTF-16 hash lookup equivalence; mutable CharSequence stays on original loop. */
public class TestM3CharArrayMapStringHash extends LuceneTestCase {
  public void testCaseSensitiveStringAndCharSequenceParity() {
    CharArrayMap<Integer> map = new CharArrayMap<>(8, false);
    String[] words = {"", "a", "Straße", "é", "\uD800", "\uDC00",
        "a\uD83D\uDE00b", "\uFFFF", "a\u0000b"};
    for (int i = 0; i < words.length; i++) map.put(words[i], i);
    for (int i = 0; i < words.length; i++) {
      String value = words[i];
      assertEquals(Integer.valueOf(i), map.get(value));
      StringBuilder mutable = new StringBuilder(value.length() + 1);
      mutable.append(value).append('!');
      mutable.setLength(value.length());
      assertEquals(Integer.valueOf(i), map.get((CharSequence) mutable));
      assertEquals(Integer.valueOf(i), map.get(value.toCharArray(), 0, value.length()));
      assertTrue(map.containsKey(value));
    }
  }
  public void testIgnoreCaseRetainsUnicodeCodePointFolding() {
    CharArrayMap<Integer> map = new CharArrayMap<>(4, true);
    map.put("AbC", 7);
    assertEquals(Integer.valueOf(7), map.get("ABC"));
    StringBuilder abc = new StringBuilder();
    abc.append('a');
    abc.append('b');
    abc.append('c');
    assertEquals(Integer.valueOf(7), map.get((CharSequence) abc));
  }
  /**
   * The String hash fast path must not force a generic CharSequence into a String.
   * M3-style immutable segmented views retain exact UTF-16 code units, including
   * surrogate pairs split across canonical leaves.
   */
  public void testCompositeCharSequenceWithoutFlattening() {
    String[][] cases = {
      {"", ""},
      {"ab", "c"},
      {"a", "\uD83D", "\uDE00", "b"},
      {"\uD800", "word", "\uDC00"},
      {"\u00E9", "a\u0301", "\u4E2D"},
      {"A", "\\u03A3", "B"},
      {"\u0000", "x", "\u2028"}
    };
    for (boolean ignoreCase : new boolean[] {false, true}) {
      for (String[] pieces : cases) {
        String flat = String.join("", pieces);
        CharArrayMap<Integer> map = new CharArrayMap<>(4, ignoreCase);
        map.put(flat, 101);
        CharSequence composite = composite(pieces);
        assertEquals(map.get(flat), map.get(composite));
        assertEquals(map.containsKey(flat), map.containsKey(composite));
      }
    }
  }

  public void testRandomizedImmutableCompositeLookup() {
    Random generator = new Random(20261015L);
    char[] alphabet = {'a', 'A', 'z', '\u00E9', '\u0301', '\u4E2D',
        '\uD83D', '\uDE00', '\uD800', '\uDC00', '\u0000'};
    for (boolean ignoreCase : new boolean[] {false, true}) {
      CharArrayMap<Integer> map = new CharArrayMap<>(8, ignoreCase);
      for (int trial = 0; trial < 1024; trial++) {
        int length = generator.nextInt(50);
        StringBuilder flat = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
          flat.append(alphabet[generator.nextInt(alphabet.length)]);
        }
        String text = flat.toString();
        String[] leaves = new String[length + 2];
        leaves[0] = "";
        for (int i = 0; i < length; i++) {
          leaves[i + 1] = text.substring(i, i + 1);
        }
        leaves[leaves.length - 1] = "";
        CharSequence segmented = composite(leaves);
        map.clear();
        map.put(text, trial);
        assertEquals(map.get(text), map.get(segmented));
        assertEquals(map.containsKey(text), map.containsKey(segmented));
        assertEquals(map.get(text.toCharArray(), 0, text.length()), map.get(segmented));
      }
    }
  }

  private static CharSequence composite(String... segments) {
    String[] leaves = segments.clone();
    int sum = 0;
    for (String value : leaves) sum = Math.addExact(sum, value.length());
    final int total = sum;
    return new CharSequence() {
      @Override
      public int length() {
        return total;
      }
      @Override
      public char charAt(int index) {
        if (index < 0 || index >= total) throw new IndexOutOfBoundsException(index);
        for (String leaf : leaves) {
          if (index < leaf.length()) return leaf.charAt(index);
          index -= leaf.length();
        }
        throw new AssertionError("unreachable");
      }
      @Override
      public CharSequence subSequence(int start, int end) {
        throw new AssertionError("composite source must not be sliced/materialized");
      }
      @Override
      public String toString() {
        throw new AssertionError("composite source must not be flattened");
      }
    };
  }

}
