package com.bot.telegram.hpk.component.model.bot.enums;

public enum WeekType {
    TOP_WEEK("w_t", "Чисельник"),
    LOW_WEEK("w_l", "Знаменник"),
    GENERAL("w_g", "Не вибрано");

    private String name;
    private String friendlyName;

    private WeekType(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public String getName() {
        return this.name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
