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
package com.danhaywood.java.testsupport.dbunit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.Statement;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.hsqldb.jdbcDriver;
import org.junit.Rule;
import org.junit.Test;

import com.danhaywood.java.testsupport.dbunit.DbUnitRule;
import com.danhaywood.java.testsupport.dbunit.DbUnitRule.Ddl;
import com.danhaywood.java.testsupport.dbunit.DbUnitRule.JsonData;

public class DbUnitRuleTest {

	@Rule
	public DbUnitRule dbUnit = new DbUnitRule(DbUnitRuleTest.class,
			jdbcDriver.class, "jdbc:hsqldb:file:src/test/resources/testdb",
			"SA", "");

	@Ddl("customer.ddl")
	@JsonData("customer.json")
	@Test
	public void update_lastName() throws Exception {

		// when
		Statement statement = dbUnit.getConnection().createStatement();
		statement
				.executeUpdate("update customer set last_name='Bloggs' where id=2");

		// then (verify directly)
		ResultSet rs2 = dbUnit
				.executeQuery("select last_name from customer where id = 2");
		assertThat(rs2.next(), is(true));
		assertThat(rs2.getString("last_name"), equalTo("Bloggs"));

		// then (verify using datasets)
		ITable actualTable = dbUnit.createQueryTable("customer",
				"select * from customer order by id");
		ITable expectedTable = dbUnit.jsonDataSet("customer-updated.json")
				.getTable("customer");

		Assertion.assertEquals(expectedTable, actualTable);
	}
}
