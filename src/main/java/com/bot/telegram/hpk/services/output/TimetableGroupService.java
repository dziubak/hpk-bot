package com.bot.telegram.hpk.services.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bot.telegram.hpk.component.dao.TimetableGroupDao;
import com.bot.telegram.hpk.component.enums.FlagForDay;

@Service
public class TimetableGroupService {

	@Autowired
	private TimetableGroupDao timetableGroupDao;

	public static String FLAG = FlagForDay.GENERAL.toString();

	public String getTimetableForGroup(String nameGroup) {
		if (FLAG == FlagForDay.TODAY.toString()) {
			return getTimetableTodayForGroup(nameGroup);
		} else if (FLAG == FlagForDay.TOMORROW.toString()) {
			return getTimetableTomorrowForGroup(nameGroup);
		} else {
			return getTimetableGeneralForGroup(nameGroup);
		}
	}

	private String getTimetableGeneralForGroup(String nameGroup) {
		StringBuilder messageWithTimetableGeneralForGroup = new StringBuilder();

		timetableGroupDao.getTimetableGeneralForGroup(nameGroup)
				.forEach(couple -> messageWithTimetableGeneralForGroup.append(couple.getDayOfWeek() + "\t"
						+ couple.getNumberOfCouple() + "\t" + couple.getPosition() + "\t" + couple.getGroup() + "\t"
						+ couple.getSubject() + "\t" + couple.getTeacher() + "\t" + couple.getTeacherSecond() + "\t"
						+ couple.getClassroom() + " " + couple.getClassroomSecond() + "\n"));

		return messageWithTimetableGeneralForGroup.toString();
	}

	private String getTimetableTodayForGroup(String nameGroup) {
		StringBuilder messageWithTimetableTodayForGroup = new StringBuilder();

		timetableGroupDao.getTimetableTodayForGroup(nameGroup)
				.forEach(couple -> messageWithTimetableTodayForGroup.append(couple.getDayOfWeek() + "\t"
						+ couple.getNumberOfCouple() + "\t" + couple.getPosition() + "\t" + couple.getGroup() + "\t"
						+ couple.getSubject() + "\t" + couple.getTeacher() + "\t" + couple.getTeacherSecond() + "\t"
						+ couple.getClassroom() + " " + couple.getClassroomSecond() + "\n"));

		return messageWithTimetableTodayForGroup.toString();
	}

	private String getTimetableTomorrowForGroup(String nameGroup) {
		StringBuilder messageWithTimetableTomorrowForGroup = new StringBuilder();

		timetableGroupDao.getTimetableTomorrowForGroup(nameGroup)
				.forEach(couple -> messageWithTimetableTomorrowForGroup.append(couple.getDayOfWeek() + "\t"
						+ couple.getNumberOfCouple() + "\t" + couple.getPosition() + "\t" + couple.getGroup() + "\t"
						+ couple.getSubject() + "\t" + couple.getTeacher() + "\t" + couple.getTeacherSecond() + "\t"
						+ couple.getClassroom() + " " + couple.getClassroomSecond() + "\n"));

		return messageWithTimetableTomorrowForGroup.toString();
	}

}
