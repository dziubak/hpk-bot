package com.bot.telegram.hpk.component.entities.wrappers;

import com.bot.telegram.hpk.component.entities.Classroom;
import com.bot.telegram.hpk.component.entities.Group;
import com.bot.telegram.hpk.component.entities.Subject;
import com.bot.telegram.hpk.component.entities.Teacher;
import com.bot.telegram.hpk.component.entities.Timetable;

public class TimetableWrapper {
	private final Timetable timetable;
	private final Group group;
	private final Teacher teacherMain;
	private final Teacher teacherAdditional;
	private final Subject subject;
	private final Classroom classroomMain;
	private final Classroom classroomAdditional;

	public TimetableWrapper(Timetable timetable, Group group, Teacher teacherMain, Teacher teacherAdditional,
			Subject subject, Classroom classroomMain, Classroom classroomAdditional) {
		this.timetable = timetable;
		this.group = group;
		this.teacherMain = teacherMain;
		this.teacherAdditional = teacherAdditional;
		this.subject = subject;
		this.classroomMain = classroomMain;
		this.classroomAdditional = classroomAdditional;
	}

	public Timetable getTimetable() {
		return timetable;
	}

	public Group getGroup() {
		return group;
	}

	public Teacher getTeacherMain() {
		return teacherMain;
	}

	public Teacher getTeacherAdditional() {
		return teacherAdditional;
	}

	public Subject getSubject() {
		return subject;
	}

	public Classroom getClassroomMain() {
		return classroomMain;
	}

	public Classroom getClassroomAdditional() {
		return classroomAdditional;
	}

}
