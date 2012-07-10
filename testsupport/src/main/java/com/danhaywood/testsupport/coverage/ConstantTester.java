package com.danhaywood.testsupport.coverage;

import java.lang.reflect.Constructor;

public final class ConstantTester {

    private Class<?> cls;

	public ConstantTester(Class<?> cls) {
		this.cls = cls;
	}

	public void invokeConstructor() throws Exception {
        final Constructor<?> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
