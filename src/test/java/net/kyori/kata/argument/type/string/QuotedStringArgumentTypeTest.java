/*
 * This file is part of kata, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.kata.argument.type.string;

import net.kyori.kata.argument.exception.StringArgumentException;
import net.kyori.kata.context.CommandContext;
import net.kyori.kata.exception.CommandException;
import net.kyori.string.StringReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuotedStringArgumentTypeTest {
  @Test
  void testParse() throws CommandException {
    assertEquals("foo bar", StringArgumentType.quoted().parse(CommandContext.empty(), StringReader.create("\"foo bar\"")));
    assertEquals("foo bar: \"", StringArgumentType.quoted().parse(CommandContext.empty(), StringReader.create("\"foo bar: \\\"\"")));
  }

  @Test
  void testParse_missingStartOfQuote() {
    assertThrows(StringArgumentException.ExpectedStartOfQuote.class, () -> StringArgumentType.quoted().parse(CommandContext.empty(), StringReader.create("foo")));
  }

  @Test
  void testParse_invalidEscape() {
    assertThrows(StringArgumentException.InvalidEscapeSequence.class, () -> StringArgumentType.quoted().parse(CommandContext.empty(), StringReader.create("\"\\foo")));
  }

  @Test
  void testParse_missingEndOfQuote() {
    assertThrows(StringArgumentException.ExpectedEndOfQuote.class, () -> StringArgumentType.quoted().parse(CommandContext.empty(), StringReader.create("\"foo bar")));
  }
}
