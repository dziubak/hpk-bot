package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static com.bot.telegram.hpk.services.bot.TelegramBotService.buildCommandHandlingMessage;

@Component
public class BotCommandRegister {

    private Map<String, BotCommand> commands;

    private static final Logger LOGGER = LogManager.getLogger(BotCommandRegister.class.getName());

    public BotCommandRegister() {}

    public void registerCommand( final String commandName, final BotCommand command ) {

        if ( commands == null ) {
            commands = new HashMap<>();
        }

        commands.put( commandName, command );
    }

    public BotApiMethod<?> executeCommand(final String commandName, final Update update, final BtnPayload btnPayload) {
        if ( !commands.containsKey( commandName ) ) {
            LOGGER.info( "Unknown command name: " + commandName );
            return null;
        }

        long chatId = update.getMessage() != null ? update.getMessage().getChatId() :
                update.getCallbackQuery() != null ? update.getCallbackQuery().getMessage().getChatId() : 0;


        LOGGER.info( "Executing command name: " + commandName );
        long timeA = System.currentTimeMillis();
        BotApiMethod<?> method = null;

        try{
            method = commands.get( commandName ).handle( update, btnPayload);
        }catch (Exception e) {
            LOGGER.info( "Exception in the command " + commandName + " " + e);
            e.printStackTrace();
            method = buildCommandHandlingMessage(chatId);
        }
        long timeB = System.currentTimeMillis();

        LOGGER.info( "Executing time: " + ( timeB - timeA ) );
        return method;
    }

}
