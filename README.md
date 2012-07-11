A collection of test support classes and utilities for Java.

ValueContractTestAbstract
-------------------------

Use to easily unit test value types, namely the `equals()` and `hashCode()` methods.  All the [usual rules](http://www.angelikalanger.com/Articles/JavaSolutions/SecretsOfEquals/Equals.html) are tested.

For example:

    public class ValueTypeContractTestAbstract_BigIntegerTest
            extends ValueTypeContractTestAbstract<BigInteger> {
        @Override
        protected List<BigInteger> getObjectsWithSameValue() {
            return Arrays.asList(new BigInteger("1"), new BigInteger("1"));
        }
        @Override
        protected List<BigInteger> getObjectsWithDifferentValue() {
            return Arrays.asList(new BigInteger("2"));
        }
    }

For further discussion, see [this blog post](http://danhaywood.com/2010/11/04/contract-test-for-value-types/)

PojoTester
----------

Use to exercise all the getters and setters of a pojo. All the main value types in the JDK are supported (int, String, java.util.Date etc), as well as enums; extensible to support any other value type.

For example:

    new PojoTester().exercise(new Customer());

PrivateConstructorTester
------------------------

Use to instantiate classes that have a hidden constructor, (to increase code coverage).

Typically this is used for classes holding a bunch of constants:

    public final class Constants {
        private Constants() {}
        public final static FOO = 1;
        public final static BAR = 2;
    }

For example:

    new PrivateConstructorTester(Constants.class).exercise();

JUnitRuleMockeryTest
--------------------

A combination of a JMock `Mockery` with a JUnit rule, providing support for Mockito-like `@Mock` annotation, along with optional auto-wiring support.  The `@Mock` support has been backported from JMock 2.6 (not released as of this writing).

For example:

    public class JUnitRuleMockery2Test_autoWiring {

        @Rule
        public JUnitRuleMockery2 context = 
            JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

        @Mock
        private Collaborator collaborator;

        @ClassUnderTest
        private Collaborating collaborating;

        @Before
	    public void setUp() throws Exception {
    	    collaborating = (Collaborating) context.getClassUnderTest();
	    }
    
        @Test
        public void checkAutoWiring() {
            assertThat(collaborating, is(not(nullValue())));
    	    assertThat(collaborating.collaborator, is(not(nullValue())));
        }
    }

Note that this class has dependencies on a number of other libraries:

-   org.junit:junit:4.10
-   org.jmock:jmock:2.5.1
-   org.jmock:jmock-junit4:2.5.1
-   org.jmock:jmock-legacy:2.5.1
-   org.hamcrest:hamcrest-core:1.3
-   org.picocontainer:picocontainer:2.14.1

For further discussion, see [this blog post](http://danhaywood.com/2012/07/11/mockito-like-automocking-and-optional-autowiring-in-jmock/).

DbUnitRule
----------

Easily test database code using a JUnit rule that integrates with DbUnit a JSON dataset.

For example:

    public class DbUnitRuleTest {

	    @Rule
        public DbUnitRule dbUnit = new DbUnitRule(
            DbUnitRuleTest.class,
			jdbcDriver.class, "jdbc:hsqldb:file:src/test/resources/testdb", "SA", "");

	    @Ddl("customer.ddl")
	    @JsonData("customer.json")
	    @Test
	    public void update_lastName() throws Exception { ... }
    }

where `customer.ddl` provides the schema, and `customer.json` is the initial data set to populate the table with

Note that this class has dependencies on a number of other libraries:

-   org.junit:junit:4.10
-   org.dbunit:dbunit:2.4.8
-   com.google.guava:guava:12.0.1
-   org.codehaus.jackson:jackson-core-asl:1.9.8
-   org.codehaus.jackson:jackson-mapper-asl:1.9.8
-   org.slf4j:slf4j-api:1.6.6
-   org.slf4j:slf4j-nop:1.6.6

For further discussion, see [this blog post](http://danhaywood.com/2011/12/20/db-unit-testing-with-dbunit-json-hsqldb-and-junit-rules/).
