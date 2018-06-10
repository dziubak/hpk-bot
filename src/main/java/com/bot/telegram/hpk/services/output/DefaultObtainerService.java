package com.bot.telegram.hpk.services.output;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bot.telegram.hpk.services.obtainers.IObtainer;

/**
 * 
 * @author alex
 *
 */
@Primary
@Service
public class DefaultObtainerService implements IObtainer {

	@Override
	public String obtainMessage(String command) {
		return "command  you entered is incorrect pls use command: 'help' to get more information about avaliable commands";
	}

	@Override
	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup() {
		// TODO Auto-generated method stub
		return null;
	}
}
