// SPDX-FileCopyrightText: 2026 Hitesh Soliwal and Contributors to the Synexia Project
// SPDX-License-Identifier: Apache-2.0
import java.util.*;
import org.apache.lucene.internal.hppc.*;
public final class PrimitiveAppendContract {
  private static long checks, transcript = 1;
  private static void check(boolean condition) { checks++; if (!condition) throw new AssertionError(checks); }
  private static void record(long value) { transcript = 31 * transcript + value; }
  public static void main(String[] args) {
    testFloat();
    testInt();
    testLong();
    System.out.println("checks=" + checks + " transcript=" + transcript);
  }
  private static void testInt() {
    var random = new Random(146707L);
    for (int run = 0; run < 2500; run++) {
      int left = random.nextInt(65), right = random.nextInt(65);
      var dest = new IntArrayList(run % 3 == 0 ? left : 256);
      var source = new IntArrayList(right);
      for (int i = 0; i < left; i++) dest.add(random.nextInt());
      for (int i = 0; i < right; i++) source.add(random.nextInt());
      if (run % 5 == 0) { source = dest; right = left; }
      int[] before = dest.toArray(), extra = source.toArray();
      var old = dest.iterator();
      check(dest.addAll(source) == right); check(dest.size() == left + right);
      for (int j = 0; j < left + right; j++) { int a = dest.get(j), b = j < left ? before[j] : extra[j - left]; check(a == b); }
      int oldCount = 0; while (old.hasNext()) { old.next(); oldCount++; } check(oldCount == left);
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer)); record(dest.buffer.length);
    }
    for (int left : new int[]{0,1,3,8}) for (int right : new int[]{-1,0,1,3,8,9}) {
      var dest = new IntArrayList(32); var source = new IntArrayList(8);
      for (int i = 0; i < 8; i++) source.buffer[i] = (i + 10);
      dest.buffer = source.buffer; dest.elementsCount = left; source.elementsCount = right;
      try { record(dest.addAll(source)); } catch (RuntimeException expected) { record(expected.getClass().getName().hashCode()); }
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer)); record(dest.buffer.length);
    }
    for (int left : new int[]{-1,0,4}) for (int right : new int[]{-1,0,1,4,5}) {
      var dest = new IntArrayList(32); var source = new IntArrayList(4);
      dest.elementsCount = left; source.elementsCount = right;
      try { record(dest.addAll(source)); } catch (RuntimeException expected) { record(expected.getClass().getName().hashCode()); }
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer));
    }
    var hook = new HookInt(); var source = new IntArrayList(); source.add(1); source.add(2);
    hook.addAll(source); record(hook.calls); check(hook.calls == 11212);
    var other = new IntArrayList(); other.addAll(hook); record(hook.calls); check(hook.calls == 112123);
    var empty = new IntArrayList(); empty.buffer = null; check(other.addAll(empty) == 0);
  }
  private static final class HookInt extends IntArrayList {
    long calls;
    @Override protected void ensureBufferSpace(int n) { calls = calls * 10 + 1; super.ensureBufferSpace(n); }
    @Override public void add(int value) { super.add(value); calls = calls * 10 + 2; }
    @Override public Iterator<IntCursor> iterator() { calls = calls * 10 + 3; return super.iterator(); }
  }
  private static void testLong() {
    var random = new Random(146707L);
    for (int run = 0; run < 2500; run++) {
      int left = random.nextInt(65), right = random.nextInt(65);
      var dest = new LongArrayList(run % 3 == 0 ? left : 256);
      var source = new LongArrayList(right);
      for (int i = 0; i < left; i++) dest.add(random.nextLong());
      for (int i = 0; i < right; i++) source.add(random.nextLong());
      if (run % 5 == 0) { source = dest; right = left; }
      long[] before = dest.toArray(), extra = source.toArray();
      var old = dest.iterator();
      check(dest.addAll(source) == right); check(dest.size() == left + right);
      for (int j = 0; j < left + right; j++) { long a = dest.get(j), b = j < left ? before[j] : extra[j - left]; check(a == b); }
      int oldCount = 0; while (old.hasNext()) { old.next(); oldCount++; } check(oldCount == left);
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer)); record(dest.buffer.length);
    }
    for (int left : new int[]{0,1,3,8}) for (int right : new int[]{-1,0,1,3,8,9}) {
      var dest = new LongArrayList(32); var source = new LongArrayList(8);
      for (int i = 0; i < 8; i++) source.buffer[i] = (long) (i + 10);
      dest.buffer = source.buffer; dest.elementsCount = left; source.elementsCount = right;
      try { record(dest.addAll(source)); } catch (RuntimeException expected) { record(expected.getClass().getName().hashCode()); }
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer)); record(dest.buffer.length);
    }
    for (int left : new int[]{-1,0,4}) for (int right : new int[]{-1,0,1,4,5}) {
      var dest = new LongArrayList(32); var source = new LongArrayList(4);
      dest.elementsCount = left; source.elementsCount = right;
      try { record(dest.addAll(source)); } catch (RuntimeException expected) { record(expected.getClass().getName().hashCode()); }
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer));
    }
    var hook = new HookLong(); var source = new LongArrayList(); source.add((long) 1); source.add((long) 2);
    hook.addAll(source); record(hook.calls); check(hook.calls == 11212);
    var other = new LongArrayList(); other.addAll(hook); record(hook.calls); check(hook.calls == 112123);
    var empty = new LongArrayList(); empty.buffer = null; check(other.addAll(empty) == 0);
  }
  private static final class HookLong extends LongArrayList {
    long calls;
    @Override protected void ensureBufferSpace(int n) { calls = calls * 10 + 1; super.ensureBufferSpace(n); }
    @Override public void add(long value) { super.add(value); calls = calls * 10 + 2; }
    @Override public Iterator<LongCursor> iterator() { calls = calls * 10 + 3; return super.iterator(); }
  }
  private static void testFloat() {
    var random = new Random(146707L);
    for (int run = 0; run < 2500; run++) {
      int left = random.nextInt(65), right = random.nextInt(65);
      var dest = new FloatArrayList(run % 3 == 0 ? left : 256);
      var source = new FloatArrayList(right);
      for (int i = 0; i < left; i++) dest.add(Float.intBitsToFloat(random.nextInt()));
      for (int i = 0; i < right; i++) source.add(Float.intBitsToFloat(random.nextInt()));
      if (run % 5 == 0) { source = dest; right = left; }
      float[] before = dest.toArray(), extra = source.toArray();
      var old = dest.iterator();
      check(dest.addAll(source) == right); check(dest.size() == left + right);
      for (int j = 0; j < left + right; j++) { float a = dest.get(j), b = j < left ? before[j] : extra[j - left]; check(Float.floatToRawIntBits(a) == Float.floatToRawIntBits(b)); }
      int oldCount = 0; while (old.hasNext()) { old.next(); oldCount++; } check(oldCount == left);
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer)); record(dest.buffer.length);
    }
    for (int left : new int[]{0,1,3,8}) for (int right : new int[]{-1,0,1,3,8,9}) {
      var dest = new FloatArrayList(32); var source = new FloatArrayList(8);
      for (int i = 0; i < 8; i++) source.buffer[i] = (float) (i + 10);
      dest.buffer = source.buffer; dest.elementsCount = left; source.elementsCount = right;
      try { record(dest.addAll(source)); } catch (RuntimeException expected) { record(expected.getClass().getName().hashCode()); }
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer)); record(dest.buffer.length);
    }
    for (int left : new int[]{-1,0,4}) for (int right : new int[]{-1,0,1,4,5}) {
      var dest = new FloatArrayList(32); var source = new FloatArrayList(4);
      dest.elementsCount = left; source.elementsCount = right;
      try { record(dest.addAll(source)); } catch (RuntimeException expected) { record(expected.getClass().getName().hashCode()); }
      record(dest.elementsCount); record(Arrays.hashCode(dest.buffer));
    }
    var hook = new HookFloat(); var source = new FloatArrayList(); source.add((float) 1); source.add((float) 2);
    hook.addAll(source); record(hook.calls); check(hook.calls == 11212);
    var other = new FloatArrayList(); other.addAll(hook); record(hook.calls); check(hook.calls == 112123);
    var empty = new FloatArrayList(); empty.buffer = null; check(other.addAll(empty) == 0);
  }
  private static final class HookFloat extends FloatArrayList {
    long calls;
    @Override protected void ensureBufferSpace(int n) { calls = calls * 10 + 1; super.ensureBufferSpace(n); }
    @Override public void add(float value) { super.add(value); calls = calls * 10 + 2; }
    @Override public Iterator<FloatCursor> iterator() { calls = calls * 10 + 3; return super.iterator(); }
  }
}
