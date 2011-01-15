/*
 * Created on Dec 24, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2011 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.IsNotEqualIgnoringCase.isNotEqual;
import static org.fest.assertions.test.CharArrayFactory.array;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.TestData.someInfo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link Strings#assertEqualsIgnoringCase(AssertionInfo, String, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class Strings_assertEqualsIgnoringCase_Test {

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Strings strings;

  @Before public void setUp() {
    failures = spy(new Failures());
    strings = new Strings(failures);
  }

  @Test public void should_fail_if_actual_is_null_and_expected_is_not() {
    AssertionInfo info = someInfo();
    try {
      strings.assertEqualsIgnoringCase(info, null, "Luke");
    } catch (AssertionError e) {
      shouldHaveFailedIfStringsAreNotEqual(info, null, "Luke");
      return;
    }
    fail("expected AssertionError not thrown");
  }

  @Test public void should_fail_if_both_Strings_are_not_equal_regardless_of_case() {
    AssertionInfo info = someInfo();
    try {
      strings.assertEqualsIgnoringCase(info, "Yoda", "Luke");
    } catch (AssertionError e) {
      shouldHaveFailedIfStringsAreNotEqual(info, "Yoda", "Luke");
      return;
    }
    fail("expected AssertionError not thrown");
  }

  private void shouldHaveFailedIfStringsAreNotEqual(AssertionInfo info, String actual, String expected) {
    verify(failures).failure(info, isNotEqual(actual, expected));
  }

  @Test public void should_pass_if_both_Strings_are_null() {
    strings.assertEqualsIgnoringCase(someInfo(), null, null);
  }

  @Test public void should_pass_if_both_Strings_are_the_same() {
    String s = "Yoda";
    strings.assertEqualsIgnoringCase(someInfo(), s, s);
  }

  @Test public void should_pass_if_both_Strings_are_equal_but_not_same() {
    strings.assertEqualsIgnoringCase(someInfo(), "Yoda", new String(array('Y', 'o', 'd', 'a')));
  }

  @Test public void should_pass_if_both_Strings_are_equal_ignoring_case() {
    strings.assertEqualsIgnoringCase(someInfo(), "Yoda", "YODA");
  }
}