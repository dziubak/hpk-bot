package com.bot.telegram.hpk.component.model.bot;

public enum DayOfWeek {
    MON("Пн", "Понеділок", "MONDAY"),
    TUE("Вт", "Вівторок", "TUESDAY"),
    WED("Ср", "Середа", "WEDNESDAY"),
    THU("Чт", "Четвер", "THURSDAY"),
    FRI("Пт", "П'ятниця", "FRIDAY");

    private String name;
    private String friendlyName;
    private String fullEngName;

    private DayOfWeek(String aName, String aFriendlyName, String fullEngName) {
        this.name = aName;
        this.friendlyName = aFriendlyName;
        this.fullEngName = fullEngName;
    }

    public String getName() {
        return this.name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public String getFullEngName(){
        return this.fullEngName;
    }

    public static DayOfWeek findDayOfWeekByName(String name) {
        DayOfWeek[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DayOfWeek day = var1[var3];
            if (day.name.equals(name)) {
                return day;
            }
        }

        throw new RuntimeException("Can't find DAY with value: " + name);
    }
}
