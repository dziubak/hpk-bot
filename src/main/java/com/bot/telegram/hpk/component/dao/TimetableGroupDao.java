package com.bot.telegram.hpk.component.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bot.telegram.hpk.component.entities.Teacher;
import com.bot.telegram.hpk.component.entities.Timetable;

@Repository
public class TimetableGroupDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static String SQL_GET_TIMETABLE_GENERAL_FOR_GROUP = "SELECT tt.day_of_week, tt.number_of_couple, tt.`position`, g.name, "
			+ "t1.surname, t1.name, t1.middle_name, t2.surname, t2.name, t2.middle_name, s.name, c1.number, c2.number "
			+ "FROM hpk_bot.timetable tt LEFT JOIN hpk_bot.`group` g ON tt.group_id = g.id "
			+ "LEFT JOIN hpk_bot.teacher t1 ON tt.teacher_id = t1.id "
			+ "LEFT JOIN hpk_bot.teacher t2 ON tt.teacher_second_id = t2.id "
			+ "LEFT JOIN hpk_bot.classroom c1 ON tt.classroom_id = c1.id "
			+ "LEFT JOIN hpk_bot.classroom c2 ON tt.classroom_second_id = c2.id "
			+ "LEFT JOIN hpk_bot.`subject` s ON tt.subject_id = s.id WHERE g.name=?";

	private final static String SQL_GET_TIMETABLE_TODAY_FOR_GROUP = "SELECT tt.day_of_week, tt.number_of_couple, tt.`position`, g.name, "
			+ "t1.surname, t1.name, t1.middle_name, t2.surname, t2.name, t2.middle_name, s.name, c1.number, c2.number "
			+ "FROM hpk_bot.timetable tt LEFT JOIN hpk_bot.`group` g ON tt.group_id = g.id "
			+ "LEFT JOIN hpk_bot.teacher t1 ON tt.teacher_id = t1.id "
			+ "LEFT JOIN hpk_bot.teacher t2 ON tt.teacher_second_id = t2.id "
			+ "LEFT JOIN hpk_bot.classroom c1 ON tt.classroom_id = c1.id "
			+ "LEFT JOIN hpk_bot.classroom c2 ON tt.classroom_second_id = c2.id "
			+ "LEFT JOIN hpk_bot.`subject` s ON tt.subject_id = s.id WHERE g.name=? AND tt.day_of_week=DAYNAME(CURDATE())";

	private final static String SQL_GET_TIMETABLE_TOMORROW_FOR_GROUP = "SELECT tt.day_of_week, tt.number_of_couple, tt.`position`, g.name, "
			+ "t1.surname, t1.name, t1.middle_name, t2.surname, t2.name, t2.middle_name, s.name, c1.number, c2.number, tt.classroom "
			+ "FROM hpk_bot.timetable tt LEFT JOIN hpk_bot.`group` g ON tt.group_id = g.id "
			+ "LEFT JOIN hpk_bot.teacher t1 ON tt.teacher_id = t1.id "
			+ "LEFT JOIN hpk_bot.teacher t2 ON tt.teacher_second_id = t2.id "
			+ "LEFT JOIN hpk_bot.classroom c1 ON tt.classroom_id = c1.id "
			+ "LEFT JOIN hpk_bot.classroom c2 ON tt.classroom_second_id = c2.id "
			+ "LEFT JOIN hpk_bot.`subject` s ON tt.subject_id = s.id WHERE g.name=? AND tt.day_of_week=DAYNAME(CURDATE()+1)";

	private final static String SQL_GET_ALL_TEACHERS = "SELECT id, surname, name, middle_name, info FROM hpk_bot.teacher;";

	public class TimetableRowMapper implements RowMapper<Timetable> {
		@Override
		public Timetable mapRow(ResultSet rs, int rowNum) throws SQLException {
			Timetable timetable = new Timetable();
			timetable.setDayOfWeek(DayOfWeek.valueOf(rs.getString(1)));
			timetable.setNumberOfCouple(rs.getString(2));
			timetable.setPosition(rs.getString(3));
			timetable.setGroup(rs.getString(4));
			timetable.setTeacher(rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
			timetable.setTeacherSecond(rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10));
			timetable.setSubject(rs.getString(11));
			timetable.setClassroom(rs.getString(12));
			timetable.setClassroomSecond(rs.getString(13));

			return timetable;
		}
	}

	public List<Timetable> getTimetableGeneralForGroup(String nameGroup) {
		return jdbcTemplate.query(SQL_GET_TIMETABLE_GENERAL_FOR_GROUP, new TimetableRowMapper(),
				new Object[] { nameGroup });
	}

	public List<Timetable> getTimetableTodayForGroup(String nameGroup) {
		return jdbcTemplate.query(SQL_GET_TIMETABLE_TODAY_FOR_GROUP, new TimetableRowMapper(), nameGroup);
	}

	public List<Timetable> getTimetableTomorrowForGroup(String nameGroup) {
		return jdbcTemplate.query(SQL_GET_TIMETABLE_TOMORROW_FOR_GROUP, new TimetableRowMapper(), nameGroup);
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
