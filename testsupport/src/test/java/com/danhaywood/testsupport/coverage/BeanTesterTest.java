package com.danhaywood.testsupport.coverage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;

import com.danhaywood.testsupport.coverage.BeanTester.FilterSet;

public class BeanTesterTest {

	private BeanTester beanTester;
	private BeanSpy beanSpy;
	private BeanSpyExt beanSpyWithOtherClass;

	public static class BeanSpy {

		public enum MyEnum {
			ONE,TWO,THREE
		}
		
		private boolean z;
		private byte b;
		private short s;
		private int i;
		private long l;
		private float f;
		private double d;
		private char c;

		private Boolean boolWrapper;
		private Byte byteWrapper;
		private Short shortWrapper;
		private Integer intWrapper;
		private Long longWrapper;
		private Float floatWrapper;
		private Double doubleWrapper;
		private Character charWrapper;

		private String string;
		private Pattern pattern;
		private File file;
		private BigDecimal bigDecimal;
		private BigInteger bigInteger;
		private java.util.Date javaUtilDate;
		private Timestamp timestamp;
		private List<?> list;
		private Iterable<?> iterable;
		private Collection<?> collection;
		private Set<?> set;
		
		private MyEnum myEnum;

		Set<String> methodInvocations = new HashSet<String>();
		boolean broken;
		
