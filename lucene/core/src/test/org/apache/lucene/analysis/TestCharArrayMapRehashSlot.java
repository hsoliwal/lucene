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
package org.apache.lucene.analysis;

import org.apache.lucene.tests.util.LuceneTestCase;

/** Regression for occupancy-only rebuilding of CharArrayMap's original char[] table. */
public class TestCharArrayMapRehashSlot extends LuceneTestCase {
  private static String collisionKey(int number, int pairs) {
    StringBuilder result = new StringBuilder(pairs * 2);
    for (int i = 0; i < pairs; i++) {
      result.append((number & (1 << i)) == 0 ? "Aa" : "BB");
    }
    return result.toString();
  }

  public void testDistinctEqualHashKeysSurviveRepeatedRehash() {
    CharArrayMap<Integer> actual = new CharArrayMap<>(1, false);
    int expectedHash = collisionKey(0, 9).hashCode();
    for (int i = 0; i < 512; i++) {
      String key = collisionKey(i, 9);
      assertEquals(expectedHash, key.hashCode());
      assertNull(actual.put(key, i));
      if ((i & 63) == 63) {
        assertEquals(i + 1, actual.size());
        for (int previous = 0; previous <= i; previous++) {
          String priorKey = collisionKey(previous, 9);
          assertEquals(Integer.valueOf(previous), actual.get(priorKey));
        }
      }
    }
    for (int i = 0; i < 512; i++) {
      String key = collisionKey(i, 9);
      assertEquals(Integer.valueOf(i), actual.get(key.toCharArray(), 0, key.length()));
      assertEquals(Integer.valueOf(i), actual.put(key, -i));
      assertEquals(Integer.valueOf(-i), actual.get(key));
    }
    assertEquals(512, actual.size());
  }

  public void testUnicodeAndCaseInsensitiveRehashWithUniqueKeys() {
    for (boolean ignoreCase : new boolean[] {false, true}) {
      CharArrayMap<Integer> actual = new CharArrayMap<>(1, ignoreCase);
      for (int i = 0; i < 4096; i++) {
        String key = (i % 2 == 0 ? "K" : "k") + i + "\uD801\uDC00\uD83D\uDE03";
        assertNull(actual.put(key, i));
      }
      assertEquals(4096, actual.size());
      for (int i = 0; i < 4096; i++) {
        String key = (i % 2 == 0 ? "K" : "k") + i + "\uD801\uDC00\uD83D\uDE03";
        assertEquals(Integer.valueOf(i), actual.get(key));
        assertEquals(Integer.valueOf(i), actual.get(key.toCharArray(), 0, key.length()));
        if (ignoreCase) {
          String alternate = (i % 2 == 0 ? "k" : "K") + i + "\uD801\uDC28\uD83D\uDE03";
          assertEquals(Integer.valueOf(i), actual.get(alternate));
        }
      }
    }
  }
}
