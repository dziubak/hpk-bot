package com.bot.telegram.hpk.services.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bot.telegram.hpk.services.obtainers.IObtainer;


/**
 * 
 * @author alex
 *
 */
@Component
public class ReplacesObtainer implements IObtainer {

	@Autowired
	private ReplacementService replacementService;

	@Override
	public String obtainMessage(String command) {

		return getAllReplacementsListInOneMessage();
	}

	private String getAllReplacementsListInOneMessage() {
		StringBuilder replacementsForUser = new StringBuilder();

		replacementService.getAllReplacements()
				.forEach(replacement -> replacementsForUser
						.append(replacement.getNameGroup() + "\t" + replacement.getNumberOfCouple()
								+ "\t" + replacement.getTeacherReplacement() + "\t"
								+ replacement.getSubject() + "\t" + replacement.getTeacher() + "\t"
								+ replacement.getClassroom() + "\n"));

		return replacementsForUser.toString();
	}

	@Override
	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup() {
		// TODO Auto-generated method stub
		return null;
	}
}
