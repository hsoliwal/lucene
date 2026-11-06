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
package org.apache.lucene.util;

import java.util.Random;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.apache.lucene.tests.util.LuceneTestCase;

/**
 * Verifies that existing Lucene CharSequence ingest works with immutable segmented text such as M3
 * String views, without asking callers for a flattened intermediate String.
 *
 * <p>BytesRef still owns UTF-8 bytes; CharTermAttributeImpl remains a mutable char[] attribute.
 * These contracts are intentionally not changed by this test.
 */
public class TestM3CompositeCharSequenceBoundary extends LuceneTestCase {

  /** Test-only segmented UTF-16 input that refuses materialization methods. */
  private static CharSequence composite(String... input) {
    final String[] pieces = input.clone();
    int total = 0;
    for (String piece : pieces) {
      total = Math.addExact(total, piece.length());
    }
    final int length = total;
    return new CharSequence() {
      @Override
      public int length() {
        return length;
      }

      @Override
      public char charAt(int index) {
        if (index < 0 || index >= length) {
          throw new IndexOutOfBoundsException(index);
        }
        for (String piece : pieces) {
          if (index < piece.length()) {
            return piece.charAt(index);
          }
          index -= piece.length();
        }
        throw new AssertionError("unreachable index");
      }

      @Override
      public CharSequence subSequence(int start, int end) {
        throw new AssertionError("unexpected materialization of segmented input");
      }

      @Override
      public String toString() {
        throw new AssertionError("unexpected flattening of segmented input");
      }
    };
  }

  public void testBytesRefEncodesSplitSurrogatesAndUnicodeWithoutFlattening() {
    String[][] cases = {
      {"", ""},
      {"hello", " ", "world"},
      {"\u4e2d", "\u6587", "\u20ac"},
      {"x", "\ud83d", "\ude00", "y"},
      {"a", "\ud800", "b", "\udc00", "c"},
      {"Cafe", "\u0301", " ", "\u03a3\u03bf\u03c2"}
    };
    for (String[] parts : cases) {
      String flat = String.join("", parts);
      assertEquals(new BytesRef(flat), new BytesRef(composite(parts)));
    }
  }

  public void testMutableCharTermAttributeAcceptsImmutableCompositeInput() {
    String[][] cases = {
      {"", ""},
      {"prefix", "-", "suffix"},
      {"\ud83d", "\ude00", "hello"},
      {"\ud800", "a", "\udc00"},
      {"\u4e2d", " ", "\u6587"}
    };
    for (String[] parts : cases) {
      String flat = String.join("", parts);
      CharTermAttributeImpl attribute = new CharTermAttributeImpl();
      attribute.append("head:");
      attribute.append(composite(parts));
      assertEquals("head:" + flat, attribute.toString());

      attribute.setEmpty();
      if (flat.length() >= 2) {
        attribute.append(composite(parts), 1, flat.length() - 1);
        assertEquals(flat.substring(1, flat.length() - 1), attribute.toString());
      }
    }
  }

  public void testFixedSeedUtf16CodeUnitSplitsHaveIdenticalEncodedBytes() {
    final Random random = new Random(20261014L);
    final char[] units = {
      'a', 'Z', '\u00e9', '\u4e2d', '\u0301', ' ', '\ud83d', '\ude00', '\ud800', '\udc00', '\u2028',
      '\u200b', '\ufffd'
    };
    for (int sample = 0; sample < 512; sample++) {
      int length = random.nextInt(70);
      StringBuilder flat = new StringBuilder(length);
      for (int i = 0; i < length; i++) {
        flat.append(units[random.nextInt(units.length)]);
      }
      String expected = flat.toString();
      String[] leaves = new String[expected.length() + 2];
      leaves[0] = "";
      for (int i = 0; i < expected.length(); i++) {
        leaves[i + 1] = expected.substring(i, i + 1);
      }
      leaves[leaves.length - 1] = "";
      assertEquals(new BytesRef(expected), new BytesRef(composite(leaves)));
    }
  }
}
