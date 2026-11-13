<!-- SPDX-License-Identifier: Apache-2.0 -->
# Lucene FixedBitSet: early exit after destination array fills

**Direct original fork source:** `hsoliwal/lucene@d5dfbe196f4c47c310fca8e18026f53d4689771d` `lucene/core/src/java/org/apache/lucene/util/FixedBitSet.java`; preimage full Git blob `544f46a7291c695c685282061967f0012f033e52`. No M3JDK or other runtime modification.

## Locked observable contracts

`FixedBitSet.intoArray(from, to, base, int[])` is defined to stop when the destination has no remaining capacity. The current method continues to scan subsequent `long[]` words and call the private `word2Array` even after its output offset equals `array.length`. For actual nonempty admitted ranges, introduce an early return immediately after the existing leading unaligned fragment and after each full-word conversion when the array has filled. Leave the existing `Objects.checkFromToIndex` validation, initial null-array interactions on empty aligned ranges, per-word ordering, dense/sparse copy decisions, sentinel values, output-prefix writes and final partial-word handling unchanged.

No persistent storage/index, collection class, public signature, constructor, serialization, JNI or module dependency changes; only `FixedBitSet.intoArray` body may change. The `long[] bits` storage remains authoritative. Complexity for a tiny output buffer becomes O(words until the last copied bit), rather than O(all queried words), without affecting output.

## Ordered gates
1. Git blob identity and exact source-method diff, before/post SHA-256.
2. Java21 `javac -Xlint:all -Werror` original/candidate standalone method oracle; expected equality for empty ranges, null arrays, malformed bounds, dense/sparse words, aligned/unaligned starts and multiple capacities. Old exception type and partially written destination must match.
3. Repeated time measurements on long ranges with tiny arrays and representative full-capacity workloads; record any regressions. No JMH claim from standalone measurements.
4. Actual fork `TestFixedBitSet` / added JUnit test, full Gradle, binary contract and cross-platform JMH. **NOT_RUN** locally without full project dependencies. Fork source PR stays draft, no automatic merge.

The block comment documents the previously unfulfilled early-stop contract; it does not authorize eliding bounds validation or changing behavior on externally invalid calls.
