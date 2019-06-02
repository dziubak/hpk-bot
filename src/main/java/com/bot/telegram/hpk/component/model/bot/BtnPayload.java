package com.bot.telegram.hpk.component.model.bot;

public class BtnPayload {
    private String cmd;
    private String param;
    private boolean isBack;

    public BtnPayload(String cmd, String param, boolean isBack) {
        this.cmd = cmd;
        this.param = param;
        this.isBack = isBack;
    }

    public BtnPayload() {
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isBack() {
        return this.isBack;
    }

    public void setBack(boolean back) {
        this.isBack = back;
    }

    public String toString() {
        return "BtnPayload{cmd='" + this.cmd + '\'' + ", param='" + this.param + '\'' + ", isBack=" + this.isBack + '}';
    }
}
