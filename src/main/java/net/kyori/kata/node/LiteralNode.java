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
package net.kyori.kata.node;

import net.kyori.kata.context.CommandContext;
import net.kyori.kata.context.CommandStack;
import net.kyori.kata.exception.CommandException;
import net.kyori.string.StringRange;
import net.kyori.string.StringReader;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A literal node.
 */
public interface LiteralNode extends ChildNode {
  @Override
  @NonNull LiteralNode add(final @NonNull ChildNode node);

  @Override
  default boolean parse(final CommandStack.@NonNull Builder stack, final @NonNull CommandContext context, final @NonNull StringReader reader) throws CommandException {
    final String name = this.name();
    if(reader.readable(name.length())) {
      final int start = reader.index();
      final int end = start + name.length();
      if(reader.string(StringRange.between(start, end)).equals(name)) {
        reader.skip(end - start);
        if(!reader.readable() || reader.peek() == ' ') {
          stack.literal(StringRange.between(start, end));
          return true;
        } else {
          reader.index(start);
        }
      }
    }
    return false;
  }

  /**
   * A literal node builder.
   */
  interface Builder extends ChildNode.Builder<LiteralNode, Builder> {
  }
}
