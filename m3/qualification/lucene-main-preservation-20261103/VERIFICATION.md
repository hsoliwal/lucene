<!-- SPDX-License-Identifier: Apache-2.0 -->
# Exact 44-file fork-main source preservation — executed Git receipt

Date: 2026-11-03. **Private additive Git archive executed, live source path restoration and all product/runtime gates HOLD.** This record is written after the independently verified archive commit.

## Original fork-main source loss detected

- Frozen fork `main@0127464691af5f1de75e800f1d9468696ec6a641`, new upstream-plus-heap source candidate `c3c1a4284ab90651d3545c83a40859ea0727ab39`.
- Original `compare(main, upstream #16)` returned 181 changed files with six `removed` statuses, but the merge base was `bc0d7097dc765aebfe43ccd9f41f4e702fc87d28` rather than current fork main.
- Subsequent `compare(main, candidate PR #19)` had exact merge base `main` and 237 changed entries with **44 `removed` statuses**. The source audit found 38 additional fork-local originals missing from upstream, beyond six upstream deletions. These 44 original live paths were NOT restored by the four-file `LongHeap`/test optimization and were not safe to silently discard.
- The actual source bytes for all 44 original paths were available as Git objects in frozen fork main. Entire original path/mode census was verified with complete Git trees: three subtrees held **23+4+5=32** unique file blobs; a fully enumerated `lucene/core` tree (2,164 entries), `lucene/analysis/common` tree (1,548), and `lucene/licenses` held **12 individual** source/test/license blobs, all modes `100644`. The 44 sorted path set matched the complete 237-row compare removal set exactly, with zero missing or extra files.

## Documentation-first Git source objects and exact chronology

- [README.md](README.md) committed **before source archiving** as `518778f67de1658136375fcf5cfacf61a42f8623`. Its Git tree `3df95dc8329b5c641213ecbf913f72f0e64b44de` preserved original PR #19 source.
- Created Git candidate tree `cd1aa23b2136534390a89dee9dc4bea7bc5f89a6` from this preimage. Only `m3/qualification/lucene-main-preservation-20261103/objects/` changed: **three exact original tree objects** and **12 exact original blob objects** were placed below that passive archive prefix. No source bytes were regenerated. The source trees and all nested Git file modes retained their original SHA/mode identities:
  - `.m3/current-jmods`: `922c3dea3ff91f324e542e5eaa9f05e88a529fbe`, 23 original files
  - `dev-tools/m3-cputools`: `f296c413cf09cf01fc50584ce0a89926bc7bedfc`, four original files
  - `m3/primitive-append-20261019`: `2a5e6d68af0c79fbbaff4c1a9021caffac48b868`, five original files
  - Remaining 12 `lucene/` paths retain individual original Git blob SHA and original mode `100644`, itemized in the documentation-first README.
- Full candidate tree structure was inspected: only top-level `m3` changed; within `m3`, only `qualification` changed; within `qualification`, only the existing document-bearing `lucene-main-preservation-20261103` changed. The original `lucene/` live source tree, existing heap implementations and existing archived M3 leaf were **completely unchanged** by this operation.
- Verified the new `objects/` tree `71d1fdf1289f504c1aa0b8e15a9676fe938f5ead` recursively: **91 Git tree entries containing exactly 44 Git blobs**, all files' relative paths exactly equal the 44 previously missing fork-main live paths, **zero extra or missing**, no truncated result and no unrecognized file modes.
- Committed normal single-parent archival change `fea6b5d3bed11be3013b98cfc72400dbe4b8f84b`, parent `518778f67de1658136375fcf5cfacf61a42f8623`, tree `cd1aa23b2136534390a89dee9dc4bea7bc5f89a6`. Re-fetched Git commit, verified exact parent/tree. Advanced only the dedicated PR #19 branch with **non-force expected-SHA CAS**, read back new remote head.
- Complete `compare(documentation parent, archive commit)`: **44 additions, zero modifications, zero removals/renames**, all under the passive `objects/` path. Complete `compare(original PR #19 head, archive commit)`: **45 additions** (README plus 44 archive paths), zero other modifications or deletions.

## Critical distinction: archive retention is not live-path behavioral restoration

After archiving, GitHub `compare(fork main, archive commit)` has the identical current-main merge base and reports **238 changed paths** with **44 `renamed` statuses**, all mapping an original missing live path to its newly archived passive counterpart. GitHub's rename detector therefore correctly recognizes that the original source blobs have been preserved under different paths. **The original 44 live paths remain absent in the upstream-derived source tree**, even though the blob bytes/modes now exist under `m3/qualification`. This does **NOT** prove API, Gradle, Java/JNI, source path, or observable behavior equivalence and must not be described as a completed live superset.

The original PR #15 four Java/test afterimages still match donor Git blobs; original PR #14 sync is an ancestor of PR #16; all histories and the fork main are preserved. The user requested additive superset: this is a **verifiable no-source-byte-loss archival step**, not full live code integration. All 44 original paths require serial exact-source reconciliation with modern upstream before any restoration or retirement is approved.

## Mandatory HOLD

Real original Gradle/JDK compilation, heap regression JUnit, exact baseline-vs-upstream integration (including classpath/module JMOD sources), full Lucene tests, source/JNI ABI signatures, Maven/OpenRewrite fixed-point and source-mode proof, Java 21 compatibility, performance and platform acceptance have **not been executed on this combined head**. Draft PR #19 must remain unmerged. `hsoliwal/lucene/main` and all other `hsoliwal/*` default branches were not modified by this archival source operation. LeetCode/HackerRank/GeeksforGeeks remain category/oracle references only, not code-copy donors.
