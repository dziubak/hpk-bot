package com.bot.telegram.hpk;

import com.bot.telegram.hpk.services.bot.command.*;
import com.bot.telegram.hpk.services.bot.command.group.*;
import com.bot.telegram.hpk.services.bot.command.teacher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

import static com.bot.telegram.hpk.services.bot.command.CommandNames.*;

@SpringBootApplication
public class TelegramBotHpkApplication {

	@Autowired
	private BotCommandRegister botCommandRegister;

	@Autowired
	private StartCommandHandler startCommandHandler;

	@Autowired
	private ShowLessonTimeCommandHandler showLessonTimeCommandHandler;

	@Autowired
	private StartPointTimetableCommandHandler startPointTimetableCommandHandler;

	@Autowired
	private OutOfSubMenuCommandHandler outOfSubMenuCommandHandler;

	@Autowired
	private TeacherScheduleCommandHandler teacherScheduleCommandHandler;

	@Autowired
	private GroupScheduleCommandHandler groupScheduleCommandHandler;

	@Autowired
	private CloseCommandHandler closeCommandHandler;

	//Teacher
	@Autowired
	private ReadTeacherNameCommandHandler readTeacherNameCommandHandler;

	@Autowired
	private ChangeTeacherNameCommandHandler changeTeacherNameCommandHandler;

	@Autowired
	private ShowTeacherLessonsCommandHandler showTeacherLessonsCommandHandler;

	@Autowired
	private ChangeTeacherDayCommandHandler changeTeacherDayCommandHandler;

	@Autowired
	private СhangeTeacherWeekCommandHandler сhangeTeacherWeekCommandHandler;


	//Group
	@Autowired
	private ReadGroupNameCommandHandler readGroupNameCommandHandler;

	@Autowired
	private ChangeGroupNameCommandHandler changeGroupNameCommandHandler;

	@Autowired
	private ShowGroupLessonsCommandHandler showGroupLessonsCommandHandler;

	@Autowired
	private ChangeGroupDayCommandHandler changeGroupDayCommandHandler;

	@Autowired
	private СhangeGroupWeekCommandHandler сhangeGroupWeekCommandHandler;


	public static void main(String[] args) {
		SpringApplication.run(TelegramBotHpkApplication.class, args);
	}

	@PostConstruct
	public void buildCommands(){
		botCommandRegister.registerCommand("/start", startCommandHandler);
		botCommandRegister.registerCommand(E_SHOW_LESSON_TIME, showLessonTimeCommandHandler );
		botCommandRegister.registerCommand(E_LESSONS, startPointTimetableCommandHandler);
		botCommandRegister.registerCommand(GENERAL_MAIN_MENU, outOfSubMenuCommandHandler);
		botCommandRegister.registerCommand(TEACHERS, teacherScheduleCommandHandler);
		botCommandRegister.registerCommand(GROUPS, groupScheduleCommandHandler);
		botCommandRegister.registerCommand(CLOSE_BTN, closeCommandHandler);

		//teachers
		botCommandRegister.registerCommand(READ_TEACHER_NAME, readTeacherNameCommandHandler);
		botCommandRegister.registerCommand( CHANGE_TEACHER_NAME, changeTeacherNameCommandHandler );
		botCommandRegister.registerCommand( SHOW_TEACHER_LESSONS, showTeacherLessonsCommandHandler );
		botCommandRegister.registerCommand(UPDATE_TEACHER_DAY_LESSONS, changeTeacherDayCommandHandler);
		botCommandRegister.registerCommand(CHANGE_TEACHER_WEEK, сhangeTeacherWeekCommandHandler );

		//groups
		botCommandRegister.registerCommand(READ_GROUP_NAME, readGroupNameCommandHandler);
		botCommandRegister.registerCommand(CHANGE_GROUP_NAME, changeGroupNameCommandHandler );
		botCommandRegister.registerCommand(SHOW_GROUP_LESSONS, showGroupLessonsCommandHandler );
		botCommandRegister.registerCommand(UPDATE_GROUP_DAY_LESSONS, changeGroupDayCommandHandler);
		botCommandRegister.registerCommand(CHANGE_GROUP_WEEK, сhangeGroupWeekCommandHandler );

	}
}
