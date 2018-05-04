package com.bot.telegram.hpk.services.obtainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bot.telegram.hpk.services.obtainers.groups.PR151Obtainer;
import com.bot.telegram.hpk.services.obtainers.teachers.TeacherObtainerService;

/**
 * 
 * @author alex
 *
 */
@Component
public class ObtainerLookUp {

	private AuthorObtainerService authorObtainerService;
	@Autowired
	private PR151Obtainer pr151Obtainer;
	@Autowired
	private HelpObtainerService helpObtainerService;
	@Autowired
	private TeacherObtainerService teacherObtainerService;
	@Autowired
	private DefaultObtainer defaultObtainer;
	@Autowired
	private ReplacesObtainer replacesObtainer;

	private List<String> listOfCommands;

	/**
	 * All commands that bot can accept located here. TODO delegating from delegate - find a way to
	 * handle many words in a command
	 * 
	 * @param command
	 * @return
	 */
	public String obtaine(String command) {

		if (command.equalsIgnoreCase("пр-151")) {
			return pr151Obtainer.obtaineMessage(command);
		}
		if (command.equalsIgnoreCase("teachers")) {
			return teacherObtainerService.obtaineMessage(command);
		}
		if (command.equalsIgnoreCase("help")) {
			return helpObtainerService.obtaineMessage(command);
		}
		if (command.equalsIgnoreCase("replaces")) {
			return replacesObtainer.obtaineMessage(command);
		}
		if (command.equalsIgnoreCase("authors")) {
			authorObtainerService = new AuthorObtainerService();
			return authorObtainerService.obtaineMessage(command);
		} else {
			return defaultObtainer.obtaineMessage(command);
		}
	}

	private int checkAmountOfCommands(String command) {
		listOfCommands = new ArrayList(Arrays.asList(command.split("\\+s")));
		return listOfCommands.size();
	}
}
