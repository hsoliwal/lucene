<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# M3 composite-text receiver: hsoliwal/lucene

Canonical recipe ownership: `hsoliwal/com.synexia`, PR11556. This receiving
pass continues PR7; it is not whole-project or whole-CPUTOOLS completion.

The original Gradle build remains the product authority. The following Maven
entrypoint checks the exact postimage of `com.synexia.m3.LuceneRuntimePass` through
the existing Synexia manifest engine, not a duplicate transformer:

```sh
mvn -f dev-tools/m3-cputools/pom.xml -Dsynexia.recipe.home=/path/to/com.synexia verify
```

This control check is not a product build or runtime proof. For explicit serial application:

```sh
python3 /path/to/com.synexia/synexia-openrewrite-recipes/recipes/cputools-runtime-20261015/verify.py --kind lucene --target /path/to/lucene --apply
```

The earlier `cputools-native-20261014` recipe is historical; its exact old postimage
is deliberately not the current postimage. Apply the initial recipe then this subsequent
pass on their source-pinned preimages; do not weaken a hash guard to admit foreign changes.
Fix the canonical payload, qualify, and rerun instead of hand-patching receiving output.
Existing String APIs, immutable caller-owned text, and original whitespace/UTF-16 behavior
remain authoritative. No alternate interner or runtime payload store is introduced.

## Runtime gates

The previous PR head passed existing JDK25 Windows, Linux and ARM test jobs; formatting
checks failed. This pass fixes the exact reported formatting in recipe payloads and adds
`TestM3TextReaders`, a real Analyzer/TokenStream differential test. It checks terms,
UTF-16 offsets, positions, type and final offsets with no composite flattening.
Historical standalone Reader checks remain. A new successful CI run is required for the
new test and formatting changes; earlier passing jobs do not prove this new head.
No index-format change, all-analyzer rewrite or real M3JDK OS-interning qualification is claimed.
