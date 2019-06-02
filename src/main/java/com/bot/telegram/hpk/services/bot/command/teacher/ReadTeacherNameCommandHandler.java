package com.bot.telegram.hpk.services.bot.command.teacher;

import com.bot.telegram.hpk.component.model.api.Teacher;
import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import com.bot.telegram.hpk.services.bot.command.BotCommand;
import com.bot.telegram.hpk.services.user.TelegramUserService;
import com.bot.telegram.hpk.services.util.KeyboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

import static com.bot.telegram.hpk.services.bot.command.CommandNames.CHANGE_TEACHER_NAME;
import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.FOUNDED_TEACHERS;

@Component
public class ReadTeacherNameCommandHandler extends BotCommand {

    @Autowired
    private TelegramUserService telegramUserService;

    @Value("${hpk.api.url}")
    private String hpkApiUrl;

    private final static String GET_TEACHER_ENDPOINT = "/teacher";

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {
        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getMessage().getMessageId();

        String teacherName = update.getMessage().getText();

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(hpkApiUrl.concat(GET_TEACHER_ENDPOINT))
                .queryParam("teacherSurname", teacherName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity(headers);

        //check teacher repository
        ResponseEntity<List<Teacher>> response = restTemplate.exchange(
                hpkApiUrl.concat(GET_TEACHER_ENDPOINT).concat("?teacherSurname=").concat(teacherName),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Teacher>>(){});
        List<Teacher> teachers = response.getBody();

        telegramUserService.updateUserModeAndCommandAndTeacherIdByUserId(chatId, UserMode.WRITE, "NONE_CMD", "NONE_ID" );

        if( !verifyTeachers( teachers ) ) {
            String userMessage = "";
            if ( teachers.size() == 0 ) {
                userMessage = "Мені не вдалось знайти викладача. Можливо помилка в прізвищі?";
            }else if ( teachers.size() > 10 ) {
                userMessage = "Я знайшов " + teachers.size() + " викладачів за твоїм запитом. Спробуй вказати точніше прізвище.";
            }

            return KeyboardUtils.buildResponseMessage( chatId, messageId, userMessage, KeyboardUtils.buildTryAgainKeyBoard( CHANGE_TEACHER_NAME ) );
        }

        return KeyboardUtils.buildResponseMessage(chatId, messageId, FOUNDED_TEACHERS, KeyboardUtils.buildTeacherNamesKeyboard(teachers) );
    }

    private boolean verifyTeachers( final List<Teacher> teacherStates ) {

        if ( teacherStates.size() == 0 || teacherStates.size() > 10 ) {
            return false;
        }

        return true;
    }

}
