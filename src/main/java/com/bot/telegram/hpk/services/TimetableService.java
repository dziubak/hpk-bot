package com.bot.telegram.hpk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bot.telegram.hpk.component.dao.TimetableDao;
import com.bot.telegram.hpk.component.entities.Teacher;
import com.bot.telegram.hpk.component.entities.Timetable;

@Service
public class TimetableService {

	@Autowired
	private TimetableDao timetableDao;

	public List<Timetable> getListWithTimetable() {
		return timetableDao.getListWithTimetable();
	}

	public List<Teacher> getAllTeachersList() {
		return timetableDao.getAllTeachersList();
	}

}
