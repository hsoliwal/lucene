// SPDX-FileCopyrightText: 2026 Hitesh Soliwal and contributors
// SPDX-License-Identifier: Apache-2.0
package org.apache.lucene.analysis;

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
      assertEquals(Integer.valueOf(i), map.get(new StringBuilder(value)));
      assertEquals(Integer.valueOf(i), map.get(value.toCharArray(), 0, value.length()));
      assertTrue(map.containsKey(value));
    }
  }
  public void testIgnoreCaseRetainsUnicodeCodePointFolding() {
    CharArrayMap<Integer> map = new CharArrayMap<>(4, true);
    map.put("AbC", 7);
    assertEquals(Integer.valueOf(7), map.get("ABC"));
    assertEquals(Integer.valueOf(7), map.get(new StringBuilder("abc")));
  }
}
