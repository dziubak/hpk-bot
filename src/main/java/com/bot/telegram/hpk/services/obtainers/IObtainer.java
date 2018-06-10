package com.bot.telegram.hpk.services.obtainers;

import com.bot.telegram.hpk.component.enums.FlagForDay;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * 
 * @author alex
 * 
 *
 */
public interface IObtainer {

	public String obtainMessage(String command);

	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup();
}
