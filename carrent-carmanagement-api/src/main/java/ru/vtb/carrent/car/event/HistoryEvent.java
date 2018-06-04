/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.event;

/**
 * History notification events.
 *
 * @author Tsimafei_Dynikau
 */
public enum HistoryEvent {
    CREATE("Автомобиль создан"),
    EDIT("Автомобиль изменен"),
    DELETE("Автомобиль удален"),
    STATUS_CHANGED("Изменен статус автомобиля");

    private final String name;

    HistoryEvent(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static HistoryEvent getHitoryEventByName(String value) {
        if (value == null) {
            return null;
        }

        for (HistoryEvent event : values()) {
            if (value.equals(event.name)) {
                return event;
            }
        }
        return null;
    }
}
