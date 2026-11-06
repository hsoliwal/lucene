<!-- SPDX-License-Identifier: Apache-2.0 -->
# Source-loss inventory and immutable fork-main archival custody

Date: 2026-11-03. **DRAFT / HOLD.** This plan precedes all historical source-object archival changes on the existing draft Lucene PR #19 integration branch. The protected fork `main`, original #14/#15/#16 donor refs, live Lucene Java classes, licenses and compilation contracts remain locked.

## Discovery: false no-loss conclusion avoided

- Original local fork `hsoliwal/lucene/main` exact SHA `0127464691af5f1de75e800f1d9468696ec6a641`; receiving new integration PR #19 before this archive `c3c1a4284ab90651d3545c83a40859ea0727ab39`. The latter's tree comes from upstream `apache/lucene` PR #16 plus the exact 9-path local heap optimization #15.
- GitHub compare `main → upstream PR #16` reported 181 paths, **6 removed**, with merge-base `bc0d7097dc765aebfe43ccd9f41f4e702fc87d28`.
- GitHub compare `main → new PR #19` reported 237 paths, **44 removed**, with merge-base equal to fork main `0127464691af5f1de75e800f1d9468696ec6a641`. This reveals **38 additional fork-local paths absent** from the upstream live tree, in addition to the six upstream removals. Earlier source remarks describing only six inherited deletions are insufficient as a full no-loss source certification.
- The bounded 9-path heap donor merge `03d63f9d4d1947c661915dbef84bb96c3fa19cb3` itself added five paths, changed four, and removed none **relative to upstream receiving parent**. It did not restore the 44 earlier fork-main paths. Production runtime equivalence therefore remains unproven, and public `main` promotion is HOLD.

## Exact archive source inventory (no live Java reinstatement)

Every missing source path was audited against GitHub's **complete** `main → PR #19` 237-row diff (below GitHub's 300 row cap). All original objects have Git tree/blobs and modes from fork main; archive is a deterministic three-tree + twelve-file original-object replay, not text reconstruction:

1. Original `.m3/current-jmods/` Git subtree `922c3dea3ff91f324e542e5eaa9f05e88a529fbe`: **23 original file blobs**, including Java 21 module descriptors, legal/LICENSE/source-lock data, verifiers and build scripts. Import the **exact original tree object** under inert archive `objects/.m3/current-jmods/`; retain every internal mode, path and byte.
2. Original `dev-tools/m3-cputools/` tree `f296c413cf09cf01fc50584ce0a89926bc7bedfc`: **four file blobs**. Import the exact original tree object under `objects/dev-tools/m3-cputools/`.
3. Original `m3/primitive-append-20261019/` tree `2a5e6d68af0c79fbbaff4c1a9021caffac48b868`: **five file blobs**. Import the exact original tree object under `objects/m3/primitive-append-20261019/`.
4. Add exactly **twelve** original `lucene/` file blobs, each original mode `100644`, under `objects/<original-path>`:
   - `lucene/analysis/common/src/java/org/apache/lucene/analysis/miscellaneous/FixBrokenOffsetsFilter.java` — `426d9d89688d787dfffcdac3aacc5a2c77cc8ad3`
   - `lucene/analysis/common/src/java/org/apache/lucene/analysis/miscellaneous/FixBrokenOffsetsFilterFactory.java` — `8f41a36b7716c7cae7762cd65160cdfb601759c1`
   - `lucene/analysis/common/src/test/org/apache/lucene/analysis/miscellaneous/TestFixBrokenOffsetsFilter.java` — `a670c05143f14af861a61a40fcfffde190ea3460`
   - `lucene/core/M3-COMPOSITE-CHARSEQUENCE.md` — `11a8cf84294507d763162f09c3882f8335361468`
   - `lucene/core/src/java/org/apache/lucene/analysis/M3TextReaders.java` — `5368641c7bb8f724437a769fbf36bd6abc287662`
   - `lucene/core/src/java/org/apache/lucene/util/packed/DirectPacked64SingleBlockReader.java` — `9883d02b02b35d5d7b93286f7e989e9059bdf11f`
   - `lucene/core/src/java/org/apache/lucene/util/packed/DirectPackedReader.java` — `b21d38578cf7123d373644f7505e0b01c690220b`
   - `lucene/core/src/test/org/apache/lucene/analysis/TestM3CharArrayMapStringHash.java` — `6d466a5469073f0c263fa32d94d4bd86d3a6429f`
   - `lucene/core/src/test/org/apache/lucene/analysis/TestM3TextReaders.java` — `5c0a69bedb686bd4c6096e082eb548a29bd18f3a`
   - `lucene/core/src/test/org/apache/lucene/util/TestM3CompositeCharSequenceBoundary.java` — `1705abfbf382c27dbc62d6592852c084399d6e40`
   - `lucene/core/src/test/org/apache/lucene/util/TestM3CompositeCharSequenceInterop.java` — `e3def499e91919ea1ba758444c3e97748fa0501f`
   - `lucene/licenses/commons-codec-1.22.1.jar.sha1` — `f0c77c9d5e588d7f9f2cb4be920f4c7a8a60138b`

Proof of exact coverage: recursive Git tree snapshots for the three imported source trees were complete, untruncated and contained **23 + 4 + 5 = 32 file blobs**, all matching the original missing-path set. An independent Git tree walk of `lucene/core` (2164 entries) and `lucene/analysis/common` (1548 entries), both untruncated, and original `lucene/licenses` tree confirmed every remaining **12/12** blob and mode. The deterministic union is **44/44 original missing files, zero extra**.

## Intended additive source operation

- Publish this spec **first**. Use only source SHA-1 Git object references returned by the connected original fork-main Git tree. Do not generate, download, copy, rename, reinterpret or edit any donor source bytes.
- Create one new Git tree derived from this docs commit, adding the above **15 immutable Git tree/blob entries** under `m3/qualification/lucene-main-preservation-20261103/objects/`: three entire trees plus 12 original files. All 44 original paths remain encoded under a **passive archive prefix**. Existing Lucene live classes and all prior #15/#16 source bits remain unchanged. There is no public API activation.
- Make a single-parent additive commit to the existing dedicated integration branch with expected-SHA non-force update; preserve the already executed #15 two-parent Git merge ancestry unchanged. Verify the tree root and full source diff against the immediately preceding receiving commit: exactly 44 **new** inert paths; none deleted, modified or renamed. Inspect original subtree modes and imported path SHAs; record result in `VERIFICATION.md`.
- Correct the PR #19 description to explicitly report all 44 absent original fork-main live paths, the passive archive, and the **remaining unresolved live restoration and build gates**. In particular, archive retention is NOT semantic/live-file equivalence and is NOT a substitute for restoring and testing original classes individually.
- Full source reconciliation must choose serial per-file original-authority recipes and test against both upstream #16 and historical fork `main` behavior. Never force class restoration into newer upstream if conflicts cannot be compiled and tested.

## Release HOLD

The 44 original source paths are absent at **their original live locations** in the upstream-derived candidate even after archival. This archive preserves exact bytes/modes/provenance for reconciliation but does **not** certify product additive equivalence. Git source/branch receipt ≠ real Gradle Java 17/21, JUnit, Lucene full reactor, Maven/OpenRewrite source replay, native/JNI, public API/ABI/notice compatibility or benchmark proof. Keep PR draft; do not merge into `main` or present as full all-`hsoliwal/*` release.
