<!-- SPDX-License-Identifier: Apache-2.0 -->
# M3 Lucene primitive heap equal-minimum contract

This public product fork receives exact source output from **private canonical Synexia [PR #15080](https://github.com/hsoliwal/com.synexia/pull/15080)** crate `m3-lucene-longheap-equal-20261101`. No Java/OpenRewrite mutation engine or recipe is duplicated here.

Upstream `apache/lucene` and this fork shared the identical preimage Git blob `9d467ed35bd9df54f02cdc83cdbc8c5a3d6daba3` for `LongHeap.java`. Original ASF notices are unchanged. The public API, class/package, heap array ownership, native/JNI bindings (none in this owner), comparator behavior (primitive only), and persisted format remain unchanged.

The sole production atom is in `LongHeap.insertWithOverflow(long)`: if an accepted value equals the existing root of a *valid* full primitive heap, the existing `updateTop()`/sift cannot move a child and the new path returns `true` directly. The rejected strict-smaller path and every different inserted value still use the original algorithm.

`TestLongHeap` extends its existing JUnit/Lucene suite with 6 capacities, 8 seeds, and 1000 offers each. This tests min-heap validity, capacity, threshold, full primitive-array contents for equal offers, `PriorityQueue` logical reference and final drain ordering. An independently compiled standalone Java 21 atom oracle passed **321,280** bounded operations with exact original vs candidate heap arrays; this does not substitute for Gradle/JUnit.

The native Lucene gate is the existing Gradle core test workflow. Suggested focused reproduction after cloning this PR:
```bash
./gradlew :lucene:core:test --tests 'org.apache.lucene.util.TestLongHeap'
```
Actual target Gradle result, canonical Maven/OpenRewrite fixed-point, API/ABI, and calibrated A/A performance **remain pending**. This PR is intentionally DRAFT/HOLD. No benchmark claim or blanket collection fork mutation.

Challenge-site references (LeetCode 215, HackerRank heap problems, GeeksforGeeks Binary Heap) supply only general algorithm categories, not copied source or test material.

## Serial second owner: 3-ary `TernaryLongHeap`

The same **`HEAP_EQUAL_MIN_BYPASS`** category pattern was checked against a distinct arity-3 primitive min-heap. The canonical [Synexia PR #15080](https://github.com/hsoliwal/com.synexia/pull/15080) crate contains an independent `m3-lucene-ternaryheap-equal-20261101` Java source manifest and JUnit/fixed-point/refusal tests; the original binary-heap source snapshot is unchanged. Original upstream `TernaryLongHeap` blob: `75c3e7f0d11720bfab9ac1acaa76d64acef6bca1`. Original upstream `TestTernaryLongHeap` blob: `335116e7324bbeacd760650399f829ed423d8ec2`.

The new target test verifies 6 capacities × 8 seeds × 1000 bounded inserts, independent PriorityQueue values, all physical heap slots for equal minima, min-heap validity and final drain order. Independent Java21 original-versus-candidate **321,280 comparisons passed** with arity-3 `upHeap`/`downHeap`; this is not a claim of successful Lucene Gradle CI. **No public APIs, JDK implementation, JNI symbols or array-payload ownership were changed.** Both Lucene heap files retain ASF/Apache-2.0 headers, and all unproved performance gates remain HOLD.

Focused Gradle owner tests:
```bash
./gradlew :lucene:core:test --tests 'org.apache.lucene.util.TestLongHeap' --tests 'org.apache.lucene.util.TestTernaryLongHeap'
```
