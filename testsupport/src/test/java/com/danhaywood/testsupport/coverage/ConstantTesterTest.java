package com.danhaywood.testsupport.coverage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConstantTesterTest {

	public static class Constants {
		private static boolean called;
		private Constants() {
			// this is where we want some coverage!
			called = true;
		}
		
		public final static int FOO = 1;
		public final static int bar = 1;
	}
	
	@Test
	public void invokeConstructor() throws Exception {
		assertThat(Constants.called, is(false));
		new ConstantTester(Constants.class).invokeConstructor();
		assertThat(Constants.called, is(true));
	}
	
}
