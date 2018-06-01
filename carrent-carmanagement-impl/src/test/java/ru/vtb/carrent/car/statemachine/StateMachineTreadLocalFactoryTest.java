package ru.vtb.carrent.car.statemachine;

import org.mockito.Mockito;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StateMachineTreadLocalFactoryTest {

    @Test
    public void testGetStateMachine() {
        StateMachine<String, String> testStateMachine = Mockito.mock(StateMachine.class);
        StateMachineFactory<String, String> mockFactory = Mockito.mock(StateMachineFactory.class);
        when(mockFactory.getStateMachine()).thenReturn(testStateMachine);

        StateMachineTreadLocalFactory<String, String> smTreadLocalFactory = new StateMachineTreadLocalFactory<>(mockFactory);
        StateMachine<String, String> resultMachine = smTreadLocalFactory.getStateMachine();
        verify(mockFactory).getStateMachine();

        resultMachine = smTreadLocalFactory.getStateMachine();
        verify(mockFactory).getStateMachine(); //check that we don't do new invocation
    }
}