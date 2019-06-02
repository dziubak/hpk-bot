package com.bot.telegram.hpk.component.model.bot;

import com.bot.telegram.hpk.component.model.bot.enums.UserMode;

import java.time.LocalDateTime;

public class TelegramUser {
    private int id;
    private int telegramUserId;
    private long telegramChatId;
    private String telegramFirstname;
    private String telegramLastname;
    private String telegramNickname;

    private LocalDateTime lastActivity;
    private boolean isBlocked;
    private UserMode mode;
    private String modeCommand;

    private String teacherId;
    private String groupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(int telegramUserId) {
        this.telegramUserId = telegramUserId;
    }

    public long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public String getTelegramFirstname() {
        return telegramFirstname;
    }

    public void setTelegramFirstname(String telegramFirstname) {
        this.telegramFirstname = telegramFirstname;
    }

    public String getTelegramLastname() {
        return telegramLastname;
    }

    public void setTelegramLastname(String telegramLastname) {
        this.telegramLastname = telegramLastname;
    }

    public String getTelegramNickname() {
        return telegramNickname;
    }

    public void setTelegramNickname(String telegramNickname) {
        this.telegramNickname = telegramNickname;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public UserMode getMode() {
        return mode;
    }

    public void setMode(UserMode mode) {
        this.mode = mode;
    }

    public String getModeCommand() {
        return modeCommand;
    }

    public void setModeCommand(String modeCommand) {
        this.modeCommand = modeCommand;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
