package com.bot.telegram.hpk.component.dao;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bot.telegram.hpk.component.entities.Teacher;
import com.bot.telegram.hpk.component.entities.Timetable;

@Repository
public class TimetableDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static String SQL_GET_TIMETABLE = "SELECT tt.id, tt.day_of_week, tt.number_of_couple, tt.`position`, g.name, "
			+ "t1.surname, t1.name, t1.middle_name, t2.surname, t2.name, t2.middle_name, s.name, "
			+ "c1.number, c2.number FROM hpk_bot.timetable tt " + "LEFT JOIN `group` g ON tt.group_id = g.id "
			+ "LEFT JOIN teacher t1 ON tt.teacher_id = t1.id LEFT JOIN teacher t2 ON tt.teacher_second_id = t2.id "
			+ "LEFT JOIN classroom c1 ON tt.classroom_id = c1.id "
			+ "LEFT JOIN classroom c2 ON tt.classroom_second_id = c2.id JOIN `subject` s ON tt.subject_id = s.id;";

	private final static String SQL_GET_ALL_TEACHERS = "SELECT id, surname, name, middle_name, info FROM hpk_bot.teacher;";

	public List<Timetable> getListWithTimetable() {
		List<Timetable> resultList = jdbcTemplate.query(SQL_GET_TIMETABLE, (rs, arg1) -> {
			Timetable timetable = new Timetable();
			timetable.setId(rs.getInt(1));
			timetable.setDayOfWeek(DayOfWeek.valueOf(rs.getString(2)));
			timetable.setNumberOfCouple(rs.getString(3));
			timetable.setPosition(rs.getString(4));
			timetable.setGroup(rs.getString(5));
			timetable.setTeacher(rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8));
			timetable.setTeacherSecond(rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11));
			timetable.setSubject(rs.getString(12));
			timetable.setClassroom(rs.getString(13));
			timetable.setClassroomSecond(rs.getString(14));

			return timetable;
		});

		return resultList;

	}

	public List<Teacher> getAllTeachersList() {

		List<Teacher> teachersList = jdbcTemplate.query(SQL_GET_ALL_TEACHERS, (rs, arg1) -> {
			Teacher teacher = new Teacher();
			teacher.setId(rs.getInt(1));
			teacher.setSurname(rs.getString(2));
			teacher.setName(rs.getString(3));
			teacher.setMiddleName(rs.getString(4));
			teacher.setInfo(rs.getString(5));

			return teacher;
		});

		return teachersList;
	}

}
