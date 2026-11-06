<!-- SPDX-License-Identifier: Apache-2.0 -->
# Lucene upstream sync and primitive heap optimization: actual additive Git source merge

Date: 2026-11-03. **Executed exact Git source/history merge on an isolated branch only. Original Gradle/JUnit runtime and default-branch acceptance NOT EXECUTED / HOLD.**

## Pinned source parents and execution receipts

- Fork default `main` at inventory: `0127464691af5f1de75e800f1d9468696ec6a641`.
- Receiving latest upstream-sync [#16](https://github.com/hsoliwal/lucene/pull/16) source head `1c2072f028431da10c80b1d2307c6f56f9297aee` with Git tree `3e0e518a16e3cd82048cd4f77f4b052becf39557`. Earlier [#14](https://github.com/hsoliwal/lucene/pull/14) head `2f424fd428da21822ac60b2c51763907f83dff01` is already a true ancestor of #16 (11 ahead/0 behind). Do not separately duplicate its upstream commits.
- Heap optimization and differential tests [#15](https://github.com/hsoliwal/lucene/pull/15) exact head `486dfe3409f8de9c15b7c522d8b132098c85810c` has **nine** source changes from the same `hsoliwal/lucene/main` preimage: **five added, four modified, zero removed**. The 181 changed paths in #16 do **not** intersect the nine paths modified by #15.
- The two original heap Java files and both original heap test files were checked by remote GitHub Contents: **all four receiver blobs equal the fork-main preimage byte-for-byte**. Original Java file modes and donor postimage modes were checked through exact Git tree entries (`100644`).
- Documentation-first receiving commit `017b672b3fb147d6a10677035d2bac8d5ec19a8c`; base Git tree `9d89cb2f24876fe180d5652526c68fc5ffe5321c`. [README.md](README.md) was published before any new source image.
- New candidate Git tree `5ccb0bac0939e44f2ce5e3556cec9acbc0835208` was built by importing **one existing donor tree object** `846f7668614574364c76bcd41403546571898f7a` (`lucene/core/m3/qualification/heap-equal-minimum-20261101`, five original files) and four exact donor Java/test postimage blobs. The donor subtree was absent from the receiving branch and its five `100644` files were verified directly against GitHub Git tree data.
- Pre/post candidate root tree inspection: only top-level `lucene` changed. Within `lucene`, only `core` changed. Within `core`, only the added `m3` subtree and existing `src` subtree changed. The imported qualification tree retained **the exact donor Git SHA**. All other root and unrelated `lucene/core` tree entries were unchanged.
- Actual two-parent Git merge commit **`03d63f9d4d1947c661915dbef84bb96c3fa19cb3`** has ordered parents `[017b672b3fb147d6a10677035d2bac8d5ec19a8c, 486dfe3409f8de9c15b7c522d8b132098c85810c]` and exact candidate tree `5ccb0bac0939e44f2ce5e3556cec9acbc0835208`. Git commit object metadata was retrieved and verified after creation; branch `m3/lucene-sync-heap-additive-20261103` was advanced with a non-force expected-SHA lease and remote readback.
- Complete `compare(documentation parent, merged)` vs `compare(original fork main, original #15 donor)` was checked by exact `(filename,status)` sets: **9/9 exact matches**, 0 missing/extra, **5 added, 4 modified, zero renamed/removed**. The merged candidate has zero commits behind the original #15 donor head.

## Exact Java/heap behavioral skeleton and blob witnesses

| File | Original receiver Git blob | Donor = merged Git blob |
| --- | --- | --- |
| `lucene/core/src/java/org/apache/lucene/util/LongHeap.java` | `9d467ed35bd9df54f02cdc83cdbc8c5a3d6daba3` | `8b0faa5bc7817613dcbc1eafcf221328a0aa2669` |
| `lucene/core/src/java/org/apache/lucene/util/TernaryLongHeap.java` | `75c3e7f0d11720bfab9ac1acaa76d64acef6bca1` | `c1a36588bcb1662d345b7cdc2f408e2e340cc065` |
| `lucene/core/src/test/org/apache/lucene/util/TestLongHeap.java` | `c37b1e3572c040f8d743c71754e825883c915998` | `9313d301df463457580d2a70e57d084c4169a23b` |
| `lucene/core/src/test/org/apache/lucene/util/TestTernaryLongHeap.java` | `335116e7324bbeacd760650399f829ed423d8ec2` | `283d14b30b6bca5707c3af8b577b9e18a59b7b45` |

Each donor and merged blob had **exact source-byte equality**. A structural declaration-line check found the same 12 public/protected declaration lines before and after in each **production** heap Java class. Each test class gained two public test declaration lines (10 → 12); this is **expected additive test coverage**, not a production API change, and should not be classified as a public API drift. The source proof does not replace the compiler.

The optimized existing primitive Java `insertWithOverflow(long)` behavior and accepted/rejected flags, root equality/thresholds, original array ownership, tie order, negative/overflow behavior and public APIs remain **contract-locked**, subject to actual JUnit proof. This candidate adds no JNI symbols or native C source.

## Important upstream and donor constraints

- The source receiving head #16 predates any protected-fork acceptance and already removes six files relative to the previous `hsoliwal/lucene/main` snapshot; those **inherited upstream changes** are not additions/deletions introduced by the bounded #15 merge. Their default-branch policy must be independently reconciled.
- ASF Lucene Apache-2.0 source notices remain authoritative. LeetCode/HackerRank/GeeksforGeeks only supply problem taxonomy and independent oracle scenarios, **not** license to copy solution code.
- Actual Gradle compilation/test execution with Lucene's prescribed JDK, `TestLongHeap`, `TestTernaryLongHeap`, original core/module regressions, performance and memory profiling, source/API compatibility and protected-main integration are **NOT VERIFIED on this merged head**. Await real compile/test/runtime logs. Do not infer passing CI from mergeability, source Git SHA, or lack of workflows.

**The verified result is a real additive source/history union of the three inventoried Lucene PR lineages, not full product equivalence or fleet-wide `hsoliwal/*` merge completion.**
