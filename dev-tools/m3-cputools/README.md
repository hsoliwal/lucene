# M3 composite-text receiver: hsoliwal/lucene

This is an additive candidate from the canonical `hsoliwal/com.synexia` recipe owner.
It does not establish whole-project, M3JDK, performance or canonical-promotion success.

Recipe: `com.synexia.m3.LuceneCompositeReader`. Receiving base: `1169cd2007d37c1e5354fdccf9aa7e4eef93d21a`.
Canonical task: `synexia-openrewrite-recipes/recipes/cputools-native-20261014`.
Public String entrypoints remain present. No alternate text/interner owner is introduced.

The original Gradle build remains product authority.
This bounded Maven POM is a review/control plane only. It invokes the existing Synexia
source-seal owner; it never downloads, copies or forks a rewrite implementation here.
Supply a locally available, reviewed Synexia recipe checkout:

```sh
mvn -o -f dev-tools/m3-cputools/pom.xml -Dsynexia.recipe.home=/path/to/com.synexia verify
```

That command performs an exact hash/fixed-point check, not a product build/test.
It was not executed locally because Maven was unavailable. For source-seal application,
run the canonical Synexia `receive.py --kind lucene --target /path/to/lucene --mode apply`;
all target/template hashes are checked before the first serial per-file write.

## Focused proof

`M3ReaderBaselineCheck.java` compares the exact before and candidate String behavior in
separate JVMs, including exceptions/messages and destination-array state. The canonical
verifier uses `-XX:-OmitStackTraceInFastThrow` to avoid history-dependent missing messages.
`M3TextReadersCheck.java` tests immutable composites whose `toString` and `subSequence`
throw, with random UTF-16, supplementary characters, unpaired surrogates and split boundaries.
The focused Java 21 checks passed. Full Gradle/analyzer/M3JDK-runtime testing remains unexecuted.

`M3TextReaders.reader(CharSequence)` returns the existing reusable reader; the caller must
keep the supplied sequence immutable while it is consumed. String fast paths and the old
reader's EOF/close behavior remain unchanged. This helper does not silently rewrite every
Analyzer call site or change Lucene index formats.
