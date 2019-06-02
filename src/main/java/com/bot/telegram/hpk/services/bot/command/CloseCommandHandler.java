package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import com.vdurmont.emoji.EmojiParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class CloseCommandHandler extends BotCommand {

    private static final Logger LOGGER = LogManager.getLogger(CloseCommandHandler.class.getName());

    @Autowired
    private AbsSender sender;

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {
        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getMessage() != null ? update.getMessage().getMessageId() : update.getCallbackQuery().getMessage().getMessageId();

        LOGGER.info("User close message " + chatId );

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);

        try {
            sender.execute( deleteMessage );
        } catch (TelegramApiException e) {
            LOGGER.info( "Error while removing users message " + chatId);
        }

        String message = EmojiParser.parseToUnicode(":closed_book:" ) + "Я закрив повідомлення " + ( btnPayload.getParam().equals("teacher") ? "з розкладом викладача." : "з нагадуванням." );

        return KeyboardUtils.buildResponseMessage( chatId, messageId, message, KeyboardUtils.buildNewMainMenuReplyKeyboard());
    }
}
