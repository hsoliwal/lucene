<!-- SPDX-License-Identifier: Apache-2.0 -->
# Additive no-loss ancestry supersession of all five open Lucene PRs

Date: 2026-11-03. **DRAFT / SOURCE-ONLY / HARD HOLD**. This is a bounded explicit reconciliation of all *currently inventoried* open `hsoliwal/lucene` PRs (#14, #15, #16, #18, #19), not an assertion that the full `hsoliwal/*` PR fleet has been merged or that Java/Gradle release requirements passed.

## Frozen exact source/history inventory

- Fork `hsoliwal/lucene/main` SHA `0127464691af5f1de75e800f1d9468696ec6a641` is protected for this operation. No default-branch mutation.
- Upstream-sync PR [#14](https://github.com/hsoliwal/lucene/pull/14) `2f424fd428da21822ac60b2c51763907f83dff01` is already a Git ancestor of PR [#16](https://github.com/hsoliwal/lucene/pull/16) `1c2072f028431da10c80b1d2307c6f56f9297aee` (11 commits ahead, 0 behind). Do not re-copy source bytes or independently re-run its donor overlay.
- Primitive Java heap optimization PR [#15](https://github.com/hsoliwal/lucene/pull/15) head `486dfe3409f8de9c15b7c522d8b132098c85810c` was incorporated as the second Git parent of original PR [#19](https://github.com/hsoliwal/lucene/pull/19) through actual two-parent merge `03d63f9d4d1947c661915dbef84bb96c3fa19cb3`, with exact nine original source files (five additions/four modified, no deleted). The resulting retained source and original public Java heap signature skeleton are source-verified; real Java JUnit and Gradle gates remain unverified.
- The existing PR #19 exact head `662192812c1668786ce68d08048edfc0a8aa6408`, tree `d45c0b130f1a8c58579aa1af7070cc7e0305272c`, additionally archives 44 original fork-main blobs absent at their original paths in the upstream-derived live tree. Its history is not a complete live-source restoration; those 44 source paths still need per-file contract/ABI reconciliation.
- Remaining concurrent review-only PR [#18](https://github.com/hsoliwal/lucene/pull/18) exact head `d4762c2cfb3b1c30692a2a166688f328befe9b6c` is **already a real two-parent merge** of the original fork main and original #15 head. Its commit tree SHA `fec383db7017a7421b00df9bd13ce33e7985dc56` equals #15's original entire Git tree SHA **exactly** (not just matching a few files). GitHub `compare(#15,#18)` returns **one commit ahead/zero behind and zero changed files**. PR #19 currently **does not include #18's distinct merge commit as an ancestor** (`compare(#18,#19)` reports one behind); it already includes all #18 source bytes via #15. Therefore #18's only missing contribution is historical merge ancestry, not any new Java/Gradle blob.

## Exact proposed integration (documentation precedes merge)

1. Create a private integration branch from PR #19's exact frozen head and publish this specification first. Freeze both review refs; refuse any moving SHA or occupied destination. No source rename, delete, or use of a stale default-branch tree.
2. Use the **receiving documentation commit's Git tree itself unchanged** to create a new actual two-parent Git commit: first parent = this documented PR #19 descendant, second parent = original PR #18 head `d4762c2cfb3b1c30692a2a166688f328befe9b6c`. The only intended addition is Git commit ancestry; any tree/blob change is prohibited. This is a **true empty-tree-delta merge**, not an `ours` conflict override, because source content from both PR #18 and PR #15 is proven *whole-tree identical*, and PR #19 retains #15 source with its own intentional upstream and archival history.
3. Re-read the created Git commit object: require exact Git tree SHA and ordered two parents. Advance only this new integration branch non-force with its expected previous SHA, verify remote ref readback. Confirm zero changed files against the first parent, zero commits behind both PR #18 and #19, and all original PR #15/#16 source histories remain ancestors.
4. Commit a post-execution `VERIFICATION.md` proof ledger and open a review-only **draft** PR against `main`. Retain original PR #14, #15, #16, #18, #19 branches; do not close or modify their source.
5. A subsequent branch may merge to `main` **only after** actual original fork-main path restoration decisions for the 44 archived files, real Lucene Gradle/JDK17/21 and differential JUnit tests, source/license/API/binary checks and runtime/performance acceptance. A zero-diff ancestry merge is not an approval or release test.

## Gmail and Google Drive evidence

On 2026-11-03 the linked Gmail searches for GitHub merge-related notifications and approval discussions returned **no actionable merge authorization**, so this operation does not infer approval from email. Google Drive's existing `M3 Additive Superset PR Merge — Live GitHub + Drive Checkpoint — 2026-11-03` contains source-only predecessor PR #19 and 44-file archive receipts. Add the actual new head/parent/tree verification there using a revision-controlled append only after execution.

## Licensing and donor scope

ASF Apache-2.0 original Lucene sources remain the implementation owner. LeetCode, HackerRank and GeeksforGeeks are algorithm-category references; no site-submission/editorial source was imported. The source merge is not a Java/JNI implementation enhancement and cannot be presented as newly compiled Java/C code.

**HOLD:** original Gradle Java17/21 target/test execution, JUnit `TestLongHeap`/`TestTernaryLongHeap`, Maven/OpenRewrite canonical source recipe/fixed-point, Java/JNI public ABI, all 44 original live paths and their source-level compatibility, full build, hosted CI and benchmarks. No run receipt from this merge has proven these gates.
