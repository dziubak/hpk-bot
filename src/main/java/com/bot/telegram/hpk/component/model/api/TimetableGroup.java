package com.bot.telegram.hpk.component.model.api;

import java.time.DayOfWeek;

public class TimetableGroup {
	private DayOfWeek dayOfWeek;
	private String numberOfCouple;
	private String position;
	private String groupName;
	private String subjectName;
	private String subjectAbbreviation;
	private String teacher;
	private String teacherSecond;
	private String classroom;
	private String classroomSecond;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}

	public void setSubjectAbbreviation(String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
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
