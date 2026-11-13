/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
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

import java.util.Arrays;
import org.apache.lucene.tests.util.LuceneTestCase;

/** The int[] copy operation must preserve bounds and stop when the destination fills. */
public class TestFixedBitSetIntoArrayCapacity extends LuceneTestCase {

  public void testStopsAtFirstFullWord() {
    FixedBitSet bits = new FixedBitSet(1 << 20);
    bits.set(0);
    bits.set(63);
    bits.set((1 << 20) - 1);
    int[] output = new int[1];
    assertEquals(1, bits.intoArray(0, bits.length(), 100, output));
    assertArrayEquals(new int[] {100}, output);
  }

  public void testUnalignedInitialWordSatisfiesArrayCapacity() {
    FixedBitSet bits = new FixedBitSet(256);
    bits.set(7);
    bits.set(29);
    bits.set(192);
    int[] first = new int[2];
    assertEquals(2, bits.intoArray(5, 255, -5, first));
    assertArrayEquals(new int[] {2, 24}, first);
    int[] second = new int[1];
    assertEquals(1, bits.intoArray(29, 255, 0, second));
    assertArrayEquals(new int[] {29}, second);
  }

  public void testAlignedEmptyRangeNullDestinationRetainsHistoricalBehavior() {
    FixedBitSet bits = new FixedBitSet(256);
    assertEquals(0, bits.intoArray(0, 0, 17, null));
    assertEquals(0, bits.intoArray(64, 64, 17, null));
    expectThrows(NullPointerException.class, () -> bits.intoArray(1, 1, 17, null));
    expectThrows(IndexOutOfBoundsException.class, () -> bits.intoArray(-1, 1, 17, null));
    expectThrows(IndexOutOfBoundsException.class, () -> bits.intoArray(0, 257, 17, null));
  }

  public void testDenseAndSparseWordsKeepDocumentIdOrder() {
    FixedBitSet bits = new FixedBitSet(512);
    for (int i = 0; i < 64; i++) {
      bits.set(i);
    }
    bits.set(77);
    bits.set(256);
    int[] full = new int[67];
    Arrays.fill(full, -1);
    assertEquals(66, bits.intoArray(0, bits.length(), 10, full));
    for (int i = 0; i < 64; i++) {
      assertEquals(10 + i, full[i]);
    }
    assertEquals(87, full[64]);
    assertEquals(266, full[65]);

    int[] shortBuffer = new int[9];
    assertEquals(9, bits.intoArray(0, bits.length(), 10, shortBuffer));
    for (int i = 0; i < shortBuffer.length; i++) {
      assertEquals(i + 10, shortBuffer[i]);
    }
  }
}
