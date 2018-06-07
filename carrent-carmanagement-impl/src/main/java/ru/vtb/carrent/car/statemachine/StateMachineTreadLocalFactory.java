/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.statemachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.status.Status;

/**
 * ThreadLocal stateMachine holder for performance optimization.
 *
 * @author Tsimafei_Dynikau
 */
@Component
class StateMachineTreadLocalFactory {
    private final ThreadLocal<StateMachine<Status, Event>> stateMachineThreadLocal = new ThreadLocal<>();

    private final CarStateMachineBuilder stateMachineBuilder;

    public StateMachineTreadLocalFactory(CarStateMachineBuilder stateMachineBuilder) {

        this.stateMachineBuilder = stateMachineBuilder;
    }

    StateMachine<Status, Event> getStateMachine() {
        StateMachine<Status, Event> stateMachine = stateMachineThreadLocal.get();
        if (stateMachine == null) {
            stateMachine = stateMachineBuilder.build();
            stateMachineThreadLocal.set(stateMachine);
        }
        return stateMachine;
    }
}
