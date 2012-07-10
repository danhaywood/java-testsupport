package com.danhaywood.testsupport.jmock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Rule;
import org.junit.Test;

import com.danhaywood.testsupport.jmock.JUnitRuleMockery2.Mode;

public class JMockActionsTest_returnEach {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    public interface Collaborator {
        public int readValue();
    }

    public static class ClassUnderTest {
        private final Collaborator collaborator;

        private ClassUnderTest(final Collaborator collaborator) {
            this.collaborator = collaborator;
        }

        public String prependAndRead(String prepend) {
            return prepend + " " + collaborator.readValue();
        }
    }

    @Mock
    private Collaborator collaborator;

    @Test
    public void poke() {
        context.checking(new Expectations() {
            {
                exactly(3).of(collaborator).readValue();
                will(JMockActions.returnEach(1,2,3));
            }
        });
        assertThat(new ClassUnderTest(collaborator).prependAndRead("foo"), is("foo 1"));
        assertThat(new ClassUnderTest(collaborator).prependAndRead("bar"), is("bar 2"));
        assertThat(new ClassUnderTest(collaborator).prependAndRead("baz"), is("baz 3"));
    }
}
