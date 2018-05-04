package com.bot.telegram.hpk.services.obtainers;

import org.springframework.stereotype.Component;

/**
 * 
 * @author alex
 *
 */
@Component
public class DefaultObtainer implements IObtainerSerivice {

	@Override
	public String obtaineMessage(String command) {
		return "command  you entered is incorrect pls use command: 'help' to get more information about avaliable commands";
	}
}
