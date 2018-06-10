package com.bot.telegram.hpk.services.obtainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * 
 * @author alex
 *
 */
@Component
public class ObtainerLookUp {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * All commands that bot can accept located here. TODO delegating from delegate
	 * - find a way to handle many words in a command
	 * 
	 * @param command
	 * @return
	 */
	public String obtainMessage(String command) {
		IObtainer iObtainer = (IObtainer) applicationContext.getBean(command + "ObtainerService");
		return iObtainer.obtainMessage(command);
	}

	public ReplyKeyboardMarkup obtainKeyboard(String command) {
		IObtainer iObtainer = (IObtainer) applicationContext.getBean(command + "ObtainerService");

		return iObtainer.obtainReplyKeyboardMarkup();
	}

}
