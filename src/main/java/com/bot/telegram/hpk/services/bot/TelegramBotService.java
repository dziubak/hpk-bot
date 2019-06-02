package com.bot.telegram.hpk.services.bot;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.TelegramUser;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import com.bot.telegram.hpk.services.ReplacementService;
import com.bot.telegram.hpk.services.TimetableService;
import com.bot.telegram.hpk.services.bot.command.BotCommandRegister;
import com.bot.telegram.hpk.services.handler.TelegramHandlerService;
import com.bot.telegram.hpk.services.util.JsonConverter;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	@Autowired
	private TelegramHandlerService telegramHandlerService;

	@Autowired
	private BotCommandRegister botCommandRegister;

	@Value("${telegram.bot.name}")
	private String botName;

	@Value("${telegram.bot.token}")
	private String botToken;

	static {
		ApiContextInitializer.init();
	}

	@PostConstruct
	public void initBot() {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(this);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		String userMessage = update.getMessage() != null ? update.getMessage().getText() : "NO_TEXT_DATA";
		String rowCallback = update.getCallbackQuery() != null ? update.getCallbackQuery().getData() : "NO_CALLBACK_DATA";

		long chatId = update.getMessage() != null ? update.getMessage().getChatId() :
				update.getCallbackQuery() != null ? update.getCallbackQuery().getMessage().getChatId() : 0;

		long start = System.currentTimeMillis();
		TelegramUser currentUser = telegramHandlerService.handleRequest(update, chatId);
		long stop = System.currentTimeMillis();

//		LOGGER.info( "USER request handling time : " + (stop - start));

		BtnPayload btnPayload = new BtnPayload( "BOT", "BOT", false );

		//it's mean that we receive callback data
		if( userMessage.equals("NO_TEXT_DATA") ) {
			btnPayload = JsonConverter.fromJsonToObj( rowCallback );
			userMessage = btnPayload.getCmd();
		}

		BotApiMethod<?> response = botCommandRegister.executeCommand(userMessage, update, btnPayload );

		//it'mean that command wasn't founded in the register.
		if ( response == null ) {

			if( currentUser.getMode() == UserMode.READ) {
				//get user mode command
				response = botCommandRegister.executeCommand(currentUser.getModeCommand(), update, btnPayload );
				//build response
				executeQuite( response );
			} else {
				//implement router for ??
//				LOGGER.info( "Bad User command: " + update.getMessage().getText() );
				executeQuite( buildResponseErrorMessage( chatId ) );
			}

		} else {
			if ( update.hasCallbackQuery() ) {
				executeQuite( new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()));
			}
			executeQuite( response );
		}

	}

	public <T extends Serializable, Method extends BotApiMethod<T>> void executeQuite(Method method) {
		try {
			execute(method);
		} catch (TelegramApiException e) {
			//TODO
			e.getStackTrace();
		}
	}

	/**
	 * Builds response message for undefined state of command.
	 * @param chatId the id of the chat.
	 * @return built simple text message.
	 */
	private BotApiMethod<?> buildResponseErrorMessage(final long chatId ) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(EmojiParser.parseToUnicode(":thought_balloon:") + " Щось я тебе не розумію, введи /start  \n" +
				"Тех. підтримка: @tuzhanskyi");

		return message;
	}

	public static BotApiMethod<?> buildCommandHandlingMessage(final long chatId ) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(EmojiParser.parseToUnicode(":tada:") + " Ой! Ти оновив мене до нової версії! \n" +
				"Введи команду /start " + EmojiParser.parseToUnicode(":arrow_down:") +
				"\nЯкщо виникли запитання: @tuzhanskyi");

		return message;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}


}
