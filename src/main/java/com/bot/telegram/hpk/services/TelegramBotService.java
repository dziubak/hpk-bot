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

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	private static final Logger log = Logger.getLogger(TelegramBotService.class);

	private final static String BOT_USERNAME = "TelegramBotHPK";
	private final static String BOT_TOKEN = "336211020:AAEVa8mCUJ8toCmRHr1P0SkdsZP52yNuMUE";

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

			switch (textFromUser) {
			case "ПР-151":
				sendMsg(message, getTimetableInOneMessage());
				break;
			case "ПР-151 сьогодні":
				sendMsg(message, getTimetableOnTodayInOneMessage());
				break;
			case "Автори":
				String authors = "(с) Team: \n 'Бог Рустам в ролі Абдула Рустама' \n 'Князь Льоха-Лєпьоха в ролі Дорожанського Олексія' \n 'Кріпак Ваня - геній відмазок в ролі Горбатюка Івана'";
				sendMsg(message, authors);
				break;
			case "help":
				String helpLine = "ПР-151 - розклад групи ПР-151 \n ПР-151 сьогодні - розклад групи ПР-151 на сьогодні \n Автори - інформація про розробників \n Викладачі - список викладачів";
				sendMsg(message, helpLine);
				break;
			case "Викладачі":
				sendMsg(message, getAllTeacherListInOneMessage());
				break;
			case "Заміни":
				sendMsg(message, getAllReplacementsListInOneMessage());
				break;
			case "Гуменна":
				break;
			}

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

		} else if (update.hasCallbackQuery()){
			// Set variables
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			long chat_id = update.getCallbackQuery().getMessage().getChatId();

			if (call_data.equals("update_msg_text")) {
				String answer = "Updated message text";
				EditMessageText new_message = new EditMessageText().setChatId(chat_id).setMessageId((int) message_id)
						.setText(answer);
				try {
					execute(new_message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getTimetableInOneMessage() {
		StringBuilder timetableForUser = new StringBuilder();
		// timetableService.getListWithTimetable().stream().filter(x -> x.getDayOfWeek()
		// == DayOfWeek.MONDAY)
		// .collect(Collectors.toList()).forEach(x -> timetableForUser.append(
		// x.getDayOfWeek() + "\t" + x.getSubject() + "\t" + x.getGroup() + "\t" +
		// x.getTeacher() + "\n"));

		timetableService.getListWithTimetable().forEach(timetable -> timetableForUser.append(timetable.getDayOfWeek()
				+ "\t" + timetable.getNumberOfCouple() + "\t" + timetable.getPosition() + "\t" + timetable.getGroup()
				+ "\t" + timetable.getSubject() + "\t" + timetable.getTeacher() + "\t" + timetable.getTeacherSecond()
				+ "\t" + timetable.getClassroom() + " " + timetable.getClassroomSecond() + "\n"));

		return timetableForUser.toString();
	}

	private String getTimetableOnTodayInOneMessage() {
		StringBuilder timetableForUser = new StringBuilder();
		LocalDate date = LocalDate.now();
		DayOfWeek dow = date.getDayOfWeek();

		timetableService.getListWithTimetable().stream().filter(x -> x.getDayOfWeek() == dow)
				.collect(Collectors.toList())
				.forEach(timetable -> timetableForUser
						.append(timetable.getDayOfWeek() + "\t" + timetable.getNumberOfCouple() + "\t"
								+ timetable.getPosition() + "\t" + timetable.getGroup() + "\t" + timetable.getSubject()
								+ "\t" + timetable.getTeacher() + "\t" + timetable.getTeacherSecond() + "\t"
								+ timetable.getClassroom() + " " + timetable.getClassroomSecond() + "\n"));

		return timetableForUser.toString();
	}

	private String getAllTeacherListInOneMessage() {
		StringBuilder teacherForUser = new StringBuilder();

		timetableService.getAllTeachersList().forEach(teacher -> teacherForUser
				.append(teacher.getSurname() + "\t" + teacher.getName() + "\t" + teacher.getMiddleName() + "\t \n"));

		return teacherForUser.toString();
	}

	private String getAllReplacementsListInOneMessage() {
		StringBuilder replacementsForUser = new StringBuilder();

		replacementService.getAllReplacements()
				.forEach(replacement -> replacementsForUser
						.append(replacement.getNameGroup() + "\t" + replacement.getNumberOfCouple() + "\t"
								+ replacement.getTeacherReplacement() + "\t" + replacement.getSubject() + "\t"
								+ replacement.getTeacher() + "\t" + replacement.getClassroom() + "\n"));

		return replacementsForUser.toString();
	}

	/**
	 * Function for send message from bot to telegram
	 * 
	 * @param message
	 *            message - message information that telegram get
	 * @param text
	 *            text-text that bot send to telegram
	 */
	private void sendMsg(Message message, String text) {
		boolean enableMarkdownMessage = false;

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
