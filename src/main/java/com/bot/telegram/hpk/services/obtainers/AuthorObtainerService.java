package com.bot.telegram.hpk.services.obtainers;

/**
 * 
 * @author Alexander Torchynskyi
 *
 */
public class AuthorObtainerService implements IObtainerSerivice {

	@Override
	public String obtaineMessage(String command) {
		return "(с) Team: \n Бог Рустам в ролі Абдула Рустама \n 'Князь Льоха-Лєпьоха в ролі Дорожанського Олексія \n Кріпак Ваня - геній відмазок в ролі Горбатюка Івана";
	}
}
