package com.bot.telegram.hpk.services.output;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bot.telegram.hpk.services.obtainers.IObtainer;

/**
 * 
 * @author Alexander Torchynskyi
 *
 */
@Service
public class AuthorObtainerService implements IObtainer {

	@Override
	public String obtainMessage(String command) {
		return "(с) Team: \n Бог Рустам в ролі Абдула Рустама \n 'Князь Льоха-Лєпьоха в ролі Дорожанського Олексія \n Кріпак Ваня - геній відмазок в ролі Горбатюка Івана";
	}

	@Override
	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup() {
		// TODO Auto-generated method stub
		return null;
	}
}
