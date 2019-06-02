package com.bot.telegram.hpk.services.bot.command.group;

import com.bot.telegram.hpk.component.model.api.Group;
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

import static com.bot.telegram.hpk.services.bot.command.CommandNames.CHANGE_GROUP_NAME;
import static com.bot.telegram.hpk.services.util.ScheduleUIConstants.FOUNDED_GROUPS;

@Component
public class ReadGroupNameCommandHandler extends BotCommand {

    @Autowired
    private TelegramUserService telegramUserService;

    @Value("${hpk.api.url}")
    private String hpkApiUrl;

    private final static String GET_GROUP_ENDPOINT = "/group";

    @Override
    public BotApiMethod<?> handle(Update update, BtnPayload btnPayload) {
        long chatId = update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getMessage().getMessageId();

        String groupName = update.getMessage().getText();

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(hpkApiUrl.concat(GET_GROUP_ENDPOINT))
                .queryParam("groupName", groupName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity(headers);

        //check teacher repository
        ResponseEntity<List<Group>> response = restTemplate.exchange(
                hpkApiUrl.concat(GET_GROUP_ENDPOINT).concat("?groupName=").concat(groupName),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Group>>(){});
        List<Group>  groups = response.getBody();

        telegramUserService.updateUserModeAndCommandAndTeacherIdByUserId(chatId, UserMode.WRITE, "NONE_CMD", "NONE_ID" );

        if( !verifyGroups( groups ) ) {
            String userMessage = "";
            if ( groups.size() == 0 ) {
                userMessage = "Не вдалось знайти групу. Можливо помилка в назві?";
            }else if ( groups.size() > 10 ) {
                userMessage = "Знайдено " + groups.size() + " груп за запитом. Спробуй вказати точніше назву групи.";
            }

            return KeyboardUtils.buildResponseMessage( chatId, messageId, userMessage, KeyboardUtils.buildTryAgainKeyBoard( CHANGE_GROUP_NAME ) );
        }

        return KeyboardUtils.buildResponseMessage(chatId, messageId, FOUNDED_GROUPS, KeyboardUtils.buildGroupNamesKeyboard(groups) );
    }

    private boolean verifyGroups(final List<Group> groupList ) {

        if ( groupList.size() == 0 || groupList.size() > 10 ) {
            return false;
        }

        return true;
    }

}
