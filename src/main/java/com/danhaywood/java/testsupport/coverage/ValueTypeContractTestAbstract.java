/*
 *  Copyright 2010-2013 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.danhaywood.java.testsupport.coverage;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static com.danhaywood.java.testsupport.matchers.HamcrestMatchers.*;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

/**
 * Contract test for value types ({@link #equals(Object) equals} and
 * {@link #hashCode() hashCode}), and also {@link Comparable#compareTo(Object) compareTo} for
 * any value types that also are {@link Comparable}
 */
public abstract class ValueTypeContractTestAbstract<T> {

    @Before
    public void setUp() throws Exception {
        assertSizeAtLeast(getObjectsWithSameValue(), 2);
        assertSizeAtLeast(getObjectsWithDifferentValue(), 1);
    }

    private void assertSizeAtLeast(final List<T> objects, final int i) {
        assertThat(objects, is(notNullValue()));
        assertThat(objects.size(), is(greaterThan(i - 1)));
    }

    @Test
    public void notEqualToNull() throws Exception {
        for (final T o1 : getObjectsWithSameValue()) {
            assertThat(o1.equals(null), is(false));
        }
        for (final T o1 : getObjectsWithDifferentValue()) {
            assertThat(o1.equals(null), is(false));
        }
    }

    @Test
    public void reflexiveAndSymmetric() throws Exception {
        for (final T o1 : getObjectsWithSameValue()) {
            for (final T o2 : getObjectsWithSameValue()) {
                assertThat(o1.equals(o2), is(true));
                assertThat(o2.equals(o1), is(true));
                assertThat(o1.hashCode(), is(equalTo(o2.hashCode())));
            }
        }
    }

    @Test
    public void notEqual() throws Exception {
        for (final T o1 : getObjectsWithSameValue()) {
            for (final T o2 : getObjectsWithDifferentValue()) {
                assertThat(o1.equals(o2), is(false));
                assertThat(o2.equals(o1), is(false));
            }
        }
    }

    @Test
    public void transitiveWhenEqual() throws Exception {
        for (final T o1 : getObjectsWithSameValue()) {
            for (final T o2 : getObjectsWithSameValue()) {
                for (final Object o3 : getObjectsWithSameValue()) {
                    assertThat(o1.equals(o2), is(true));
                    assertThat(o2.equals(o3), is(true));
                    assertThat(o1.equals(o3), is(true));
                }
            }
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void comparableEquivalence() throws Exception {
        for (final T o1 : getObjectsWithSameValue()) {
            assumeThat(o1 instanceof Comparable, is(true));
            Comparable c1 = (Comparable)o1;

            for (final T o2 : getObjectsWithSameValue()) {
                assumeThat(o2 instanceof Comparable, is(true));
                Comparable c2 = (Comparable)o2;
                
                assertThat(c1.compareTo(c2), is(0));
                assertThat(c2.compareTo(c1), is(0));
            }
            
            for (final T o2 : getObjectsWithDifferentValue()) {
                assumeThat(o2 instanceof Comparable, is(true));
                Comparable c2 = (Comparable)o2;
                
                final int x = c1.compareTo(c2);
                final int y = c2.compareTo(c1);
                assertThat(x, is(not(0)));

                assertThat(x, is(-y));
            }
        }
    }

    private static <T extends Comparable<T>> Matcher<T> greaterThan(final T i) {
        return new TypeSafeMatcher<T>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("greater than ").appendValue(i);
            }

            @Override
            protected boolean matchesSafely(T item) {
                return item.compareTo(i) > 0;
            }
        };
    }


    protected abstract List<T> getObjectsWithSameValue();

    protected abstract List<T> getObjectsWithDifferentValue();

}
