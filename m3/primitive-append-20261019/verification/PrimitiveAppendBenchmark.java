// SPDX-FileCopyrightText: 2026 Hitesh Soliwal and Contributors to the Synexia Project
// SPDX-License-Identifier: Apache-2.0
package com.synexia.collectionwitness;

import org.apache.lucene.internal.hppc.FloatArrayList;
import org.apache.lucene.internal.hppc.IntArrayList;
import org.apache.lucene.internal.hppc.LongArrayList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/** Distinguishes preallocated bulk append from allocation of the result list. */
@State(Scope.Thread)
public class PrimitiveAppendBenchmark {
  @Param({"0", "1", "8", "64", "4096"}) public int size;
  private IntArrayList ints, intTarget;
  private LongArrayList longs, longTarget;
  private FloatArrayList floats, floatTarget;

  @Setup public void setup() {
    this.ints = new IntArrayList(this.size);
    this.longs = new LongArrayList(this.size);
    this.floats = new FloatArrayList(this.size);
    this.intTarget = new IntArrayList(this.size);
    this.longTarget = new LongArrayList(this.size);
    this.floatTarget = new FloatArrayList(this.size);
    for (int i = 0; i < this.size; i++) {
      this.ints.add(i * 31);
      this.longs.add((long) i * Integer.MAX_VALUE);
      this.floats.add(i * 1.25F);
    }
  }
  @Benchmark public Object appendInts() {
    this.intTarget.elementsCount = 0;
    this.intTarget.addAll(this.ints);
    return this.intTarget;
  }
  @Benchmark public Object appendLongs() {
    this.longTarget.elementsCount = 0;
    this.longTarget.addAll(this.longs);
    return this.longTarget;
  }
  @Benchmark public Object appendFloats() {
    this.floatTarget.elementsCount = 0;
    this.floatTarget.addAll(this.floats);
    return this.floatTarget;
  }
  @Benchmark public Object copyInts() { return new IntArrayList(this.ints); }
  @Benchmark public Object copyLongs() { return new LongArrayList(this.longs); }
  @Benchmark public Object copyFloats() { return new FloatArrayList(this.floats); }
}
