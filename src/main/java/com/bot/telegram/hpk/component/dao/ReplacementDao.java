package com.bot.telegram.hpk.component.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bot.telegram.hpk.component.entities.Replacement;
import com.bot.telegram.hpk.component.entities.Teacher;

@Repository
public class ReplacementDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static String SQL_GET_ALL_REPLACEMENTS = "SELECT id, teacher_on_duty, group_on_duty, position, date_of_replacement, day_of_week,\n"
			+ "name_group, number_of_couple, teacher_replacement, subject, teacher, classroom FROM hpk_bot.replacement;";

	public List<Replacement> getAllReplacements() {
		List<Replacement> replacementsList = jdbcTemplate.query(SQL_GET_ALL_REPLACEMENTS, (rs, arg1) -> {
			Replacement replacement = new Replacement();
			replacement.setId(rs.getInt(1));
			replacement.setSurname(rs.getString(2));
			replacement.setName(rs.getString(3));
			replacement.setMiddleName(rs.getString(4));
			replacement.setInfo(rs.getString(5));

			return replacement;
		});

		return replacementsList;
	}
}
