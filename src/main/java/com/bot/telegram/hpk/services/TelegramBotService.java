package com.bot.telegram.hpk.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import com.bot.telegram.hpk.services.obtainers.ObtainerLookUp;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	private static final Logger log = Logger.getLogger(TelegramBotService.class);

	private final static String BOT_USERNAME = "TelegramBotHPK";
	private final static String BOT_TOKEN = "336211020:AAEVa8mCUJ8toCmRHr1P0SkdsZP52yNuMUE";
	@Autowired
	private ObtainerLookUp lookerUp;

	@Autowired
	private TimetableService timetableService;

	@Autowired
	private ReplacementService replacementService;

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
			String textFromUser = message.getText();
			long chatId = message.getChatId();

			sendMsg(message, lookerUp.obtaine(textFromUser));


			// switch (textFromUser) {

			// case "ПР-151 сьогодні":
			// sendMsg(message, getTimetableOnTodayInOneMessage());
			// break;

			// InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
			// List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
			// List<InlineKeyboardButton> rowInline = new ArrayList<>();
			// rowInline.add(new InlineKeyboardButton().setText("Update message
			// text").setCallbackData("update_msg_text"));
			// // Set the keyboard to the markup
			// rowsInline.add(rowInline);
			// // Add it to the message
			// markupInline.setKeyboard(rowsInline);
			// message.setReplyMarkup(markupInline);

		} else if (update.hasCallbackQuery()) {
			// Set variables
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			long chat_id = update.getCallbackQuery().getMessage().getChatId();

			if (call_data.equals("update_msg_text")) {
				String answer = "Updated message text";
				EditMessageText new_message = new EditMessageText().setChatId(chat_id)
						.setMessageId((int) message_id).setText(answer);
				try {
					execute(new_message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private String getTimetableOnTodayInOneMessage() {
		StringBuilder timetableForUser = new StringBuilder();
		LocalDate date = LocalDate.now();
		DayOfWeek dow = date.getDayOfWeek();

		timetableService.getListWithTimetable().stream().filter(x -> x.getDayOfWeek() == dow)
				.collect(Collectors.toList())
				.forEach(timetable -> timetableForUser.append(timetable.getDayOfWeek() + "\t"
						+ timetable.getNumberOfCouple() + "\t" + timetable.getPosition() + "\t"
						+ timetable.getGroup() + "\t" + timetable.getSubject() + "\t"
						+ timetable.getTeacher() + "\t" + timetable.getTeacherSecond() + "\t"
						+ timetable.getClassroom() + " " + timetable.getClassroomSecond() + "\n"));

		return timetableForUser.toString();
	}


	/**
	 * Function for send message from bot to telegram
	 * 
	 * @param message message - message information that telegram get
	 * @param text text-text that bot send to telegram
	 */
	public void sendMsg(Message message, String text) {
		boolean enableMarkdownMessage = false;
		System.out.println("and here I've got ");
		SendMessage messageForSend = new SendMessage();
		messageForSend.enableMarkdown(enableMarkdownMessage);
		messageForSend.setChatId(message.getChatId().toString());
		messageForSend.setText(text);

		try {
			execute(messageForSend);
		} catch (TelegramApiException e) {

		}
	}
}
