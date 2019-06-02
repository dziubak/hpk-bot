package com.bot.telegram.hpk.services.bot.command.group;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import com.bot.telegram.hpk.services.bot.command.BotCommand;
import com.bot.telegram.hpk.services.bot.command.CommandNames;
import com.bot.telegram.hpk.services.user.TelegramUserService;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.ASK_GROUP_NAME;
import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.ASK_TEACHER_NAME;

@Component
public class ChangeGroupNameCommandHandler extends BotCommand {

    @Autowired
    private AbsSender sender;

    @Autowired
    private TelegramUserService telegramUserService;

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {

        //delete message

        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getMessage() != null ? update.getMessage().getMessageId() : update.getCallbackQuery().getMessage().getMessageId();

//        LOGGER.info("User close message " + chatId );

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);

        try {
            sender.execute( deleteMessage );
        } catch (TelegramApiException e) {
            e.getStackTrace();
//            LOGGER.info( "Error while removing users message from Teachers " + chatId);
        }

        telegramUserService.updateUserModeAndCommandAndTeacherIdByUserId(chatId, UserMode.READ, CommandNames.READ_GROUP_NAME, "NONE_ID");

        //build and send message
        //TODO: Fix grammar error
        return KeyboardUtils.buildResponseMessage( chatId, ASK_GROUP_NAME, null, true);
    }

}
