/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.statemachine;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.status.Status;

/**
 * Use for statemachine initiation.
 *
 * @author Tsimafei_Dynikau
 */
@Component
public class StateMachineSupplier {

    private final StateMachineTreadLocalFactory<Status, Event> statesEventsStateMachineTreadLocalFactory;

    public StateMachineSupplier(StateMachineTreadLocalFactory<Status, Event> stateMachineFactory) {
        this.statesEventsStateMachineTreadLocalFactory = stateMachineFactory;
    }

    public StateMachine<Status, Event> getCarStateMachine(Car car) {
        StateMachine<Status, Event> machine = statesEventsStateMachineTreadLocalFactory.getStateMachine();
        ExtendedState extendedState = new DefaultExtendedState();
        extendedState.getVariables().put("car", car);
        machine.getStateMachineAccessor().withRegion().resetStateMachine(
                new DefaultStateMachineContext<>(
                        Status.get(car.getCurrentStatus()),
                        null,
                        null,
                        extendedState
                ));
        return machine;
    }
}
