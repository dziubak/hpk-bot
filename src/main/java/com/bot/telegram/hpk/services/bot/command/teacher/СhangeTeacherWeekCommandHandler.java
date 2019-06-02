package com.bot.telegram.hpk.services.bot.command.teacher;

import com.bot.telegram.hpk.component.model.api.Teacher;
import com.bot.telegram.hpk.component.model.api.TimetableTeacher;
import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.enums.DayOfWeek;
import com.bot.telegram.hpk.component.model.bot.enums.WeekType;
import com.bot.telegram.hpk.services.bot.command.BotCommand;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

import static com.bot.telegram.hpk.services.util.KeyboardUtils.buildTeacherLessonsDaysKeyboard;
import static com.bot.telegram.hpk.services.util.KeyboardUtils.buildTeacherLessonsResponse;

@Component
public class СhangeTeacherWeekCommandHandler extends BotCommand {

    private final static String GET_TEACHER_LESSONS = "/timetable/teacher/{teacherId}";
    private final static String GET_TEACHER_BY_ID = "/teacher/{teacherId}";

    @Value("${hpk.api.url}")
    private String hpkApiUrl;

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {

        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getMessage() != null ? update.getMessage().getMessageId() : update.getCallbackQuery().getMessage().getMessageId();


        //get teacherId
        //get week type to display
        //TOP_WEEK 8 c219d0 14 TOP_WEEK
        WeekType retrievedWeekType = WeekType.valueOf( btnPayload.getParam().split("~")[2]);
        WeekType opositeWeekType = buildOpositeWeekType( retrievedWeekType );
        String teacherId = btnPayload.getParam().split("~")[1];

        String weekType = retrievedWeekType.getFriendlyName().toLowerCase();

        DayOfWeek dayOfWeek = DayOfWeek.valueOf( btnPayload.getParam().substring( 0, 3 ) );

        RestTemplate restTemplate = new RestTemplate();

        String urlGetTeacher = hpkApiUrl.concat(GET_TEACHER_BY_ID.replace("{teacherId}", teacherId));

        Teacher teacher = restTemplate.getForObject(urlGetTeacher, Teacher.class);

        String urlForRequest = hpkApiUrl.concat(GET_TEACHER_LESSONS.replace("{teacherId}", teacherId))
                .concat("?position=").concat(weekType)
                .concat("&dayOfWeek=").concat(dayOfWeek.getFullEngName());

        ResponseEntity<List<TimetableTeacher>> responseEntity = restTemplate.exchange(urlForRequest, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<TimetableTeacher>>() {});

        List<TimetableTeacher> lessons = responseEntity.getBody();

        String lessonsText = buildTeacherLessonsResponse(lessons, teacher, retrievedWeekType.getFriendlyName());

        return KeyboardUtils.buildResponseMessage(chatId, lessonsText,
                buildTeacherLessonsDaysKeyboard( teacherId,
                        dayOfWeek.getName().substring(0, 2),
                        opositeWeekType.getFriendlyName(),
                        opositeWeekType.toString(),
                        dayOfWeek.toString()  ),
                messageId, true);

    }
}
