package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.services.util.ScheduleUIConstants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

@Component
public class ShowLessonTimeCommandHandler extends BotCommand {

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {

        long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setParseMode("html");
        message.setChatId(chatId);
        message.setText(buildMessage());

        return message;
    }

    private String buildMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Пара\t  Початок - Кінець</b>");
        ScheduleUIConstants.PAIR_TIME.forEach( (number, time ) -> sb.append( "\n   " + number + "           " + time ) );

        return sb.toString();
    }
}
