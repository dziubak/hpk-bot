package com.bot.telegram.hpk.services.output;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bot.telegram.hpk.services.obtainers.IObtainer;

/**
 * 
 * @author alex
 *
 */
@Component
public class HelpObtainerService implements IObtainer {

	/**
	 * @return - Here might be some information in returning about commands that user can use
	 * 
	 */
	@Override
	public String obtainMessage(String command) {
		return command;
	}

	@Override
	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup() {
		// TODO Auto-generated method stub
		return null;
	}
}
