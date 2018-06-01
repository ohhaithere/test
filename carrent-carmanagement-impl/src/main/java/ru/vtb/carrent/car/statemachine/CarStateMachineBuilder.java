/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.statemachine;

import lombok.SneakyThrows;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.listener.CarStateMachineListener;
import ru.vtb.carrent.car.service.impl.CarStatusServiveImpl;
import ru.vtb.carrent.car.status.Status;

import java.util.EnumSet;

/**
 * CarStateMachineBuilder for build machine.
 *
 * @author Tsimafei_Dynikau
 */
@Component
public class CarStateMachineBuilder {

    private final CarStateMachineListener carStateMachineListener;
    private final CarStatusServiveImpl carStatusService;

    public CarStateMachineBuilder(CarStateMachineListener carStateMachineListener, CarStatusServiveImpl carStatusService) {
        this.carStateMachineListener = carStateMachineListener;
        this.carStatusService = carStatusService;
    }

    /**
     * Build and configure car statemachine.
     *
     * @return new state machine
     */
    @SneakyThrows
    public StateMachine<Status, Event> build() {
        StateMachineBuilder.Builder<Status, Event> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .listener(listener())
                .autoStartup(true);

        builder.configureStates()
                .withStates()
                .initial(Status.IN_STOCK)
                .states(EnumSet.allOf(Status.class));

        builder.configureTransitions()
                .withExternal()
                .source(Status.IN_STOCK).target(Status.IN_RENT)
                .event(Event.MANUAL_BOOKING)
                .and()
                .withExternal()
                .source(Status.IN_STOCK).target(Status.IN_RENT)
                .event(Event.PREORDER_BOOKING)
                .and()
                .withExternal()
                .source(Status.IN_RENT).target(Status.IN_STOCK)
                .action(this.release())
                .event(Event.RENT_DONE)
                .and()
                .withExternal()
                .source(Status.IN_STOCK).target(Status.ON_MAINTENANCE)
                .action(putOnMaintenance())
                .event(Event.GO_TO_SERVICE)
                .and()
                .withExternal()
                .source(Status.ON_MAINTENANCE).target(Status.IN_STOCK)
                .action(this.release())
                .event(Event.SERVICE_DONE)
                .and()
                .withExternal()
                .source(Status.ON_MAINTENANCE).target(Status.DROP_OUT)
                .event(Event.DROP_CAR);

        return builder.build();
    }

    private Action<Status, Event> release() {
        return context -> carStatusService.release(getCarFromContext(context));
    }

    public Action<Status, Event> putOnMaintenance() {
        return context -> carStatusService.putOnMaintenance(getCarFromContext(context));
    }

    public StateMachineListener<Status, Event> listener() {
        return new StateMachineListenerAdapter<Status, Event>() {
            private StateContext<Status, Event> stateContext;

            @Override
            public void stateContext(StateContext<Status, Event> stateContext) {
                this.stateContext = stateContext;
            }

            @Override
            public void stateChanged(State<Status, Event> from, State<Status, Event> to) {
                Car car = getCarFromContext(stateContext);
                carStateMachineListener.changeCarStatus(car, to.getId(), stateContext.getEvent());
            }
        };
    }

    private static Car getCarFromContext(StateContext<Status, Event> context) {
        return context.getExtendedState().get("car", Car.class);
    }
}
