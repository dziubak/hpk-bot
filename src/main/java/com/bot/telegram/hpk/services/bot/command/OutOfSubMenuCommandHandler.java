package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import static com.bot.telegram.hpk.services.util.KeyboardUtils.buildResponseMessage;

/**
 * Handles returning from the sub-menu.
 * Should show some message to the user.
 * Set UserMode.WRITE, because it's kind of cancel of READing.
 * Show the main keyboard.
 */
@Component
public class OutOfSubMenuCommandHandler extends BotCommand {
    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {
        long chatId = update.getMessage().getChatId();
        int messageId = update.getMessage().getMessageId();

        return buildResponseMessage( chatId, messageId,
                "Назад на головне меню" + EmojiParser.parseToUnicode(" :small_red_triangle_down: "),
                KeyboardUtils.buildNewMainMenuReplyKeyboard() );

    }
}
