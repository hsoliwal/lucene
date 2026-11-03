<!-- SPDX-FileCopyrightText: 2026 Hitesh Soliwal and Contributors to the Synexia Project -->
<!-- SPDX-License-Identifier: Apache-2.0 -->
<!-- License: https://www.apache.org/licenses/LICENSE-2.0 -->
# Primitive-list bulk append

Float, Int and Long array lists bulk-copy eligible exact-class, nonempty source
ranges after the original capacity check. Empty lists, subclasses, unsafe shared
buffer overlap and invalid public-field states retain the original iterator path.
This preserves callback and partial-update behavior while avoiding cursor work
on the admitted path. No fields, signatures or per-entry objects are added.

Run `./gradlew --no-daemon --max-workers=1 :lucene:core:test --tests
'org.apache.lucene.internal.hppc.Test*ArrayList'` with the branch's required JDK 25.
Native baseline and candidate each pass 104 selected tests with zero failures,
errors or skips. The standalone contract adds 505,415 checks per version and the
matching transcript 8352013011485974343. Every field/method descriptor is unchanged.

Final JMH bulk append improves time and allocation for all three types at sizes
64 and 4,096. Small and some copying cells remain inconclusive. The initial
empty-list slowdown was rejected; its source/results and the adaptive guard repair
are preserved in the canonical evidence. Complete product/device performance
qualification and universal space-and-time admission remain open.

Canonical recipe, manifests, licenses, failure history and exact replay commands:
[6edef08c6ab5a3b29dad74a13d3f20df687e0ef5](https://github.com/hsoliwal/com.synexia/tree/6edef08c6ab5a3b29dad74a13d3f20df687e0ef5/synexia-openrewrite-recipes/crates/m3-all-fork-array-pass-20261019).

This receiver retains proof witnesses only. Reusable recipe ownership remains in
Synexia; the collection runtime has no Synexia dependency. First-party witnesses
are Apache-2.0; existing target source keeps its original upstream notices.
