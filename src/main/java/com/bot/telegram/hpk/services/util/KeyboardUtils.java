package com.bot.telegram.hpk.services.util;

import com.bot.telegram.hpk.component.model.api.Group;
import com.bot.telegram.hpk.component.model.api.Teacher;
import com.bot.telegram.hpk.component.model.api.TimetableGroup;
import com.bot.telegram.hpk.component.model.api.TimetableTeacher;
import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.DayOfWeek;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bot.telegram.hpk.services.bot.command.CommandNames.*;
import static com.bot.telegram.hpk.services.util.ButtonUtils.buildBackButton;
import static com.bot.telegram.hpk.services.util.JsonConverter.objToString;
import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.*;

public class KeyboardUtils {

    /**
     * Builds message for the regular incoming message.
     * @param chatId
     * @param text
     * @param markup
     * @return
     */
    public static BotApiMethod<?> buildResponseMessage(long chatId, int messageId, String text, ReplyKeyboard markup) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        message.setReplyMarkup(markup);

        return message;
    }

    public static InlineKeyboardMarkup buildTryAgainKeyBoard(final String commandName ) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        rowsInline.add(
                Arrays.asList(
                        new InlineKeyboardButton().setText( TYPE_AGAIN )
                                .setCallbackData(objToString(new BtnPayload( commandName, "", false)))
                )
        );


        markup.setKeyboard(rowsInline);

        return markup;
    }

    public static InlineKeyboardMarkup buildTeacherNamesKeyboard(final List<Teacher> teacherStates ) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Teacher teacher : teacherStates ) {
            rowsInline.add(
                    Arrays.asList(
                            new InlineKeyboardButton().setText(teacher.getSurname() + " " + teacher.getName() + " " + teacher.getMiddleName())
                                    .setCallbackData(objToString(new BtnPayload( SHOW_TEACHER_LESSONS, String.valueOf(teacher.getId()), false)))
                    )
            );
        }

        rowsInline.add(
                Arrays.asList(
                        new InlineKeyboardButton().setText( CHANGE_TEACHER_NAME_TEXT )
                                .setCallbackData(objToString(new BtnPayload( CHANGE_TEACHER_NAME, "", false)))
                )
        );

        markup.setKeyboard(rowsInline);

        return markup;
    }

    public static InlineKeyboardMarkup buildGroupNamesKeyboard(final List<Group> groupList ) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Group group : groupList ) {
            rowsInline.add(
                    Arrays.asList(
                            new InlineKeyboardButton().setText(group.getCourse() + " | " + group.getName())
                                    .setCallbackData(objToString(new BtnPayload( SHOW_GROUP_LESSONS, String.valueOf(group.getId()), false)))
                    )
            );
        }

        rowsInline.add(
                Arrays.asList(
                        new InlineKeyboardButton().setText( CHANGE_GROUP_NAME_TEXT )
                                .setCallbackData(objToString(new BtnPayload( CHANGE_GROUP_NAME, "", false)))
                )
        );

        markup.setKeyboard(rowsInline);

        return markup;
    }

    public static ReplyKeyboardMarkup buildNewMainMenuReplyKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Create a keyboard row
        KeyboardRow lessonsAndTeachers = new KeyboardRow();
        KeyboardRow timeAndNotifications = new KeyboardRow();

        lessonsAndTeachers.add(E_LESSONS);

        timeAndNotifications.add(E_SHOW_LESSON_TIME);

        keyboard.addAll( Arrays.asList( lessonsAndTeachers, timeAndNotifications ) );

        replyKeyboardMarkup.setResizeKeyboard( true );
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup buildTimetableStartPointKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow scheduleAndRating = new KeyboardRow();
        KeyboardRow mainMany = new KeyboardRow();
        scheduleAndRating.add(GROUPS);
        scheduleAndRating.add(TEACHERS);

        mainMany.add(GENERAL_MAIN_MENU);
        keyboard.addAll( Arrays.asList( scheduleAndRating, mainMany ) );

        replyKeyboardMarkup.setResizeKeyboard( true );
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
        
    }

    /**
     * Builds a keyboard with defined buttons and prefixes.
     * @param buttonNames a collection of button names.
     * @param command callback command name.
     * @return a built keyboard.
     */
    public static InlineKeyboardMarkup buildInlineKeyboardMarkup(List<String> buttonNames, String command, List<InlineKeyboardButton> backBtn, int colCount ) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();


        int counter = 0;
        int globalCounter = 1;

        for (String btnName : buttonNames) {
            globalCounter++;

            rowInline.add( new InlineKeyboardButton().setText( btnName )
                    .setCallbackData(objToString( new BtnPayload( command, btnName, true )
                            )
                    )
            );
            counter++;

            if (counter == colCount) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
                counter = 0;
            }
        }

        if( buttonNames.size() - (globalCounter) < 3) {
            for( int j = globalCounter - 1; j < buttonNames.size(); j++){
                String btnName = buttonNames.get(j);
                rowInline.add(new InlineKeyboardButton().setText( btnName )
                        .setCallbackData(objToString( new BtnPayload( command, btnName, false )
                                )
                        )
                );
            }

            rowsInline.add(rowInline);
        }


        markup.setKeyboard(rowsInline);
        markup.getKeyboard().add(backBtn);

        return markup;
    }

    public static InlineKeyboardMarkup buildInlineCourseMarkup(List<String> cellNames, String command, List<InlineKeyboardButton> backBtn) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        for (String name : cellNames ) {
            rowInline.add(new InlineKeyboardButton().setText(name + " курс")
                    .setCallbackData(objToString(new BtnPayload( command, name, false))));
        }

        rowsInline.add(rowInline);

        markup.setKeyboard(rowsInline);

        //Back button
        markup.getKeyboard().add(backBtn);

        return markup;
    }

    public static String buildTeacherLessonsResponse(List<TimetableTeacher> lessons,
                                                      Teacher teacher,
                                                     String weekName ) {
        StringBuilder sb = new StringBuilder();

        sb
                .append(EmojiParser.parseToUnicode(":mortar_board: ")).append("Викладач: ").append("<b>")
                .append(teacher.getSurname() + " " + teacher.getName() + " " + teacher.getMiddleName())
                .append("</b>").append("\n")
                //TODO
//                .append(EmojiParser.parseToUnicode(":briefcase: ")).append("Кафедра: ").append("<b>").append( teacherData.getDepartment() ).append("</b>").append("\n")
                .append(EmojiParser.parseToUnicode(":date: ")).append("Тиждень: ").append("<b>").append( weekName ).append("</b>").append("\n");

                if ( lessons.size() == 0 ) {
                    sb.append("~~~~~~~~~~~~~\n")
                            .append(EmojiParser.parseToUnicode(":tada: ")).append("Схоже в цей день пар немає!");
                }

                for ( TimetableTeacher lesson : lessons ) {
                    sb.append("~~~~~~~~~~~~~\n")
                            .append("<b>" + lesson.getNumberOfCouple() + " " + lesson.getSubjectName() + "</b>\n");

                    String classroom =  lesson.getClassroom() != null ?  lesson.getClassroom() : "";
                    sb.append(EmojiParser.parseToUnicode(":round_pushpin: ")  + classroom.replace("&nbsp;", " ") + "\n");

                    sb.append(EmojiParser.parseToUnicode(":cat:") + lesson.getGroupName()).append("\n");
                    sb.append(EmojiParser.parseToUnicode(":clock4: ") + getTimeForCouple(lesson.getNumberOfCouple())).append( "\n" );
                }

                return sb.toString();
    }

    public static String buildGroupLessonsResponse(List<TimetableGroup> lessons,
                                                     Group group,
                                                     String weekName) {
        StringBuilder sb = new StringBuilder();

        sb
                .append(EmojiParser.parseToUnicode(":mortar_board: ")).append("Група: ").append("<b>")
                .append(group.getName())
                .append("</b>").append("\n")

                .append(EmojiParser.parseToUnicode(":mortar_board: ")).append("Курс: ").append("<b>")
                .append(group.getCourse())
                .append("</b>").append("\n")

                .append(EmojiParser.parseToUnicode(":date: ")).append("Тиждень: ").append("<b>").append( weekName ).append("</b>").append("\n");

        if ( lessons.size() == 0 ) {
            sb.append("~~~~~~~~~~~~~\n")
                    .append(EmojiParser.parseToUnicode(":tada: ")).append("Схоже в цей день пар немає!");
        }

        for (TimetableGroup lesson : lessons ) {
            sb.append("~~~~~~~~~~~~~\n")
                    .append("<b>" + lesson.getNumberOfCouple() + " " + lesson.getSubjectName() + "</b>\n");

            String classroomNumber = lesson.getClassroom() != null ? lesson.getClassroom() : "";
            sb.append(EmojiParser.parseToUnicode(":round_pushpin: ")  + classroomNumber.replace("&nbsp;", " ") + "\n");

            sb.append(EmojiParser.parseToUnicode(":clock4: ") + getTimeForCouple(lesson.getNumberOfCouple()) ).append( "\n" );
        }

        return sb.toString();
    }

    private static String getTimeForCouple(String numberOfCouple){
        if(numberOfCouple.equals("1-2")){
            numberOfCouple = "1";
        }
        if(numberOfCouple.equals("3-4")){
            numberOfCouple = "2";
        }
        if(numberOfCouple.equals("5-6")){
            numberOfCouple = "3";
        }
        if(numberOfCouple.equals("7-8")){
            numberOfCouple = "4";
        }
        return PAIR_TIME.get(numberOfCouple);
    }

    /**
     * Defines a start and end time for the lesson.
     * @param numberOfLesson provided number of the lesson.
     * @return start and end string time.
     */
    public static String retrieveLessonTime( int numberOfLesson ) {
        return PAIR_TIME.get(numberOfLesson);
    }

    /**
     * Checks dates for equality.
     * @param date date to compare.
     * @return boolean value of compar. result.
     */
    private static boolean isDateNearest( Instant date ) {
        Instant now = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

        return now.isBefore(date) || now.compareTo(date) == 0;
    }

    /**
     * Checks dates for equality.
     * @param date date to compare.
     * @return boolean value of comparation result.
     */
    private static boolean isEventDatePast( Instant date ) {
        Instant now = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

        return date.isBefore( now );
    }

    /**
     * Builds EditMessage as response for the callback message.
     * @param chatId the id of the chat.
     * @param text a text of the message.
     * @param markup a keyboard.
     * @param messageId the id of the message.
     * @param isHtml means to change a mode.
     * @return edited message for the callback.
     */
    public static BotApiMethod<?> buildResponseMessage(long chatId, String text, InlineKeyboardMarkup markup, int messageId, boolean isHtml) {
        EditMessageText message = new EditMessageText();
        message.setText(text);
        message.setChatId(chatId);
        message.setReplyMarkup(markup);
        message.setMessageId(messageId);

        if(isHtml) {
            message.setParseMode("html");
        }

        return message;
    }

    /**
     * Builds SendMessage as response for the callback message.
     * @param chatId the id of the chat.
     * @param text a text of the message.
     * @param markup a keyboard.
     * @param isHtml means to change a mode.
     * @return edited message for the callback.
     */
    public static BotApiMethod<?> buildResponseMessage(long chatId, String text, InlineKeyboardMarkup markup, boolean isHtml) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);

        if( markup != null ){
            message.setReplyMarkup(markup);
        }

        if( isHtml ) {
            message.setParseMode("html");
        }

        return message;
    }

    public static InlineKeyboardMarkup buildTeacherLessonsDaysKeyboard(String teacherId,
                                                                         String currentDayOfWeek,
                                                                         String originWeekName,
                                                                         String opositeWeekName,
                                                                         String dayOfWeek ) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add( buildBackButton( markAsSelected( "Пн", currentDayOfWeek ),
                new BtnPayload(UPDATE_TEACHER_DAY_LESSONS, DayOfWeek.MON.toString().concat("~").concat(teacherId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Вт", currentDayOfWeek ),
                new BtnPayload(UPDATE_TEACHER_DAY_LESSONS, DayOfWeek.TUE.toString().concat("~").concat(teacherId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Ср", currentDayOfWeek ),
                new BtnPayload(UPDATE_TEACHER_DAY_LESSONS, DayOfWeek.WED.toString().concat("~").concat(teacherId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Чт", currentDayOfWeek ),
                new BtnPayload(UPDATE_TEACHER_DAY_LESSONS, DayOfWeek.THU.toString().concat("~").concat(teacherId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Пт", currentDayOfWeek ),
                new BtnPayload(UPDATE_TEACHER_DAY_LESSONS, DayOfWeek.FRI.toString().concat("~").concat(teacherId), false)) );

        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();

        rowInline.add(
                buildBackButton( EmojiParser.parseToUnicode(":date: ") + originWeekName,
                        new BtnPayload( CHANGE_TEACHER_WEEK, dayOfWeek.concat("~").concat(teacherId).concat("~".concat(opositeWeekName)), false))
        );

        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();

        rowInline.add(
                buildBackButton( CLOSE,
                        new BtnPayload(CLOSE_BTN, "teacher", false))
        );

        rowsInline.add(rowInline);

        markup.setKeyboard(rowsInline);

        return markup;
    }

    public static InlineKeyboardMarkup buildGroupLessonsDaysKeyboard(String groupId,
                                                                       String currentDayOfWeek,
                                                                       String originWeekName,
                                                                       String opositeWeekName,
                                                                       String dayOfWeek ) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add( buildBackButton( markAsSelected( "Пн", currentDayOfWeek ),
                new BtnPayload(UPDATE_GROUP_DAY_LESSONS, DayOfWeek.MON.toString().concat("~").concat(groupId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Вт", currentDayOfWeek ),
                new BtnPayload(UPDATE_GROUP_DAY_LESSONS, DayOfWeek.TUE.toString().concat("~").concat(groupId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Ср", currentDayOfWeek ),
                new BtnPayload(UPDATE_GROUP_DAY_LESSONS, DayOfWeek.WED.toString().concat("~").concat(groupId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Чт", currentDayOfWeek ),
                new BtnPayload(UPDATE_GROUP_DAY_LESSONS, DayOfWeek.THU.toString().concat("~").concat(groupId), false)) );

        rowInline.add( buildBackButton( markAsSelected( "Пт", currentDayOfWeek ),
                new BtnPayload(UPDATE_GROUP_DAY_LESSONS, DayOfWeek.FRI.toString().concat("~").concat(groupId), false)) );

        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();

        rowInline.add(
                buildBackButton( EmojiParser.parseToUnicode(":date: ") + originWeekName,
                        new BtnPayload(CHANGE_GROUP_WEEK, dayOfWeek.concat("~").concat(groupId).concat("~".concat(opositeWeekName)), false))
        );

        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();

        rowInline.add(
                buildBackButton( CLOSE,
                        new BtnPayload(CLOSE_BTN, "group", false))
        );

        rowsInline.add(rowInline);

        markup.setKeyboard(rowsInline);

        return markup;
    }

    private static String markAsSelected( String name, String toCheck ) {
        return name.equals(toCheck) ? EmojiParser.parseToUnicode(":arrow_right: ") + name  : name;
    }

}