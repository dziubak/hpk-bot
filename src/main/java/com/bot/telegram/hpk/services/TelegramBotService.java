package com.bot.telegram.hpk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.bot.telegram.hpk.services.button.InteractWithUserViaButtonsService;
import com.bot.telegram.hpk.services.obtainers.ObtainerLookUp;
import com.bot.telegram.hpk.services.output.TimetableGroupService;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	private final static String BOT_USERNAME = "TelegramBotHPK";
	private final static String BOT_TOKEN = "336211020:AAEVa8mCUJ8toCmRHr1P0SkdsZP52yNuMUE";

	@Autowired
	private ObtainerLookUp lookerUp;

	@Autowired
	private InteractWithUserViaButtonsService interactWithUserViaButtonsService;

	@Autowired
	private TimetableGroupService timetableGroupService;

	/**
	 * Initialize Api Context
	 */
	static {
		ApiContextInitializer.init();
	}

	/**
	 * Initialization of Api Context
	 */
	public void initBot() {

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(this);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getBotUsername() {
		return BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}

	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			// Set variables
			Message message = update.getMessage();
			String textFromUser = update.getMessage().getText();

			if (update.getMessage().getText().equals("/start")) {
				textFromUser = "Hello! Select item:";
				sendMsg(message, textFromUser, interactWithUserViaButtonsService.createKeyboardMain());

			} else if (!timetableGroupService.getTimetableForGroup(textFromUser).isEmpty()) {
				sendMsg(message, timetableGroupService.getTimetableForGroup(textFromUser),
						interactWithUserViaButtonsService.createKeyboardMain());
			} else {
				sendMsg(message, lookerUp.obtainMessage(textFromUser), lookerUp.obtainKeyboard(textFromUser));
			}
		}
	}

	/**
	 * Function for send message from bot to telegram
	 * 
	 * @param message
	 *            message - message information that telegram get
	 * @param text
	 *            text-text that bot send to telegram
	 */
	private SendMessage sendMsg(Message message, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
		SendMessage messageForSend = new SendMessage();

		messageForSend.enableMarkdown(true);
		messageForSend.setChatId(message.getChatId());
		messageForSend.setText(text);

		messageForSend.setReplyMarkup(replyKeyboardMarkup);

		try {
			execute(messageForSend);
		} catch (TelegramApiException e) {

		}

		return messageForSend;
	}
}
