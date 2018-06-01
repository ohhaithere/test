package ru.vtb.carrent.car.statemachine;

import org.mockito.Mockito;
import org.springframework.statemachine.StateMachine;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.status.Status;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StateMachineTreadLocalFactoryTest {

    @Test
    public void testGetStateMachine() {
        StateMachine<Status, Event> testStateMachine = Mockito.mock(StateMachine.class);
        CarStateMachineBuilder mockBuilder = Mockito.mock(CarStateMachineBuilder.class);
        when(mockBuilder.build()).thenReturn(testStateMachine);

        StateMachineTreadLocalFactory smTreadLocalFactory = new StateMachineTreadLocalFactory(mockBuilder);
        StateMachine<Status, Event> resultMachine = smTreadLocalFactory.getStateMachine();
        verify(mockBuilder).build();

        resultMachine = smTreadLocalFactory.getStateMachine();
        verify(mockBuilder).build(); //check that we don't do new invocation
    }
}