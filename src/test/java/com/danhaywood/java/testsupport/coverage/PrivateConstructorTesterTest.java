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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.danhaywood.java.testsupport.coverage.PrivateConstructorTester;

import org.junit.Test;

public class PrivateConstructorTesterTest {

	public static class Constants {
		private static boolean called;
		private Constants() {
			// this is where we want some coverage!
			called = true;
		}
		
		public final static int FOO = 1;
		public final static int BAR = 1;
	}
	
	@Test
	public void invokeConstructor() throws Exception {
		assertThat(Constants.called, is(false));
		new PrivateConstructorTester(Constants.class).exercise();
		assertThat(Constants.called, is(true));
	}
	
}
