/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.statemachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

/**
 * ThreadLocal stateMachine holder for performance optimization.
 *
 * @param <S> the type of state
 * @param <E> the type of event
 *
 * @author Tsimafei_Dynikau
 */
@Component
class StateMachineTreadLocalFactory<S, E> {
    private ThreadLocal<StateMachine<S, E>> stateMachineThreadLocal = new ThreadLocal<>();

    private final StateMachineFactory<S, E> stateMachineFactory;

    public StateMachineTreadLocalFactory(StateMachineFactory<S, E> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    public StateMachine<S, E> getStateMachine() {
        StateMachine<S, E> stateMachine = stateMachineThreadLocal.get();
        if (stateMachine == null) {
            stateMachine = stateMachineFactory.getStateMachine();
            stateMachineThreadLocal.set(stateMachine);
        }
        return stateMachine;
    }
}
