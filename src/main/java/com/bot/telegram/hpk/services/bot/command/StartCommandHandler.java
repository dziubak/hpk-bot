package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import static com.bot.telegram.hpk.services.util.KeyboardUtils.buildResponseMessage;
import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.BOT_START_MAIN;

/**
 * This handler builds start menu.
 */
@Component
public class StartCommandHandler extends BotCommand {

    private static final Logger LOGGER = LogManager.getLogger(StartCommandHandler.class.getName());

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {
        long chatId = update.getMessage().getChatId();
        int messageId = update.getMessage().getMessageId();

        LOGGER.info( "Show Welcome Message: " + chatId );

        return buildResponseMessage( chatId, messageId, BOT_START_MAIN, KeyboardUtils.buildNewMainMenuReplyKeyboard() );
    }


}
