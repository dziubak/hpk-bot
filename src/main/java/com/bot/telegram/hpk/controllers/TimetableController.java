package com.bot.telegram.hpk.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bot.telegram.hpk.component.dao.TimetableDao;
import com.bot.telegram.hpk.component.entities.Timetable;

@RestController
public class TimetableController {

	@Autowired
	private TimetableDao timetableDao;

	@RequestMapping(method = RequestMethod.GET, value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	List<Timetable> getListWithTimetable() {
		List<Timetable> u = timetableDao.getListWithTimetable();
		return u;
	}

}
