/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Additional M3 composite-string compatibility tests.
 * Existing ASF sources and notices are left unchanged.
 */
package org.apache.lucene.util;

import org.apache.lucene.analysis.CharArrayMap;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.tests.util.LuceneTestCase;

/**
 * A contract proof that Lucene's existing CharSequence entry points already accept logical segments
 * without flattening them to an intermediate String.
 */
public class TestM3CompositeCharSequenceInterop extends LuceneTestCase {
  private static final class Segments implements CharSequence {
    private final String left;
    private final String right;

    Segments(String left, String right) {
      this.left = left;
      this.right = right;
    }

    @Override
    public int length() {
      return left.length() + right.length();
    }

    @Override
    public char charAt(int index) {
      if (index < 0 || index >= length()) {
        throw new StringIndexOutOfBoundsException(index);
      }
      return index < left.length() ? left.charAt(index) : right.charAt(index - left.length());
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      if (start < 0 || end < start || end > length()) {
        throw new StringIndexOutOfBoundsException();
      }
      StringBuilder result = new StringBuilder(end - start);
      for (int i = start; i < end; i++) result.append(charAt(i));
      return result;
    }

    @Override
    public String toString() {
      throw new AssertionError("No eager String flattening at the tested Lucene boundary");
    }
  }

  public void testBytesRefUtf8FromCompositeWithoutToString() {
    String[] corpus = {
      "",
      "a",
      "ab",
      "cafe",
      "\u00E9",
      "a\u0301",
      "\u20AC",
      "\uD83D\uDE00",
      "a\uD801\uDC1Cb",
      "\uD801\uDC1C\uD801\uDC1C"
    };
    for (String spelling : corpus) {
      for (int split = 0; split <= spelling.length(); split++) {
        CharSequence input = new Segments(spelling.substring(0, split), spelling.substring(split));
        BytesRef actual = new BytesRef(input);
        BytesRef expected = new BytesRef(spelling);
        assertTrue("UTF-8 mismatch at split " + split, actual.bytesEquals(expected));
        assertEquals(spelling, actual.utf8ToString());
      }
    }
  }

  public void testCharArraySetAndMapCompositeLookupWithoutToString() {
    CharArraySet exact = new CharArraySet(4, false);
    exact.add("alpha");
    exact.add("a\uD801\uDC1Cz");
    assertTrue(exact.contains(new Segments("al", "pha")));
    assertTrue(exact.contains(new Segments("a\uD801", "\uDC1Cz")));
    assertFalse(exact.contains(new Segments("al", "phb")));

    CharArraySet folded = new CharArraySet(4, true);
    folded.add("ABC\uD801\uDC1C");
    assertTrue(folded.contains(new Segments("abc\uD801", "\uDC44")));
    assertFalse(folded.contains(new Segments("abc\uD801", "\uDC45")));

    CharArrayMap<Integer> dictionary = new CharArrayMap<>(4, false);
    dictionary.put("word\uD83D\uDE00", 7);
    assertEquals(Integer.valueOf(7), dictionary.get(new Segments("word\uD83D", "\uDE00")));
    assertNull(dictionary.get(new Segments("word", "x")));
  }

  public void testExplicitMaterializationIsStillASeparateBoundary() {
    CharArrayMap<Integer> dictionary = new CharArrayMap<>(2, false);
    // CharArrayMap.put(CharSequence) calls toString() by its existing contract;
    // do not rewrite that producer path without separate compatibility proof.
    expectThrows(AssertionError.class, () -> dictionary.put(new Segments("foo", "bar"), 1));
  }
}
