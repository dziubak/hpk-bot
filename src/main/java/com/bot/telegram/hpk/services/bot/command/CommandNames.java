package com.bot.telegram.hpk.services.bot.command;

import com.vdurmont.emoji.EmojiParser;

/**
 * Container for command names.
 */
public class CommandNames {

    //General
    public static final String GENERAL_MAIN_MENU = EmojiParser.parseToUnicode( ":arrow_backward:") + " Меню";
    public static final String TEACHERS = EmojiParser.parseToUnicode( ":mortar_board:" ) + " Викладачі";
    public static final String GROUPS = EmojiParser.parseToUnicode( ":school_satchel:" ) + " Групи";
    public static final String CLOSE_BTN = "close";

    //Teachers
    public static final String READ_TEACHER_NAME = "tchName";
    public static final String SHOW_TEACHER_LESSONS = "tchLsn";
    public static final String UPDATE_TEACHER_DAY_LESSONS = "tchDay";
    public static final String CHANGE_TEACHER_NAME = "chgTchName";
    public static final String CHANGE_TEACHER_WEEK = "cTchW";

    //Groups
    public static final String READ_GROUP_NAME = "grpName";
    public static final String SHOW_GROUP_LESSONS = "grpLsn";
    public static final String UPDATE_GROUP_DAY_LESSONS = "grpDay";
    public static final String CHANGE_GROUP_NAME = "chgGrpName";
    public static final String CHANGE_GROUP_WEEK = "cGrpW";

    //NEW
    public static final String E_LESSONS = EmojiParser.parseToUnicode(":pencil: ") + "Розклад занять";
    public static final String E_SHOW_LESSON_TIME = EmojiParser.parseToUnicode(":hourglass: ") + "Час занять";
}
