package ru.vtb.carrent.car.status;

/**
 * Car statuses
 *
 * @author Valiantsin_Charkashy
 */
public enum Status {

    IN_STOCK("В Наличии"),
    IN_RENT("В Прокате"),
    ON_MAINTENANCE("На Обслуживании"),
    DROP_OUT("Выбыл из автопарка");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Status get(String value) {
        if (value == null) {
            return  null;
        }

        for (Status status : values()) {
            if (value.equalsIgnoreCase(status.getDisplayName())) {
                return status;
            }
        }
        return null;
    }
}
