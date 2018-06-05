package ru.vtb.carrent.car.statemachine;

import org.mockito.Mockito;
import org.springframework.statemachine.StateMachine;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.service.CarStatusService;
import ru.vtb.carrent.car.status.Status;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class CarStateMachineTest {

    private CarStatusService carStatusService;
    private StateMachineSupplier stateMachineSupplier;

    @BeforeClass
    public void setUp() {
        carStatusService = Mockito.mock(CarStatusService.class);
        CarStateMachineBuilder stateMachineBuilder = new CarStateMachineBuilder(carStatusService);
        StateMachineTreadLocalFactory stateMachineFactory = new StateMachineTreadLocalFactory(stateMachineBuilder);
        stateMachineSupplier = new StateMachineSupplier(stateMachineFactory);
    }

    @Test
    public void testGoToService() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_STOCK.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.GO_TO_SERVICE);

        verify(carStatusService).putOnMaintenance(testCar);
    }

    @Test
    public void testGoToServiceFromIncorrectStatus() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_RENT.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.GO_TO_SERVICE);

        verify(carStatusService, never()).putOnMaintenance(testCar);
    }

    @Test
    public void testPreorderBooking() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_STOCK.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.PREORDER_BOOKING);

        verify(carStatusService).rent(testCar, null);
    }

    @Test
    public void testPreorderBookingFromIncorrectStatus() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_RENT.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.PREORDER_BOOKING);

        verify(carStatusService, never()).rent(testCar, null);
    }

    @Test
    public void testRentDone() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_RENT.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.RENT_DONE);

        verify(carStatusService).release(testCar);
    }

    @Test
    public void testRentDoneFromIncorrectStatus() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_STOCK.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.RENT_DONE);

        verify(carStatusService, never()).release(testCar);
    }

    @Test
    public void testServiceDone() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.ON_MAINTENANCE.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.SERVICE_DONE);

        verify(carStatusService).release(testCar);
    }

    @Test
    public void testServiceDoneFromIncorrectStatus() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_STOCK.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.SERVICE_DONE);

        verify(carStatusService, never()).release(testCar);
    }

    @Test
    public void testDropCar() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.ON_MAINTENANCE.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.DROP_CAR);

        verify(carStatusService).drop(testCar);
    }

    @Test
    public void testDropCarFromIncorrectStatus() {
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_STOCK.name());
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(testCar);

        stateMachine.sendEvent(Event.DROP_CAR);

        verify(carStatusService, never()).drop(testCar);
    }
}