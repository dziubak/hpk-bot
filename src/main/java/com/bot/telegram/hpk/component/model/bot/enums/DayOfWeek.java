package com.bot.telegram.hpk.component.model.bot.enums;

/**
 * Enum to represent days of the week.
 */
public enum DayOfWeek {
    MON("Пн.", "MONDAY"),
    TUE("Вт.", "TUESDAY"),
    WED("Ср.", "WEDNESDAY"),
    THU("Чт.", "THURSDAY"),
    FRI("Пт.", "FRIDAY"),
    SAT("Сб.", "SATURDAY"),
    SUN("Нд.", "SUNDAY");

    private String name;
    private String fullEngName;

    DayOfWeek(String name, String fullEngName) {
        this.name = name;
        this.fullEngName = fullEngName;
    }

    public String getName() {
        return name;
    }

    public String getFullEngName(){
        return fullEngName;
    }
}
