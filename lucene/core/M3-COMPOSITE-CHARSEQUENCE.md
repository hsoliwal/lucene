# M3 composite String contract: Lucene

This additive test-only patch verifies existing Lucene APIs that can consume
non-flat, segmented logical UTF-16 characters with no explicit toString().

Verified entry points by source inspection:
- BytesRef(CharSequence): encodes UTF-16 units as Lucene-required UTF-8 bytes.
- CharArraySet.contains(CharSequence): direct hash/character comparison.
- CharArrayMap.get(CharSequence): direct hash/character comparison.

The test uses a CharSequence whose toString() throws, covering all split
positions of ASCII, combining marks, BMP Unicode, and supplementary Unicode
(including splits between high/low surrogate code units). It also tests
case-insensitive Deseret membership. Thus successful tests rule out accidental
eager materialization on these entry points. No production source/API change
is required to obtain that capability.

Do NOT blindly rewrite CharArrayMap.put(CharSequence): its current behavior
materializes via toString(), which can be observably different from iterating
charAt for arbitrary CharSequence implementations. Preserve BytesRef's
UTF-8 index wire format and unsigned byte order.

Canonical Synexia original and SHA-256 source-pinned Maven/OpenRewrite recipe:
- com.synexia.rewrite.M3LuceneCompositeInteropRecipe
- synexia-openrewrite-recipes/src/main/resources/com/synexia/rewrite/m3-lucene-composite-interop/manifest.tsv

Verification gate (pending): run the Lucene core module's Gradle tests
including TestM3CompositeCharSequenceInterop, full analysis regression and
M3JDK21 String runtime comparisons. No benchmark gain or JNI integration
is claimed in this test-only change.
