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

## Exact implemented source and bounded runtime receipt

- Original full Git blob `544f46a7291c695c685282061967f0012f033e52`, SHA-256 `cc7ccd80308828decc44c602ef637c03d80dc07d14c9ce25ba67c3ae1d774e91`.
- Candidate Git blob `d013a253c0dd5c0880c60d9b67fad7334b25c96c`, SHA-256 `1dca01288423af436393e8db9eb064ad2099e075acf8fee87ca889841c343f0a`. The only two added conditionals are within `intoArray` after the original leading-fragment and full-word conversion calls; all other source bytes are unchanged.
- Real fork-owned `TestFixedBitSetIntoArrayCapacity.java` has four LuceneTestCase methods covering one-entry destination, unaligned leading fragment, invalid-range exception ordering, historical null-on-empty behavior and dense/sparse word order.
- Independent exact-method witness `FixedBitSetIntoArrayProbe.java`, SHA-256 `b0164f713003a5923ae2a3f76b2d9880ed242544cf7be90ca1300b16df1d1900`, compiled under Java21 `-Xlint:all -Werror` and passed **124,001 comparisons** in JIT plus **124,001** in `-Xint`. Random 0..1024-bit maps, aligned/misaligned/empty ranges, random integer bases, invalid index/null arrays, output-prefix and full destination contents were compared against the original method.
- In five warm standalone samples of 1,048,576 queried bits with the first bit present and a one-int destination, candidate/base timing ratios `0.0040/0.0019/0.0023/0.0014/0.0086`. For a set bit only at the *last* position, results were mixed (one `1.277` sample regression). This is a **bounded conditional improvement**; full product performance admission is HOLD until actual Lucene Gradle/JMH.
- The local JIT/interpreted proof log SHA-256 values are `545c401fe730b6561fb5226e147bcde0f39757dc8152b233cd4106bbfbbe3f0f` / `19dedaab42f0c031147c515a5967e296a72dbf035f7b3320f26c7631722e6767`. No full fork-native tests executed locally.
