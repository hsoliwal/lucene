<!-- SPDX-License-Identifier: Apache-2.0 -->
# Executed zero-source-delta all-five-open-PR ancestry merge — Lucene, 2026-11-03

**Status:** exact Git history source-preserving merge **EXECUTED** on a new private branch; live source/classpath/Java/Gradle compatibility **NOT VERIFIED / DRAFT HOLD**. This receipt reports only work that was actually executed through authenticated GitHub Git Data/compare tools.

## Frozen original PR scope

At inventory, **exactly five open pull requests** were returned by the GitHub search for `hsoliwal/lucene`: #14, #15, #16, #18, #19. The common protected fork `main` head was `0127464691af5f1de75e800f1d9468696ec6a641`. Original PR heads:
- #14 original upstream source: `2f424fd428da21822ac60b2c51763907f83dff01`.
- #15 exact heap Java/JUnit source: `486dfe3409f8de9c15b7c522d8b132098c85810c`.
- #16 newer upstream source: `1c2072f028431da10c80b1d2307c6f56f9297aee`, already a descendant of #14 (11 ahead, zero behind).
- #18 existing first-party source-review merge: `d4762c2cfb3b1c30692a2a166688f328befe9b6c`, two parents `[0127464691af5f1de75e800f1d9468696ec6a641, 486dfe3409f8de9c15b7c522d8b132098c85810c]`, source tree `fec383db7017a7421b00df9bd13ce33e7985dc56`. This exact Git tree is **identical** to original #15's entire tree, confirmed from Git commit objects and `compare(#15,#18)` (one ahead, zero behind, **zero changed files**). This is evidence of source-byte identity, not inferred semantic equivalence.
- #19 existing upstream-sync plus original heap/qualification source and passive archival preservation head `662192812c1668786ce68d08048edfc0a8aa6408`, tree `d45c0b130f1a8c58579aa1af7070cc7e0305272c`. Its existing two-parent source merge `03d63f9d4d1947c661915dbef84bb96c3fa19cb3` had original #15 as a second parent, so #19 already retained **all #18 source blobs**, but had **not** retained the distinct #18 historical two-parent commit (comparison showed one commit behind).

## Actual merge and postimage checks

1. Created isolated branch `m3/lucene-all-open-ancestry-20261103` at original #19 head. Documented this exact no-delta procedure in [README.md](README.md) **before** merge at commit `9c0f5ff88fa6e87375678b9caf96ce5af7105b23`, Git tree `6cfbc1a5afa6beb55bebc0f4a135175dffd78d26`.
2. Created actual **two-parent Git merge commit `865e8468f47606f8a4411959f75a904e1950ac3f`** with first parent documentation commit `9c0f5ff88fa6e87375678b9caf96ce5af7105b23`, second parent exact #18 head `d4762c2cfb3b1c30692a2a166688f328befe9b6c`, and **unchanged first-parent tree `6cfbc1a5afa6beb55bebc0f4a135175dffd78d26`**. Original PR #18 and PR #19 source trees were not modified or force-pushed. The merged Git commit object was independently fetched and its parent order/tree SHA verified exactly.
3. Advanced **only** the new private integration branch with a non-force `update_ref` and the exact expected previous commit SHA, then read back its remote head as `865e8468f47606f8a4411959f75a904e1950ac3f`.
4. GitHub `compare(documentation-first parent, merge)` returned **two ahead, zero behind, zero changed file rows**: no file added/modified/deleted, no source recopy, and no rename. (Two commits includes a commit accessible through the previously missing PR #18 history; this does not imply two new code changes.)
5. Explicit ancestry comparisons `compare(each original PR head, merge)`: all five original heads are **zero behind**. Details: from #14 (86 ahead, zero behind), #15 (53 ahead, zero behind), #16 (75 ahead, zero behind), #18 (52 ahead, zero behind), #19 (3 ahead, zero behind). Against #19 source, the only new path before this verification receipt is the source contract README; all original product/classifier/heap/current-JMOD/archival bytes remain unchanged by the zero-source-delta merger.

## Important release and source-equivalence distinctions

PR #19 still has the original **44 fork-main live paths absent** at their live paths after upstream-derived source replacement. Their complete original source blobs/modes were passively archived under `m3/qualification/lucene-main-preservation-20261103/objects/`, with exact 44/44 archival custody and a separate prior ledger. Historical source custody **does not** make current live Java APIs equivalent. No accidental silent default-branch restoration, deletion or classpath source injection is justified by adding PR #18 ancestry.

**NOT EXECUTED on the final combined head:** original Lucene Gradle/JDK compiler, TestLongHeap/TestTernaryLongHeap JUnit, Maven/OpenRewrite source replay/fixed point, public API/ABI compatibility, original-main 44-path semantic reconciliation, full core/analysis modules, license notice acceptance, performance benchmarks and hosted CI. Git mergeability/source hashes alone are never a runtime PASS. Keep all original PR heads available and any final review PR **DRAFT/HOLD**. The protected `main` was unchanged. This completes the exact five currently inventoried Lucene PR histories, **not** every `hsoliwal/*` open PR or a product release.

## Gmail / Google Drive source decisions

The connected Gmail searches for merge-related GitHub notification and project approval content returned no actionable approval evidence. The existing Google Drive checkpoint `M3 Additive Superset PR Merge — Live GitHub + Drive Checkpoint — 2026-11-03` is the selected canonical audit record; append this actual Git merge/zero-tree-change receipt by revision-controlled write and verify readback, without duplicating older PR or private-email content.