		public boolean isZ() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return z;
		}
		public void setZ(boolean z) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
				this.z = z;
		}
		public byte getB() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return b;
		}
		public void setB(byte b) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
				this.b = b;
		}
		public short getS() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return s;
		}
		public void setS(short s) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.s = s;
		}
		public int getI() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return i;
		}
		public void setI(int i) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.i = i;
		}
		public long getL() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return l;
		}
		public void setL(long l) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.l = l;
		}
		public float getF() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return f;
		}
		public void setF(float f) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.f = f;
		}
		public double getD() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return d;
		}
		public void setD(double d) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.d = d;
		}
		public char getC() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return c;
		}
		public void setC(char c) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.c = c;
		}

		public Boolean getBoolWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return boolWrapper;
		}
		public void setBoolWrapper(Boolean zWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.boolWrapper = zWrapper;
		}
		public Byte getByteWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return byteWrapper;
		}
		public void setByteWrapper(Byte bWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.byteWrapper = bWrapper;
		}
		public Short getShortWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return shortWrapper;
		}
		public void setShortWrapper(Short sv) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.shortWrapper = sv;
		}
		public Integer getIntWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return intWrapper;
		}
		public void setIntWrapper(Integer iWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.intWrapper = iWrapper;
		}
		public Long getLongWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return longWrapper;
		}
		public void setLongWrapper(Long lWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.longWrapper = lWrapper;
		}
		public Float getFloatWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return floatWrapper;
		}
		public void setFloatWrapper(Float fWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.floatWrapper = fWrapper;
		}
		public Double getDoubleWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return doubleWrapper;
		}
		public void setDoubleWrapper(Double dWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.doubleWrapper = dWrapper;
		}
		public Character getCharWrapper() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return charWrapper;
		}
		public void setCharWrapper(Character cWrapper) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.charWrapper = cWrapper;
		}
		public String getString() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return string;
		}
		public void setString(String string) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.string = string;
		}
		public Pattern getPattern() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return pattern;
		}
		public void setPattern(Pattern pattern) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.pattern = pattern;
		}
		public File getFile() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return file;
		}
		public void setFile(File file) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.file = file;
		}
		public BigDecimal getBigDecimal() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return bigDecimal;
		}
		public void setBigDecimal(BigDecimal bigDecimal) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.bigDecimal = bigDecimal;
		}
		public BigInteger getBigInteger() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return bigInteger;
		}
		public void setBigInteger(BigInteger bigInteger) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.bigInteger = bigInteger;
		}
		public java.util.Date getJavaUtilDate() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return javaUtilDate;
		}
		public void setJavaUtilDate(java.util.Date javaUtilDate) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.javaUtilDate = javaUtilDate;
		}
		public Timestamp getTimestamp() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return timestamp;
		}
		public void setTimestamp(Timestamp timestamp) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.timestamp = timestamp;
		}
		public List<?> getList() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return list;
		}
		public void setList(List<?> list) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.list = list;
		}
		public Iterable<?> getIterable() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return iterable;
		}
		public void setIterable(Iterable<?> iterable) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.iterable = iterable;
		}
		public Collection<?> getCollection() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return collection;
		}
		public void setCollection(Collection<?> collection) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.collection = collection;
		}
		public Set<?> getSet() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return set;
		}
		public void setSet(Set<?> set) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.set = set;
		}
		public MyEnum getMyEnum() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return myEnum;
		}
		public void setMyEnum(MyEnum myEnum) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.myEnum = myEnum;
		}
	}

	public static class BeanSpyExt extends BeanSpy {

		public static class OtherClass {
			public OtherClass(int x) {
				this.x = x;
			}
			int x;
		}
		
		private OtherClass otherClass;

		public OtherClass getOtherClass() {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			return otherClass;
		}
		public void setOtherClass(OtherClass otherClass) {
			methodInvocations.add(Thread.currentThread().getStackTrace()[1].getMethodName());
			if(!broken)
			this.otherClass = otherClass;
		}
	}

	private final int NUMBER_OF_GETTERS_AND_SETTERS = 56;

	@Before
	public void setUp() throws Exception {
		beanTester = new BeanTester();
		beanSpy = new BeanSpy();
		beanSpyWithOtherClass = new BeanSpyExt();
	}

	@Test
	public void happyCase() {
		beanTester.testAllSetters(beanSpy);
		assertThat(beanSpy.methodInvocations.size(), is(NUMBER_OF_GETTERS_AND_SETTERS)); // for each getter/setter
	}

	@Test
	public void includingOnly() {
		beanTester.exercise(beanSpy, FilterSet.includingOnly("z", "longWrapper"));
		assertThat(beanSpy.methodInvocations.size(), is(4)); // for each getter/setter
	}

	@Test
	public void excluding() {
		beanTester.exercise(beanSpy, FilterSet.excluding("z", "longWrapper"));
		assertThat(beanSpy.methodInvocations.size(), is(NUMBER_OF_GETTERS_AND_SETTERS - 4)); // for each getter/setter
	}

	@Test
	public void withOtherClass_fixtureDataSupplied() {
		beanTester.withFixture(BeanSpyExt.OtherClass.class, new BeanSpyExt.OtherClass(1), new BeanSpyExt.OtherClass(2));
		
		beanTester.exercise(beanSpyWithOtherClass, FilterSet.includingOnly("otherClass"));
		assertThat(beanSpyWithOtherClass.methodInvocations.size(), is(2)); // for each getter/setter
	}

	@Test(expected=AssertionFailedError.class)
	public void withOtherClass_noFixtureDataSupplied_atAll() {
		beanTester.exercise(beanSpyWithOtherClass, FilterSet.includingOnly("otherClass"));
	}

	@Test(expected=AssertionFailedError.class)
	public void withOtherClass_noFixtureDataSupplied_missingValues() {
		beanTester.exercise(beanSpyWithOtherClass, FilterSet.includingOnly("otherClass"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void attemptToSupplyForEnum() {
		beanTester.withFixture(BeanSpy.MyEnum.class, BeanSpy.MyEnum.ONE, BeanSpy.MyEnum.TWO);
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken1() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("z"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken2() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("b"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken3() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("s"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken4() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("i"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken5() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("l"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken6() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("f"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken7() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("d"));
	}

	@Test(expected=AssertionFailedError.class)
	public void whenBroken8() {
		beanSpy.broken = true;
		beanTester.exercise(beanSpy, FilterSet.includingOnly("c"));
	}


}
