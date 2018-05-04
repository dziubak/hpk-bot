package com.bot.telegram.hpk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bot.telegram.hpk.services.TelegramBotService;

@RestController
@RequestMapping(value = "/bot")
public class TelegramBotController {

	@Autowired
	private TelegramBotService telegramBotService;

	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public String runBot() {
		telegramBotService.initBot();
		return "Telegram bot start.";
	}

}
