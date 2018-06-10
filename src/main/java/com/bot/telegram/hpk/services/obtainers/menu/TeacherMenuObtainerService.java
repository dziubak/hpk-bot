package com.bot.telegram.hpk.services.obtainers.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bot.telegram.hpk.component.dao.TimetableGroupDao;
import com.bot.telegram.hpk.services.obtainers.IObtainer;

/**
 * 
 * @author alex
 *
 */
@Component
public class TeacherMenuObtainerService implements IObtainer {

	@Autowired
	private TimetableGroupDao timetableGroupDao;

	/**
	 * return all teachers;
	 */
	@Override
	public String obtainMessage(String command) {
		System.out.println(timetableGroupDao.toString());
		return getAllTeacherListInOneMessage();
	}

	private String getAllTeacherListInOneMessage() {
		StringBuilder teacherForUser = new StringBuilder();

		timetableGroupDao.getAllTeachersList().forEach(teacher -> teacherForUser
				.append(teacher.getSurname() + "\t" + teacher.getName() + "\t" + teacher.getMiddleName() + "\t \n"));

		return teacherForUser.toString();
	}

	@Override
	public ReplyKeyboardMarkup obtainReplyKeyboardMarkup() {
		// TODO Auto-generated method stub
		return null;
	}
}
