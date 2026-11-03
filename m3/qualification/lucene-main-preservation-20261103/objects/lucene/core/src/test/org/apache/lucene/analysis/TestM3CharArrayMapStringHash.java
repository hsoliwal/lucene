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

// SPDX-FileCopyrightText: 2026 Hitesh Soliwal and contributors
// SPDX-License-Identifier: Apache-2.0
package org.apache.lucene.analysis;

import org.apache.lucene.tests.util.LuceneTestCase;

/** Exact UTF-16 hash lookup equivalence; mutable CharSequence stays on original loop. */
public class TestM3CharArrayMapStringHash extends LuceneTestCase {
  public void testCaseSensitiveStringAndCharSequenceParity() {
    CharArrayMap<Integer> map = new CharArrayMap<>(8, false);
    String[] words = {
      "", "a", "Straße", "é", "\uD800", "\uDC00", "a\uD83D\uDE00b", "\uFFFF", "a\u0000b"
    };
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
