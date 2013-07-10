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
package com.danhaywood.java.testsupport.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mvel2.MVEL;

public abstract class HamcrestMatchers<T> {

    private HamcrestMatchers() {
    }

    public static Matcher<Object> navigatedFrom(final Object context, final String expression) {
        return new BaseMatcher<Object>() {

            @Override
            public boolean matches(Object item) {
                Object eval = eval(context, expression);
                return nullSafeEquals(item, eval);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(
                        eval(context, expression) + " (" + expression + ")");
            }

            private boolean nullSafeEquals(Object item, Object eval) {
                if (item == null && eval == null) {
                    return true;
                }
                if (item == null || eval == null) {
                    return false;
                }
                return item.equals(eval);
            }

            private Object eval(final Object context, final String expression) {
                try {
                    return MVEL.eval(expression, context);
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }

}
