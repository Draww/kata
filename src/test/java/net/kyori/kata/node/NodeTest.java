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

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static net.kyori.kata.node.Node.literal;
import static net.kyori.kata.node.Node.root;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NodeTest {
  @Test
  void testChildren() {
    assertThat(root().children()).isEmpty();
    assertThat(
      root()
        .add(literal("foo"))
        .add(literal("foo"))
        .add(literal("bar"))
        .children()
    ).hasSize(2);
  }

  @Test
  void testAddRemove() {
    final RootNode root = root();
    assertThat(root.children()).isEmpty();
    root.add(literal("foo"));
    assertThat(root.children()).hasSize(1);
    root.remove("foo");
    assertThat(root.children()).isEmpty();
  }

  @Test
  void testAdd_replaceExecutable() {
    assertThrows(UnsupportedOperationException.class, () -> root()
      .add(literal("foo").executes(stack -> {
      }))
      .add(literal("foo").executes(stack -> {
      }))
    );
  }
}
