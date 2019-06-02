package com.bot.telegram.hpk.services.bot.command;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.enums.WeekType;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Abstract class for Command of the Bot.
 */
public abstract class BotCommand {

    @Autowired
    protected AbsSender absSender;

    public abstract BotApiMethod<?> handle(final Update update, final BtnPayload btnPayload);

    protected WeekType buildOpositeWeekType(WeekType currentWeek ) {
        if ( currentWeek == WeekType.LOW_WEEK ) {
            return WeekType.TOP_WEEK;
        } else if ( currentWeek == WeekType.TOP_WEEK ) {
            return WeekType.LOW_WEEK;
        }

        return currentWeek;
    }
}
