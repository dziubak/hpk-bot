package com.bot.telegram.hpk.services.obtainers;

import org.springframework.stereotype.Component;

/**
 * 
 * @author alex
 *
 */
@Component
public class HelpObtainerService implements IObtainerSerivice {

	/**
	 * @return - Here might be some information in returning about commands that user can use
	 * 
	 */
	@Override
	public String obtaineMessage(String command) {
		return command;
	}
}
