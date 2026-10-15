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
