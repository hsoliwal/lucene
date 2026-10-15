<!--
    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with
    this work for additional information regarding copyright ownership.
    The ASF licenses this file to You under the Apache License, Version 2.0
    the "License"); you may not use this file except in compliance with
    the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
-->

# Apache Lucene

![Lucene Logo](https://lucene.apache.org/theme/images/lucene/lucene_logo_green_300.png?v=0e493d7a)

Apache Lucene is a high-performance, full-featured text search engine library
written in Java.

[![Build Status](https://ci-builds.apache.org/job/Lucene/job/Lucene-Artifacts-main/badge/icon?subject=Lucene)](https://ci-builds.apache.org/job/Lucene/job/Lucene-Artifacts-main/)
[![Revved up by Develocity](https://img.shields.io/badge/Revved%20up%20by-Develocity-06A0CE?logo=Gradle&labelColor=02303A)](https://develocity.apache.org/scans?search.buildToolType=gradle&search.rootProjectNames=lucene-root)

<!-- fork-homepage-guide:begin -->
## Explore this fork

Lucene is an embeddable Java search library: your application chooses how to
ingest documents, analyze text, store an index and expose search. This
[hsoliwal fork](https://github.com/hsoliwal/lucene) retains
[Apache Lucene](https://github.com/apache/lucene)'s project identity, licenses and
Gradle product build. Upstream release links and badges below do not certify this fork.

At reviewed baseline
[`dfde02b`](https://github.com/hsoliwal/lucene/commit/dfde02b3aed658baa9e0c449e17aa1e40bfaff86),
the fork includes a case-sensitive String-hash path in `CharArrayMap`, composite
UTF-16 interoperability tests, and
[M3TextReaders](lucene/core/src/java/org/apache/lucene/analysis/M3TextReaders.java)
for borrowing stable immutable text through Lucene's existing reader owner.
Those are bounded source additions, not an index-format redesign, universal
analyzer optimization or measured speedup. The [receiver guide](dev-tools/m3-cputools/README.md)
records recipe ownership and remaining runtime gates; earlier passing jobs do not
qualify a newer commit.

### Search concepts at a glance

| Term | What it means | Common mistake to avoid |
| --- | --- | --- |
| Document / field | One indexed item and its named values | Assuming every field is both searchable and retrievable |
| Analyzer / token stream | Converts text into terms, positions and offsets | Searching raw text as if it were already an analyzed term |
| Inverted index | Maps terms to matching documents | Treating Lucene as a database server with an included UI |
| IndexWriter | Adds, updates and deletes indexed documents | Reusing a demonstration's create-mode path for important data |
| DirectoryReader / IndexSearcher | Reads a view of an index and executes queries | Assuming an already-open reader sees every later write |
| Stored field | A value retained for result retrieval | Confusing it with indexed terms or doc values |

### Quickstart: one executable index-and-search test

Install **JDK 25** and use the repository's Gradle wrapper. It downloads the pinned
Gradle distribution on first use; broader checks also need the contributor
prerequisites, including Python 3 and Perl.

```sh
git clone https://github.com/hsoliwal/lucene.git
cd lucene
java -version
git rev-parse HEAD
./gradlew -p lucene/core assemble
./gradlew -p lucene/core test --tests org.apache.lucene.TestDemo
```

Use `gradlew.bat` on Windows. The core JAR is under `lucene/core/build/libs/`.
Open [TestDemo](lucene/core/src/test/org/apache/lucene/TestDemo.java) alongside the
command: it creates a temporary directory, indexes a stored text field, closes
the writer, opens a reader, searches, and checks retrieved text.

### Worked search: why a term and a phrase are different

The demo indexes text beginning `This is the text to be indexed.` with
`StandardAnalyzer`. `new TermQuery(new Term("fieldname", "text"))` looks up one
indexed term. `new PhraseQuery("fieldname", "to", "be")` additionally constrains
term order and positions. The test expects one matching document for each and
reads the original stored value back. A query term is not automatically analyzed
by `TermQuery`; use the same analysis rules for a real query pipeline.

Those outcomes are assertions in the linked source, not a test run performed by
this homepage update. Continue with the [core API overview](lucene/core/src/java/overview.html)
and [IndexFiles / SearchFiles examples](lucene/demo/src/java/org/apache/lucene/demo/).
Use a disposable index directory: `IndexFiles` defaults to creating a new index.

### Developer cheat sheet

| Intent | Command or reference |
| --- | --- |
| Build only core artifacts | `./gradlew -p lucene/core assemble` |
| Run the fork's reader regression | `./gradlew -p lucene/core test --tests org.apache.lucene.analysis.TestM3TextReaders` |
| Run core validation | `./gradlew -p lucene/core check` |
| Run repository-wide validation | `./gradlew check` |
| Build API documentation | `./gradlew documentation` → `lucene/documentation/build/site` |
| Reproduce a randomized failure | [Testing guide](help/tests.md): retain the failing seed and full reproduction command |
| Review a version upgrade | [Migration guide](lucene/MIGRATE.md) |

The fork's borrowed text must remain immutable and alive while the reader uses
it. Preserve Lucene's UTF-8 term encoding, UTF-16 offset semantics and mutable
term contracts; the [composite contract](lucene/core/M3-COMPOSITE-CHARSEQUENCE.md)
explains why arbitrary `CharSequence` inputs cannot be blindly rewritten.
Before contributing, read [CONTRIBUTING.md](CONTRIBUTING.md) and the
[AI policy](AI_POLICY.md), including human review and ownership requirements.

<!-- fork-homepage-guide:end -->

## Online Documentation

This README file only contains basic setup instructions. For more
comprehensive documentation, visit:

- Latest Releases: <https://lucene.apache.org/core/documentation.html>
- Nightly: <https://ci-builds.apache.org/job/Lucene/job/Lucene-Artifacts-main/javadoc/>
- New contributors should start by reading [Contributing Guide](./CONTRIBUTING.md)
- Build System Documentation: [help/](./help/)
- Migration Guide: [lucene/MIGRATE.md](./lucene/MIGRATE.md)

## Building

### Basic steps

1. Install JDK 25 using your package manager or download manually from
[OpenJDK](https://jdk.java.net/),
[Adoptium](https://adoptium.net/temurin/releases),
[Azul](https://www.azul.com/downloads/),
[Oracle](https://www.oracle.com/java/technologies/downloads/) or any other JDK provider.
2. Clone Lucene's git repository (or download the source distribution).
3. Run gradle launcher script (`gradlew`).

We'll assume that you know how to get and set up the JDK - if you don't, then we suggest starting at <https://jdk.java.net/> and learning more about Java, before returning to this README.

### Build and test a focused change

Run commands from the checkout root using the checked-in wrapper (`./gradlew`
on Unix-like systems, or `gradlew.bat` on Windows), rather than a separately
installed Gradle. The wrapper downloads the required Gradle version on first use.
Some validation tasks, including those reached by `check`, also require Perl and
Python 3; see the [contributor prerequisites](./CONTRIBUTING.md#pre-requisites).

Start with a single module or test before running the full verification suite:

```bash
# Assemble the core JAR; output is under lucene/core/build/libs/
./gradlew -p lucene/core assemble

# Run the core indexing-and-search example as a test
./gradlew -p lucene/core test --tests org.apache.lucene.TestDemo

# Run core tests and validation tasks
./gradlew -p lucene/core check

# Run repository-wide tests and validation tasks
./gradlew check
```

A passing focused test is not a replacement for repository-wide validation.
Some expensive or externally dependent checks are omitted locally and run in CI;
see [Testing](./help/tests.md) for filtering, randomization seeds, and reruns.

To build API documentation for this checkout, run `./gradlew documentation`;
the generated site is under `lucene/documentation/build/site`. More build targets
and output locations are listed in the [workflow guide](./help/workflow.md).

## Exploring the source

- [Core API overview](./lucene/core/src/java/overview.html): an introduction to
  indexing and searching with Lucene.
- [Core TestDemo](./lucene/core/src/test/org/apache/lucene/TestDemo.java): a small,
  executable example that writes documents, searches terms and phrases, and reads
  stored fields. The focused test command above runs this example.
- [IndexFiles](./lucene/demo/src/java/org/apache/lucene/demo/IndexFiles.java) and
  [SearchFiles](./lucene/demo/src/java/org/apache/lucene/demo/SearchFiles.java):
  command-line examples for indexing files and searching the resulting index.
  Use a disposable index directory when exploring: `IndexFiles` defaults to
  creating a new index, replacing previously indexed documents at that path.
- [Migration guide](./lucene/MIGRATE.md): API and behavior changes to review when
  moving between Lucene versions.

## Contributing

Bug fixes, improvements and new features are always welcome!
Please review the [Contributing to Lucene
Guide](./CONTRIBUTING.md) for information on
contributing. AI-assisted contributions must also follow the [AI Policy](./AI_POLICY.md),
including its human review and ownership requirements.

- Additional Developer Documentation: [dev-docs/](./dev-docs/)

## Discussion and Support

- [Users Mailing List](https://lucene.apache.org/core/discussion.html#java-user-list-java-userluceneapacheorg)
- [Developers Mailing List](https://lucene.apache.org/core/discussion.html#developer-lists)
- IRC: `#lucene` and `#lucene-dev` on freenode.net
