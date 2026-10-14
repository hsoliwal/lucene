# Lucene M3 text receiver

Draft candidate from `hsoliwal/lucene@1169cd2007d37c1e5354fdccf9aa7e4eef93d21a`.
Master recipe/tests: [Synexia PR 11438](https://github.com/hsoliwal/com.synexia/pull/11438), pinned commit `e3af85dac71bfac047163286ae42bda326df549f`.

## Generated change

Inside CharArrayMap's private CharSequence hash method, actual String inputs in case-sensitive mode now reuse String.hashCode(). The existing null check, generic CharSequence loop, char[] path, case-insensitive code-point lowering, equality and public API remain unchanged. This lets String-owned hash caching participate, including a separately qualified M3-backed runtime, without importing an internal M3String type or replacing Lucene byte/term/index structures.

The runtime source and full postimage template are byte-identical and SHA-256 sealed; original licensed source is retained as a preimage fixture. Reusable recipe/test implementation stays in Synexia. `source-packet.tsv` pins each ref/path/hash; `receiver.sh` verifies source bytes before compiling/executing. An offline checkout cannot bypass those checks.

```sh
mvn -B -ntp -f .m3/text-integration/pom.xml verify
# Apply only to the exact admitted preimage in an exclusive checkout:
mvn -B -ntp -f .m3/text-integration/pom.xml -Dm3.mode=apply verify
# Offline source handoff, same checksum enforcement:
M3_CANONICAL_CHECKOUT=/path/to/synexia bash .m3/text-integration/receiver.sh . verify
# After receiver execution, search the canonical task catalogue:
grep -i 'hash' .m3/text-integration/target/canonical/catalogue.jsonl
```

## Evidence and limits

Local direct-script qualification passed 191,090 checks: source/template drift refusal, cancellation, deterministic replay/fixed point, actual compiler-extracted hash methods over both case modes, every single UTF-16 unit, random String/StringBuilder inputs and hostile CharSequences whose hashCode/toString must never be called. The harness checks that all other source bytes remain unchanged after removing the admitted method. Null and supplementary/malformed UTF-16 cases are included.

These are isolated method checks, not a full Lucene Gradle/JUnit run, end-to-end search test or performance benchmark. The current main branch requires JDK25 for its product build; this JDK21 Maven review/control plane does not lower that requirement or prove M3JDK21 compatibility. The original Gradle product build remains authoritative.

Maven was absent in the authoring environment; the receiver/qualification scripts were executed directly. CI requests the Maven lifecycle; missing/skipped/zero-job CI remains unverified. Full product compilation/lint/JUnit, OpenRewrite scheduler/canonical task admission, matched M3JDK runtime and representative cold/warm benchmarks are pending gates. Keep draft. The char[] hot path is unchanged and no end-to-end speedup is claimed.

The 15-category master catalogue preserves donor comparison and refusal decisions. Existing Synexia AGENTS.md and recipe-mastery invariant remain normative. Fix canonical recipes/templates and rerun rather than patching generated sources.

Not implemented: shared sentence ownership, immutable-term or encoding caches, writer/field/generation handle bridges, M3JDK21 backport/lowering or repository-wide file coverage. Mutable buffers, binary terms, unsigned UTF-8 ordering and malformed-surrogate encoding policies remain untouched.
