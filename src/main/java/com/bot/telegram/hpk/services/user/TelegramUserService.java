package com.bot.telegram.hpk.services.user;

import com.bot.telegram.hpk.component.dao.telegram.TelegramUserDao;
import com.bot.telegram.hpk.component.model.bot.TelegramUser;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserService {

    @Autowired
    private TelegramUserDao telegramUserDao;

    public TelegramUser getTelegramUserByTelegramChatId(long telegramChatId) {
        return telegramUserDao.getTelegramUserByTelegramUserId(telegramChatId);
    }

    public boolean createTelegramUser(TelegramUser telegramUser) {
        return telegramUserDao.createTelegramUser(telegramUser);
    }

    public boolean updateUserModeAndCommandAndTeacherIdByUserId(long chatId, UserMode mode, String modeCommand, String teacherId) {
        return telegramUserDao.updateUserModeAndCommandAndTeacherIdByUserId(chatId, mode, modeCommand, teacherId);
    }

    public boolean updateTelegramUserByUserId(TelegramUser telegramUser){
        return telegramUserDao.updateTelegramUserByUserId(telegramUser);
    }

}
