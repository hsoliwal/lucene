<!-- SPDX-License-Identifier: Apache-2.0 -->
# Direct Lucene CharArrayMap rehash optimization (2026-11-13)

**Scope:** `hsoliwal/lucene` public collection fork, NOT M3JDK21 or an alternate collection layer. Existing `char[][] keys` and `V[] values` remain the only retained payload. This is a public scratch-fork candidate; canonical reusable Maven/OpenRewrite recipes and M3JDK product admission remain separately owned by Synexia.

## Exact source and one replaceable atom

- Baseline: `hsoliwal/lucene@d5dfbe196f4c47c310fca8e18026f53d4689771d`, `lucene/core/src/java/org/apache/lucene/analysis/CharArrayMap.java` Git blob `70d3d8e5cb554c7e20c886e6d949800460dc7ac1`.
- Before full UTF-8 SHA-256: `f8c1ef9173d597ebca825d814af03e4a87884f1a7c4a891ae8bb0f70b3edfe4d`.
- Candidate full UTF-8 SHA-256: `17455d4ee3787f701ddcf3429fa405de854a1614980ab33aa1da174d66a54753`.
- Private operation: `CharArrayMap.rehash()`, the placement of distinct already-stored `char[]` keys into a fresh table. The original method invokes `getSlot()` for each key; `getSlot` scans collision candidates by calling `equals(char[], ...)` even though the target table starts empty and all retained keys were distinct on admission. Replace only that rehash-time lookup with an occupancy-only probe using the **same** `getHashCode`, signed right-shift, odd step and power-of-two bucket mask. No additional allocation, output array, object, public method, JNI path or callback.

## Contract boundary

- All valid map states preserve exact resulting slots and values, including zero-length keys, null values, Unicode/supplementary code points, case-insensitive mode, rehash size and existing iteration/serialization behavior. The optimization assumes the documented contract that callers **never mutate retained key character arrays after insertion**; externally violating that contract can defeat the uniqueness invariant and is outside the admitted domain. No user-defined key `equals` callbacks are removed: private `equals` compares primitive `char` contents only.
- Inserting and querying new/existing keys still uses the original public methods and `getSlot`. `CharArrayMap` subclass hooks and public/protected signatures are untouched.

## Actual proof completed before fork edit

An independent Java 21 standalone oracle copied the exact hash/probe/equality atoms, and compared original vs occupancy-only `rehash` at the same output positions and value identities for random, case-insensitive Unicode and colliding-key workloads. **146,148 checks passed**, including 512 and 1,024 deliberate equal-hash keys. `javac -Xlint:all -Werror` passed.

Five warmed independent JVM benchmark processes (nine alternating per-process samples) measured:
- 4096 random distinct keys: candidate/baseline ratios **0.8098, 0.9688, 0.9548, 0.9163, 1.2076**. The fifth run regressed; therefore **random-workload performance admission remains HOLD** pending JMH/representative benchmark.
- 512 distinct same-hash keys: ratios **0.1658, 0.2394, 0.1670, 0.1625, 0.1750**. This is a bounded strong collision-path improvement, not proof of denial-of-service resistance.

The original code, new source, actual test, and observed performance evidence must be reviewed before merging. The full Lucene Gradle reactor, `TestCharArrayMap`, test variants and source-built M3JDK acceptance have **NOT_RUN** in this session (no offline Gradle dependencies). They are required for promotion; a queued GitHub job is not a passing test. This candidate does not widen source or product scope.
