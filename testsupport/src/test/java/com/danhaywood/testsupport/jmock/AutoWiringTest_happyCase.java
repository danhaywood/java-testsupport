package com.danhaywood.testsupport.jmock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.danhaywood.testsupport.jmock.JUnitRuleMockery2.ClassUnderTest;
import com.danhaywood.testsupport.jmock.JUnitRuleMockery2.Mode;

public class AutoWiringTest_happyCase {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    private Collaborator collaborator;

    @ClassUnderTest
	private Collaborating collaborating;

    @Before
	public void setUp() throws Exception {
    	collaborating = (Collaborating) context.getClassUnderTest();
	}
    
    @Test
    public void poke() {
    	assertThat(collaborating.collaborator, is(not(nullValue())));
    }

    
    ////////////////////////

    public static interface Collaborator {
        public void doOtherStuff();
    }

    public static class Collaborating {
        private final Collaborator collaborator;

        public Collaborating(final Collaborator collaborator) {
            this.collaborator = collaborator;
        }

        public void doStuff() {
            collaborator.doOtherStuff();
        }
    }


}
