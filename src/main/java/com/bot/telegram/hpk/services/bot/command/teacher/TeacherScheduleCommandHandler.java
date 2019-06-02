package com.bot.telegram.hpk.services.bot.command.teacher;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import com.bot.telegram.hpk.services.bot.command.BotCommand;
import com.bot.telegram.hpk.services.bot.command.CommandNames;
import com.bot.telegram.hpk.services.user.TelegramUserService;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.ASK_TEACHER_NAME;

/**
 * Point where we need to set up reading users data.
 * We send here a message and ask user to write a teacher name.
 * Additionally we change context to UserMode.READ for reading next data.
 */
@Component
public class TeacherScheduleCommandHandler extends BotCommand {

    @Autowired
    private TelegramUserService telegramUserService;

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {
        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();

        //set user mode to read data.
        telegramUserService.updateUserModeAndCommandAndTeacherIdByUserId(chatId, UserMode.READ, CommandNames.READ_TEACHER_NAME, "NONE_ID" );

        //build and send message
        return KeyboardUtils.buildResponseMessage(chatId, ASK_TEACHER_NAME, null, true);
    }
}
