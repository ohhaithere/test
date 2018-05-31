/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.event;

/**
 * Car events.
 *
 * @author Tsimafei_Dynikau
 */
public enum Event {

    MANUAL_BOOKING("МП вручную выдал АМ без предзаказа"),
    PREORDER_BOOKING("АМ по предзаказу"),
    RENT_DONE("Клиент вернул авто"),
    GO_TO_SERVICE("Пора на сервис"),
    SERVICE_DONE("ТО пройден"),
    DROP_CAR("Менеджер по обслуживанию по результатам ТО принял решение о выбытии АМ");

    private final String name;

    Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Event getEventByName(String value) {
        if (value == null) {
            return null;
        }

        for (Event event : values()) {
            if (value.equals(event.name)) {
                return event;
            }
        }
        return null;
    }

}
