package com.bot.telegram.hpk.component.entities;

import java.time.DayOfWeek;

public class Timetable {
	private int id;
	private DayOfWeek dayOfWeek;
	private String numberOfCouple;
	private String position;
	private String group;
	private String subject;
	private String teacher;
	private String teacherSecond;
	private String classroom;
	private String classroomSecond;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getNumberOfCouple() {
		return numberOfCouple;
	}

	public void setNumberOfCouple(String numberOfCouple) {
		this.numberOfCouple = numberOfCouple;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getTeacherSecond() {
		return teacherSecond;
	}

	public void setTeacherSecond(String teacherSecond) {
		this.teacherSecond = teacherSecond;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getClassroomSecond() {
		return classroomSecond;
	}

	public void setClassroomSecond(String classroomSecond) {
		this.classroomSecond = classroomSecond;
	}

}
