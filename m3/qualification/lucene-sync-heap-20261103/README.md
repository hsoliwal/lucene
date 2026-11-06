<!-- SPDX-License-Identifier: Apache-2.0 -->
# Lucene additive source and Git-history union: upstream sync plus primitive heaps

Date: 2026-11-03. **DRAFT / HOLD**. This specification precedes the source merge on the isolated branch `m3/lucene-sync-heap-additive-20261103`. Do not force, squash, rebase, delete, or merge into protected `main` without original Lucene Gradle/JDK source and runtime acceptance.

## Exact source parents

1. The original receiving Git commit is upstream [Lucene sync PR #16](https://github.com/hsoliwal/lucene/pull/16), head SHA `1c2072f028431da10c80b1d2307c6f56f9297aee` from the `apache/lucene` network, Git tree `3e0e518a16e3cd82048cd4f77f4b052becf39557`. Original fork `hsoliwal/lucene/main` is `0127464691af5f1de75e800f1d9468696ec6a641`. This branch is an independent protected-default candidate; no default refs are modified by this operation.
2. [Lucene upstream sync PR #14](https://github.com/hsoliwal/lucene/pull/14), SHA `2f424fd428da21822ac60b2c51763907f83dff01`, is **already a Git ancestor** of #16: `compare(#14,#16)` returned 11 ahead, zero behind. Do not recreate/reimport the same upstream source history.
3. Independent original primitive Java heap optimization [PR #15](https://github.com/hsoliwal/lucene/pull/15), exact head `486dfe3409f8de9c15b7c522d8b132098c85810c` with common fork base `0127464691af5f1de75e800f1d9468696ec6a641`. Its complete compared source delta is **nine paths: five added, four modified, zero deleted/renamed**, against that common base. Its nine affected path names have **zero intersection** with the 181 changed paths from `main` to #16. The four original Java/test receivers are still **exactly byte-identical** to the common-base source before modification.

## Hash and file-mode custody

The four original Java/test preimages (fork base = #16 receiver) and exact donor afterimages:
- `lucene/core/src/java/org/apache/lucene/util/LongHeap.java`: `9d467ed35bd9df54f02cdc83cdbc8c5a3d6daba3` → `8b0faa5bc7817613dcbc1eafcf221328a0aa2669`.
- `lucene/core/src/java/org/apache/lucene/util/TernaryLongHeap.java`: `75c3e7f0d11720bfab9ac1acaa76d64acef6bca1` → `c1a36588bcb1662d345b7cdc2f408e2e340cc065`.
- `lucene/core/src/test/org/apache/lucene/util/TestLongHeap.java`: `c37b1e3572c040f8d743c71754e825883c915998` → `9313d301df463457580d2a70e57d084c4169a23b`.
- `lucene/core/src/test/org/apache/lucene/util/TestTernaryLongHeap.java`: `335116e7324bbeacd760650399f829ed423d8ec2` → `283d14b30b6bca5707c3af8b577b9e18a59b7b45`.

Five qualification-only source files are absent from #16 and have exact donor Git tree `846f7668614574364c76bcd41403546571898f7a` at `lucene/core/m3/qualification/heap-equal-minimum-20261101`, with five `100644` blobs, no Git tree truncation. Original contents, license notices and Git modes must be retained exactly.

## Additive merge algorithm and correctness boundary

1. Inventory source names/SHAs/modes first and publish this document before any production-target Java changes.
2. Build a candidate tree based on this documentation-bearing receiver commit. Insert the **single original donor qualification tree** in its absent location; update only the four preimage-matching original Java/test files to their exact donor Git postimage blobs. Reject any source collision, mode change, missing source or extra path.
3. Create a genuine two-parent commit with receiving docs parent first and donor #15 exact head second. Non-force expected-SHA advance of the isolated integration branch only. Recheck exact nine donor filename/status changes, zero additions/deletions beyond these nine; preserve unchanged receiver and both parent histories.
4. Record Git source custody and actual merge metadata in a separate `VERIFICATION.md`, and open one **draft PR targeting `main`** for review. Do not merge into `main` by default.
5. Existing first-party Apache Lucene heap classes and ASF Apache-2.0 notices remain the implementation donor. Problem-category references on LeetCode/HackerRank/GeeksforGeeks do not authorize copying solutions or editorial code. The original fast path optimizes equal-minimum `insertWithOverflow(long)` and is **not** an independent JNI implementation. Public method descriptors, heap array identity, accepted flags, min thresholds, all deterministic observables and negative/overflow behavior are locked.

**HARD HOLD:** actual Lucene Gradle Java17/21 source compatibility and Gradle/JUnit `TestLongHeap` / `TestTernaryLongHeap` differential execution, complete upstream fork drift resolution, public binary/API, all platforms, benchmarks, donor license and source-review evidence. Upstream #16 source already differs from local `main` (six files removed in that upstream sync candidate); those inherited deletions are **not** attributed to this nine-path additive heap merge and require their own reconciled source policy. Git object equality is custody, not compiled product behavior.

This is a bounded candidate for the three inventoried Lucene PRs, **not** a claim that every `hsoliwal/*` PR has been merged.
