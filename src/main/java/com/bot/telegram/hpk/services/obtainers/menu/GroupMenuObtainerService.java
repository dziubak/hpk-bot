package com.bot.telegram.hpk.services.obtainers.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bot.telegram.hpk.services.button.InteractWithUserViaButtonsService;
import com.bot.telegram.hpk.services.obtainers.IObtainer;

@Service
public class GroupMenuObtainerService implements IObtainer {

	@Autowired
	private InteractWithUserViaButtonsService interactWithUserViaButtonsService;

	@Override
	public String obtainMessage(String command) {
		return command;
	}

	@Override
	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup() {
		return interactWithUserViaButtonsService.createKeyboardTimetableGroup();
	}

}
