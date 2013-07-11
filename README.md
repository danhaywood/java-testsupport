java-testsupport
================

[![Build Status](https://travis-ci.org/danhaywood/java-testsupport.png?branch=master)](https://travis-ci.org/danhaywood/java-testsupport)

A collection of unit testing utilities.

## JMock Extensions

An extension to [JMock](http://jmock.org), supporting autowiring of mocks, was originally developed here, but has now moved into [Apache Isis](http://isis.apache.org) (in the `org.apache.isis.core:isis-core-unittestsupport` module).

See [this blog post](http://danhaywood.com/2012/07/11/mockito-like-automocking-and-optional-autowiring-in-jmock/) for the original discussion on the features; see the [Isis documentation](http://isis.apache.org/core/unittestsupport.html) for the current documentation.

## Code Coverage

The objective of these utilities is to take some of the automate the testing of 'trivial' code which would otherwise be tedious to test.  The point is not so much that we expect the tests to fail, but to be able to increase the code coverage figures.  Thus, any figure less than 100% represents real business logic that has not been tested.

You could also think of these tests as contract tests, enforcing the (usually implied) contract of what it means for a class to be a pojo, or a `Util` class, or a value type.

### PojoTester

Exercises getters and setters of a pojo, this utility exercises them automatically.  All primitive, strings, dates and java.math values are automatically exercised.  The `FixtureDatumFactory` interface allows representative instances of other types to be provided.

For example, the following exercises the `AgreementRole` class (which in addition to the built-ins has a properties of type `Agreement`, `Party` and `AgreementRoleType`): 

    @Mock
    protected DomainObjectContainer mockContainer;

    @Test
    public void test() {
        PojoTester.relaxed()
			.withFixture(dates())
            .withFixture(DomainObjectContainer.class, mockContainer)
            .withFixture(FixtureDatumFactoriesForAnyPojo.pojos(
                              AgreementRoleType.class))
            .withFixture(FixtureDatumFactoriesForAnyPojo.pojos(
                              Agreement.class, AgreementForTesting.class))
            .withFixture(FixtureDatumFactoriesForAnyPojo.pojos(
                              Party.class, PartyForTesting.class))
            .exercise(new AgreementRole());
    }

	private static FixtureDatumFactory<LocalDate> dates() {
		return new FixtureDatumFactory<LocalDate>(LocalDate.class, new LocalDate(2012, 7, 19), new LocalDate(2012, 7, 20), new LocalDate(2012, 8, 19), new LocalDate(2013, 7, 19));
	}

### PrivateConstructorTester

This utility simply instantiates any classes (such as `Util` classes) that have a private constructor.

For example:

	@Test
	public void invokeConstructor() throws Exception {
		new PrivateConstructorTester(Constants.class).exercise();
	}

where:

	public static class Constants {
		private Constants() {
			// this is where we want some coverage!
		}
		
		public final static int FOO = 1;
		public final static int BAR = 1;
	}
	

### ValueTypeContractTestAbstract

A utility to check the contract for value types was originally developed here, but has now moved into [Apache Isis](http://isis.apache.org) (in the `org.apache.isis.core:isis-core-unittestsupport` module).

See [this blog post](http://danhaywood.com/2010/11/04/contract-test-for-value-types/) for the original discussion on the features; see the [Isis documentation](http://isis.apache.org/core/unittestsupport.html) for the current documentation.  (As of isis-core-1.3.0-SNAPSHOT and later, this utility also tests value types that are `Comparable`).


## DBUnitRule

The `DbUnitRule` bootstraps an instance of [HsqlDB](http://hsqldb.org/) for each test and uses [DbUnit](http://www.dbunit.org/) to load data and assert on changes, using JSON to hold the datasets.  See [this blog post](http://danhaywood.com/2011/12/20/db-unit-testing-with-dbunit-json-hsqldb-and-junit-rules/) for further discussion.

> DBUnit is licensed under [LGPL 2.1](http://www.gnu.org/licenses/lgpl-2.1.html), which is [incompatible](http://www.apache.org/legal/resolved.html#category-x) with a pure [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0.html) library.  For this reason the dbunit dependency is marked as <tt>&lt;optional>true&lt;/optional></tt>.  If you wish to use this feature, make sure you include the dbUnit dependency directly.

The following example shows a possible use:

    public class DbUnitRuleTest {
    
    	@Rule
    	public DbUnitRule dbUnit = 
            new DbUnitRule(DbUnitRuleTest.class, jdbcDriver.class,
                           "jdbc:hsqldb:file:src/test/resources/testdb",
    			           "SA", "");
    
    	@Ddl("customer.ddl")
    	@JsonData("customer.json")
    	@Test
    	public void update_lastName_verifyUsingDataSets() throws Exception {
    
    		// when
    		Statement statement = dbUnit.getConnection().createStatement();
    		statement.executeUpdate(
                "update customer set last_name='Bloggs' where id=2");
    
    		// then
    		ITable actualTable = 
                 dbUnit.createQueryTable("customer",
    				                     "select * from customer order by id");
    		ITable expectedTable =
                 dbUnit.jsonDataSet("customer-updated.json").getTable("customer");
    
    		Assertion.assertEquals(expectedTable, actualTable);
    	}
    }

where `customer.ddl` is:

    drop table customer if exists;
    create table customer (
        id         int         not null primary key
       ,first_name varchar(30) not null
       ,initial    varchar(1)  null
       ,last_name  varchar(30) not null
    )

and `customer.json` is:

    {
      "customer": [
	    {
	      "id": 1, "first_name": "John", "initial": "K", "last_name": "Smith"
	    },
	    {
	      "id": 2, "first_name": "Mary", "last_name": "Jones"
	    }
	  ]
    }

and `customer-updated.json` is:

    {
      "customer": [
	    {
	      "id": 1, "first_name": "John", "initial": "K", "last_name": "Smith"
	    }, {
	      "id": 2, "first_name": "Mary", "last_name": "Bloggs"
	    }
      ]
    }



## Hamcrest Matchers

The library provides a Hamcrest matcher to assert on the contents of object graphs.  This idea was originally discussed in [this blog post](http://danhaywood.com/2009/12/14/asserting-on-object-graphs-using-hamcrest-and-mvel/)

For example:

    @Test
    public void customer_address_city_name() throws Exception {
        assertThat("London", navigatedFrom(customer, "address.city.name"));
    }

The matcher also supports collections.  Behind the scenes it uses the [MVEL](http://mvel.codehaus.org/) expression language.

## Legal Stuff

### License

    Copyright 2013 Dan Haywood

    Licensed under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

### Dependencies

    <dependencies>
        <dependency>
            <!-- Common Public License - v 1.0 -->
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.mvel</groupId>
            <artifactId>mvel2</artifactId>
            <version>2.1.6.Final</version>
        </dependency>
    
        <dependency>
            <!-- GNU Lesser General Public License, Version 2.1 -->
            <groupId>org.dbunit</groupId>
            <artifactId>dbunit</artifactId>
            <version>2.4.9</version>
            <!-- 
            marked as optional due to the license; include explicitly
            -->
            <optional>true</optional>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>14.0.1</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-core-asl</artifactId>
            <version>1.9.12</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-mapper-asl</artifactId>
            <version>1.9.12</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.5</version>
        </dependency>

        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-nop</artifactId>
            <version>1.7.5</version>
        </dependency>

    </dependencies>
