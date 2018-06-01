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
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.service.impl.CarStatusServiceImpl;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.PreorderDto;

import java.util.EnumSet;

/**
 * CarStateMachineBuilder for build machine.
 *
 * @author Tsimafei_Dynikau
 */
@Component
public class CarStateMachineBuilder {

    private final CarStatusServiceImpl carStatusService;

    public CarStateMachineBuilder(CarStatusServiceImpl carStatusService) {
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
                .action(this.drop())
                .event(Event.DROP_CAR);

        return builder.build();
    }

    private Action<Status, Event> drop() {
        return context -> carStatusService.drop(getCarFromContext(context));
    }

    private Action<Status, Event> rent() {
        return context -> carStatusService.rent(getCarFromContext(context),
                context.getExtendedState().get(ContextVars.PREORDER, PreorderDto.class));
    }

    private Action<Status, Event> release() {
        return context -> carStatusService.release(getCarFromContext(context));
    }

    private Action<Status, Event> putOnMaintenance() {
        return context -> carStatusService.putOnMaintenance(getCarFromContext(context));
    }

    private static Car getCarFromContext(StateContext<Status, Event> context) {
        return context.getExtendedState().get(ContextVars.CAR, Car.class);
    }
}
