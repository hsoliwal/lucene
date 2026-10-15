/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.tests.analysis.MockAnalyzer;
import org.apache.lucene.tests.util.LuceneTestCase;
import org.apache.lucene.tests.util.TestUtil;

/** Runs borrowed composite text through a real Analyzer and its reusable TokenStream. */
public class TestM3TextReaders extends LuceneTestCase {
  private record Composite(String left, String right) implements CharSequence {
    @Override
    public int length() {
      return left.length() + right.length();
    }

    @Override
    public char charAt(int index) {
      Objects.checkIndex(index, length());
      return index < left.length() ? left.charAt(index) : right.charAt(index - left.length());
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      throw new AssertionError("Do not slice the caller's text");
    }

    @Override
    public String toString() {
      throw new AssertionError("Do not flatten the caller's text");
    }
  }

  private record Token(String term, int start, int end, int increment, int length, String type) {}

  private record Result(List<Token> tokens, int endOffset, int endIncrement) {}

  private static Result consume(TokenStream stream) throws IOException {
    try (stream) {
      var term = stream.addAttribute(CharTermAttribute.class);
      var offset = stream.addAttribute(OffsetAttribute.class);
      var increment = stream.addAttribute(PositionIncrementAttribute.class);
      var length = stream.addAttribute(PositionLengthAttribute.class);
      var type = stream.addAttribute(TypeAttribute.class);
      List<Token> tokens = new ArrayList<>();
      stream.reset();
      while (stream.incrementToken()) {
        tokens.add(
            new Token(
                term.toString(),
                offset.startOffset(),
                offset.endOffset(),
                increment.getPositionIncrement(),
                length.getPositionLength(),
                type.type()));
      }
      stream.end();
      return new Result(List.copyOf(tokens), offset.endOffset(), increment.getPositionIncrement());
    }
  }

  public void testAnalyzerAcrossCompositeBoundaries() throws Exception {
    try (Analyzer analyzer = new MockAnalyzer(random())) {
      for (int trial = 0; trial < atLeast(500); trial++) {
        String text = TestUtil.randomUnicodeString(random());
        int split = random().nextInt(text.length() + 1);
        Result expected = consume(analyzer.tokenStream("text", text));
        try (Reader source =
            M3TextReaders.reader(new Composite(text.substring(0, split), text.substring(split)))) {
          assertEquals(expected, consume(analyzer.tokenStream("text", source)));
        }
      }
      for (String text : List.of("", "abc", "A\ud83d\ude00B", "\ud800x\udc00", "x\u0000 y\n")) {
        for (int split = 0; split <= text.length(); split++) {
          Result expected = consume(analyzer.tokenStream("text", text));
          try (Reader source =
              M3TextReaders.reader(new Composite(text.substring(0, split), text.substring(split)))) {
            assertEquals(expected, consume(analyzer.tokenStream("text", source)));
          }
        }
      }
    }
  }
}
