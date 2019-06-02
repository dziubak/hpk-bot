package com.bot.telegram.hpk.services.handler;

import com.bot.telegram.hpk.component.model.bot.TelegramUser;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import com.bot.telegram.hpk.services.bot.command.BotCommandRegister;
import com.bot.telegram.hpk.services.user.TelegramUserService;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.time.LocalDateTime;

@Service
public class TelegramHandlerService {

    private final static Logger log = Logger.getLogger(TelegramHandlerService.class);

    @Autowired
    private TelegramUserService telegramUserService;

    public TelegramUser handleRequest(Update update, long telegramChatId){
        TelegramUser currentUser = telegramUserService.getTelegramUserByTelegramChatId(telegramChatId);

        if (currentUser != null) {
            telegramUserService.updateTelegramUserByUserId(buildTelegramUser(update, telegramChatId));

        } else {
            telegramUserService.createTelegramUser(buildTelegramUser(update, telegramChatId));
            currentUser = telegramUserService.getTelegramUserByTelegramChatId(telegramChatId);

            log.info("New user with telegram chat id: " + telegramChatId);
        }

        return currentUser;
    }

    private TelegramUser buildTelegramUser(Update update, long telegramChatId) {
        TelegramUser telegramUser = new TelegramUser();

        User user = update.getMessage() != null ? update.getMessage().getFrom() :
                update.getCallbackQuery() != null ? update.getCallbackQuery().getMessage().getFrom() : new User();

        telegramUser.setTelegramUserId(user.getId());
        telegramUser.setTelegramChatId(telegramChatId);
        telegramUser.setTelegramFirstname(user.getFirstName());
        telegramUser.setTelegramLastname(user.getLastName());
        telegramUser.setTelegramNickname(user.getUserName());

        telegramUser.setLastActivity(LocalDateTime.now());
        telegramUser.setBlocked(false);
        telegramUser.setMode(UserMode.WRITE);
        telegramUser.setModeCommand("NONE_CMD");

        return telegramUser;
    }


}
