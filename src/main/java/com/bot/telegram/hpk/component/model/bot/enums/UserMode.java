package com.bot.telegram.hpk.component.model.bot.enums;

public enum UserMode {
    READ("read"),
    WRITE("write");

    private String alias;

    private UserMode(String anAlias) {
        this.alias = anAlias;
    }

    public String getAlias() {
        return this.alias;
    }
}
