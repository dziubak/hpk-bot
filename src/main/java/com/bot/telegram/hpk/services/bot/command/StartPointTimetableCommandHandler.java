package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

@Component
/**
 * Start point of the Teacher Module.
 */
public class StartPointTimetableCommandHandler extends BotCommand {

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {

        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();


        return KeyboardUtils.buildResponseMessage(chatId, 0, "Зроби наступний вибір в меню " + EmojiParser.parseToUnicode( ":small_red_triangle_down:"),
                KeyboardUtils.buildTimetableStartPointKeyboard() );

    }

}
