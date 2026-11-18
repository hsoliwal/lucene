# Apache Lucene agent instructions

## Clean output rule (owner directive, 2026-11-14) - read first

This rule overrides any older text in this file.

- This fork is clean output: one coherent Apache Lucene repository. M3 work lives
  inside the project's own classes and packages, written the way the project
  writes its code. It is not stamped across folders.
- Do not add `m3/`, `.m3/`, `docs/m3/` or `tools/m3*` folders.
- Do not add any of the following. They belong in `hsoliwal/com.synexia`, the
  convergence workspace:
  - qualification bundles;
  - evidence, receipts and benchmark tables;
  - work orders;
  - donor copies;
  - recipe sources or recipe resources.
- Do not add parallel `M3`-prefixed types or `*.m3` packages where an existing
  class owns the behaviour. Change that class in place, keep it internal unless
  the owner admits new API, and follow the project's naming.
- A pull request here contains only the product change, plus its tests in the
  project's own test layout. Put the proof material in the paired `com.synexia`
  pull request and link to it.
- This fork's merged pull-request history shows the intended direction.
- Collection forks: arrayify and leetcodify the implementations here, then consolidate them into M3JDK (hsoliwal/M3jdk21), which owns the final implementations.


## Priority 0 — universal additive superset, full-history merge and last-checked rule (2026-11-18)

Preserve the **backward-compatible optimized superset** of all applicable accepted upstream/fork capabilities, source/binary/public APIs, behavior, fixes, platform/runtime contracts and previous qualified optimizations. Compare candidate algorithms and branches on differential correctness, worst-case complexity, CPU/heap/native cost, scalability and maintainability; choose the best **proven** implementation, allowing compatible additive API extensions but no silent removal. Before merge, inspect the earliest reachable ancestors across every relevant branch/tag, old merged/closed/reverted PR, merge base and both merge parents, to identify bad commits, lost code, removed APIs and lost optimizations; never rebase `develop`. Persist last-checked date, precise ref/head SHA, scope, oldest ancestor checked, last verified SHA, branch/PR coverage, loss findings and PENDING/PARTIAL/VERIFIED/HOLD in the canonical Synexia checkpoint register. An observed HEAD is **not** an exhaustive history audit. First-party separable new work prefers Apache-2.0; preserve original upstream and derivative license/NOTICE obligations. LLM atomization/patternization does not by itself change copyright or code licensing. Source-changing work follows canonical Synexia Maven/OpenRewrite recipe/historical-proof gates; public product forks remain clean postimage-only receivers. Policy and initial explicitly **PENDING** repository inventory: [Synexia PR #18764](https://github.com/hsoliwal/com.synexia/pull/18764).


## MOP SDK — source-bound atoms and known/unknown algorithm registry (2026-11-18)

Read the [canonical Synexia MOP SDK invariant](https://github.com/hsoliwal/com.synexia/blob/develop/docs/M3-SCALE/invariants/SYNEXIA-MOP-SDK-BIDIRECTIONAL-LATTICE-1.md) and existing universal API atom/pattern policy before source changes. Decompose from repository -> project/module -> package -> file -> declarations -> meaningful behavioral atoms, sealing public API/ABI, state, effects, order, concurrency, native and resource contracts. Match `KNOWN_ALGORITHM`/`KNOWN_PATTERN` only to versioned, role- and applicability-qualified catalogue entries with recorded evidence. Otherwise preserve the original and create source-bound machine-readable `UNKNOWN_ALGORITHM`, `UNKNOWN_PATTERN` or `UNPROVEN_MATCH` residue with atom/owner identity, dependency/effect context, provenance, and verification status; link valid documentation or existing supported annotations, never invent noncompiling markers. Recompose and verify only the affected dependency closure upward; independent file preparation may be parallel, canonical mutation and proof remain serial. Preserve the strongest demonstrated **compatible additive API superset** after full available ancestry/branch/PR audit, and preserve inherited licenses and notices. Code/proof/recipe owners stay in Synexia, receiving products accept only source-sealed qualified postimages. This text does **not** certify a full-history scan, OpenRewrite run, compilation, tests or runtime.
