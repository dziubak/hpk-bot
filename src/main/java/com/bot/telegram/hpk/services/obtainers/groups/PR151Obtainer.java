package com.bot.telegram.hpk.services.obtainers.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.bot.telegram.hpk.services.TimetableService;
import com.bot.telegram.hpk.services.obtainers.IObtainerSerivice;

/**
 * 
 * @author alex
 *
 */
@Component
public class PR151Obtainer implements IObtainerSerivice {

	@Autowired
	private TimetableService timetableService;

	/**
	 * return a schedule
	 */
	@Override
	public String obtaineMessage(String command) {
		System.out.println(timetableService.toString());
		return getTimetableInOneMessage();
	}

	private String getTimetableInOneMessage() {
		StringBuilder timetableForUser = new StringBuilder();

		timetableService.getListWithTimetable()
				.forEach(timetable -> timetableForUser.append(timetable.getDayOfWeek() + "\t"
						+ timetable.getNumberOfCouple() + "\t" + timetable.getPosition() + "\t"
						+ timetable.getGroup() + "\t" + timetable.getSubject() + "\t"
						+ timetable.getTeacher() + "\t" + timetable.getTeacherSecond() + "\t"
						+ timetable.getClassroom() + " " + timetable.getClassroomSecond() + "\n"));

		return timetableForUser.toString();
	}
}
