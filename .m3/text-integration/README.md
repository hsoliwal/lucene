# Lucene M3 text receiver

Draft candidate: source base `1169cd2007d37c1e5354fdccf9aa7e4eef93d21a`.
Canonical recipe/test owner: [Synexia PR 11438](https://github.com/hsoliwal/com.synexia/pull/11438). Runtime postimage SHA-256: `9c1a3ab23e51853873d267204c4b89a86a34915d5b1526930ef499dfc0a5efe1`.

## Implemented

CharArrayMap's private CharSequence hash method reuses String.hashCode for actual String inputs in case-sensitive mode. Original null behavior, generic CharSequence loop, char[] path, case-insensitive code-point algorithm, equality and public APIs remain unchanged. No internal M3String import, new interner, byte/index-format change or production speedup claim is introduced.

## Recipe and source custody

`source-packet/` is a **generated immutable source handoff**, not a separately maintained downstream recipe. Every source is pinned by origin commit/path/SHA-256 in source-packet.tsv and checked before compilation or execution. Master source stays in Synexia. Fix canonical source/templates, regenerate and replay; never edit receiving snapshots or generated production output as an independent master. The frozen-source delivery exception is explicit for review.

The original private-Synexia download assumption produced an HTTP404 in the companion OpenNLP CI run. The canonical `public-handoff/manifest.tsv` recipe at Synexia commit `b392d2e2ce03997f01231cb7208a887c73d77731` now generates readable source snapshots and the receiver. No credentials, network source fetch or access-policy change is needed. This supersedes initial download/offline-checkout instructions and the old receipt's delivery mode; runtime source hashes are unchanged.

```sh
mvn -B -ntp -f .m3/text-integration/pom.xml verify
# Exact admitted preimage only, exclusive checkout:
mvn -B -ntp -f .m3/text-integration/pom.xml -Dm3.mode=apply verify
# Search the task catalogue:
grep -i 'hash' .m3/text-integration/source-packet/catalogue.jsonl
```

## Evidence and remaining gates

Original and revised receivers each passed locally with **191,090 checks**: source/template drift refusal, cancellation, exact replay/fixed point, actual compiler-extracted methods, both case modes, every single UTF-16 unit, randomized Strings/builders and hostile generic CharSequences. All other class bytes remain unchanged after extracting the admitted method.

The current Lucene product build requires JDK25. This bounded JDK21 Maven control plane does not lower or replace the Gradle requirement, or prove M3JDK21 compatibility. Local Maven was absent; direct scripts ran. Actual CI results must be checked. Full product Gradle/lint/JUnit, canonical OpenRewrite admission, matched M3JDK runtime and representative performance qualification remain separate gates. The char[] hot path is unchanged.

Not implemented: shared sentence ownership, immutable-term/encoding caches, writer/field/generation handle bridges, M3JDK21 backport/lowering or whole-repository coverage. The15-entry catalogue records selected and refused/deferred donor shapes. No editorial/submission implementation or GPL OpenJDK source was copied.
