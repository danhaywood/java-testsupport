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

import static org.junit.Assert.assertThat;
import static com.danhaywood.java.testsupport.matchers.HamcrestMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

public class HamcrestMatchersTest_navigableFrom {

    static class Customer {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        private Address address;
        public Address getAddress() {
            return address;
        }
        public void setAddress(Address address) {
            this.address = address;
        }

        private List<PhoneNumber> phoneNumbers = Lists.newArrayList();
        public List<PhoneNumber> getPhoneNumbers() {
            return phoneNumbers;
        }
        public PhoneNumber addPhoneNumber(String numberStr) {
            final PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setNumber(numberStr);
            phoneNumbers.add(phoneNumber);
            return phoneNumber;
        }
    }
    
    static class Address {
        private int houseNumber;
        public int getHouseNumber() {
            return houseNumber;
        }
        public void setHouseNumber(int houseNumber) {
            this.houseNumber = houseNumber;
        }
        
        private City city;
        public City getCity() {
            return city;
        }
        public void setCity(City city) {
            this.city = city;
        }
        
    }
    
    static class PhoneNumber {
        private String number;
        public String getNumber() {
            return number;
        }
        public void setNumber(String number) {
            this.number = number;
        }
    }
    static class City {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    private Customer customer;
    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;
    private City city;
    private Address address;

    @Before
    public void setUp() throws Exception {
        customer = new Customer();
        customer.setName("Joe Bloggs");

        address = new Address();
        address.setHouseNumber(23);
        customer.setAddress(address);

        city = new City();
        city.setName("London");
        address.setCity(city);

        phoneNumber1 = customer.addPhoneNumber("0207 123 4567");
        phoneNumber2 = customer.addPhoneNumber("0207 765 4321");
    }

    @Test
    public void canNavigateFromNullContext() throws Exception {
        assertThat(null, navigatedFrom(null, "anything"));
    }

    @Test
    public void canNavigateToValue() throws Exception {
        assertThat("Joe Bloggs", navigatedFrom(customer, "name"));
    }

    @Test
    public void canNavigateToNullValue() throws Exception {
        customer.setName(null);
        assertThat(null, navigatedFrom(customer, "name"));
    }

    @Test
    public void canNavigateToNull() throws Exception {
        assertThat(null, navigatedFrom(customer, "nonExistent"));
    }

    @Test
    public void canNavigateToReference() throws Exception {
        assertThat(address, navigatedFrom(customer, "address"));
    }

    @Test
    public void canNavigateOneHop() throws Exception {
        assertThat(23, navigatedFrom(customer, "address.houseNumber"));
    }

    @Test
    public void canNavigateToNullOneHop() throws Exception {
        assertThat(null, navigatedFrom(customer, "address.nonExistent"));
    }

    @Test
    public void canNavigateTwoHops() throws Exception {
        assertThat("London", navigatedFrom(customer, "address.city.name"));
    }

    @Test
    public void canNavigateToCollection() throws Exception {
        assertThat(Arrays.asList(phoneNumber1, phoneNumber2), navigatedFrom(
                customer, "phoneNumbers"));
    }

    @Test
    public void canEvaluateWithinCollection() throws Exception {
        assertThat(phoneNumber2, navigatedFrom(customer, "phoneNumbers[1]"));
    }
}