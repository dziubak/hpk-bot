package com.bot.telegram.hpk.component.model.bot.enums;

public enum GroupType {
    FIRST_GROUP("g_1", "I підгрупа"),
    SECOND_GROUP("g_2", "ІІ підгрупа"),
    GENERAL("g_g", "Загальна підгрупа");

    private String name;
    private String friendlyName;

    GroupType(String name, String friendlyName) {
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
