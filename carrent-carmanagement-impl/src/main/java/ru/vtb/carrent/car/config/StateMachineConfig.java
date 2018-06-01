package ru.vtb.carrent.car.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.listener.CarStateMachineListener;
import ru.vtb.carrent.car.service.impl.CarStatusServiveImpl;
import ru.vtb.carrent.car.status.Status;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<Status, Event> {

    private final CarStateMachineListener carStateMachineListener;
    private final CarStatusServiveImpl carStatusServive;

    public StateMachineConfig(CarStateMachineListener carStateMachineListener, CarStatusServiveImpl carStatusServive) {
        this.carStateMachineListener = carStateMachineListener;
        this.carStatusServive = carStatusServive;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<Status, Event> config)
            throws Exception {
        config
                .withConfiguration()
                .listener(listener())
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<Status, Event> states)
            throws Exception {
        states
                .withStates()
                .initial(Status.IN_STOCK)
                .states(EnumSet.allOf(Status.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Status, Event> transitions)
            throws Exception {
        transitions
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
                .event(Event.RENT_DONE)
                .and()
                .withExternal()
                .source(Status.IN_STOCK).target(Status.ON_MAINTENANCE)
                .action(putOnMaintenance())
                .event(Event.GO_TO_SERVICE)
                .and()
                .withExternal()
                .source(Status.ON_MAINTENANCE).target(Status.IN_STOCK)
                .event(Event.SERVICE_DONE)
                .and()
                .withExternal()
                .source(Status.ON_MAINTENANCE).target(Status.DROP_OUT)
                .event(Event.DROP_CAR);
    }

    public Action<Status, Event> putOnMaintenance() {
        return context -> carStatusServive.putOnMaintenance(getCarFromContext(context));
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
