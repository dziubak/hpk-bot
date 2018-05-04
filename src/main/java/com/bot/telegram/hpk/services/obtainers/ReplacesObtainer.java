package com.bot.telegram.hpk.services.obtainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bot.telegram.hpk.services.ReplacementService;


/**
 * 
 * @author alex
 *
 */
@Component
public class ReplacesObtainer implements IObtainerSerivice {

	@Autowired
	private ReplacementService replacementService;

	@Override
	public String obtaineMessage(String command) {

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
}
