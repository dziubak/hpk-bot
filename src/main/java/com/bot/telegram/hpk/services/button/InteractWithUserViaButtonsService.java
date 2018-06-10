package com.bot.telegram.hpk.services.button;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class InteractWithUserViaButtonsService {

	public ReplyKeyboardMarkup createKeyboardMain() {
		// Create keyboard
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(true);

		// Create list of rows keyboard
		List<KeyboardRow> keyboard = new ArrayList<>();

		// First row keyboard
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		// Add buttons in first row keyboard
		keyboardFirstRow.add("groupMenu");
		keyboardFirstRow.add("teacherMenu");

		// Second row keyboard
		KeyboardRow keyboardSecondRow = new KeyboardRow();
		// Add buttons in second row keyboard
		keyboardSecondRow.add("🢂");

		// Add all rows keyboard in list
		keyboard.add(keyboardFirstRow);
		keyboard.add(keyboardSecondRow);

		// and set this list for our keyboard
		replyKeyboardMarkup.setKeyboard(keyboard);

		return replyKeyboardMarkup;
	}

	public ReplyKeyboardMarkup createKeyboardTimetableGroup() {
		// Create keyboard
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(true);

		// Create list of rows keyboard
		List<KeyboardRow> keyboard = new ArrayList<>();

		// First row keyboard
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		// Add buttons in first row keyboard
		keyboardFirstRow.add("timetableTodayForGroupInput");
		keyboardFirstRow.add("timetableTomorrowForGroupInput");

		// Second row keyboard
		KeyboardRow keyboardSecondRow = new KeyboardRow();
		// Add buttons in second row keyboard
		keyboardSecondRow.add("timetableGeneralForGroupInput");
		keyboardSecondRow.add("<-");

		// Add all rows keyboard in list
		keyboard.add(keyboardFirstRow);
		keyboard.add(keyboardSecondRow);

		// and set this list for our keyboard
		replyKeyboardMarkup.setKeyboard(keyboard);

		return replyKeyboardMarkup;
	}

}
