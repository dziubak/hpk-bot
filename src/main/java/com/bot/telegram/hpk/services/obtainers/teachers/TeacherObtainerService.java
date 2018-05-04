package com.bot.telegram.hpk.services.obtainers.teachers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bot.telegram.hpk.services.TimetableService;
import com.bot.telegram.hpk.services.obtainers.IObtainerSerivice;


/**
 * 
 * @author alex
 *
 */
@Component
public class TeacherObtainerService implements IObtainerSerivice {

	@Autowired
	private TimetableService timetableService;

	/**
	 * return all teachers;
	 */
	@Override
	public String obtaineMessage(String command) {
		System.out.println(timetableService.toString());
		return getAllTeacherListInOneMessage();
	}

	private String getAllTeacherListInOneMessage() {
		StringBuilder teacherForUser = new StringBuilder();

		timetableService.getAllTeachersList()
				.forEach(teacher -> teacherForUser.append(teacher.getSurname() + "\t"
						+ teacher.getName() + "\t" + teacher.getMiddleName() + "\t \n"));

		return teacherForUser.toString();
	}
}
