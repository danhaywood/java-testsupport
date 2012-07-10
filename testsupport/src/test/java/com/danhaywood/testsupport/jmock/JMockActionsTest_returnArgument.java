package com.danhaywood.testsupport.jmock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Rule;
import org.junit.Test;

import com.danhaywood.testsupport.jmock.JUnitRuleMockery2.Mode;

public class JMockActionsTest_returnArgument {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    public interface Collaborator {
        public int selectOneOf(int x, int y, int z);
    }

    public static class ClassUnderTest {
        private final Collaborator collaborator;

        private ClassUnderTest(final Collaborator collaborator) {
            this.collaborator = collaborator;
        }

        public int addTo(int x) {
            return x + collaborator.selectOneOf(10, 20, 30);
        }
    }

    @Mock
    private Collaborator collaborator;

    @Test
    public void poke() {
        context.checking(new Expectations() {
            {
                one(collaborator).selectOneOf(with(any(Integer.class)), with(any(Integer.class)), with(any(Integer.class)));
                will(JMockActions.returnArgument(1)); // ie the 2nd argument, which is '20'
            }
        });
        assertThat(new ClassUnderTest(collaborator).addTo(4), is(24)); // adding 4 to the second argument
    }
    
}
