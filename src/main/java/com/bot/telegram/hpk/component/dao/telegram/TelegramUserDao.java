package com.bot.telegram.hpk.component.dao.telegram;

import com.bot.telegram.hpk.component.model.bot.TelegramUser;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TelegramUserDao {

    @Autowired
    @Qualifier("hpkBotDb")
    private NamedParameterJdbcTemplate hpkBotDbNamedParameterJdbcTemplate;

    public TelegramUser getTelegramUserByTelegramUserId(long telegramChatId){
        String sql = "SELECT id, telegram_user_id, telegram_chat_id, " +
                "telegram_firstname, telegram_lastname, telegram_nickname, " +
                "last_activity, mode, mode_command, blocked, teacher_id " +
                "FROM telegram_user WHERE telegram_chat_id=:telegramChatId";

        try {
            return hpkBotDbNamedParameterJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource().addValue("telegramChatId", telegramChatId),
                    new TelegramUserWrapper());
        }catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException){
            incorrectResultSizeDataAccessException.getMessage();
        }

        return null;
    }

    public boolean createTelegramUser(TelegramUser telegramUser){
        String sql = "INSERT INTO telegram_user (telegram_user_id, telegram_chat_id, " +
                "telegram_firstname, telegram_lastname, telegram_nickname) " +
                "VALUES (:telegramUserId, :telegramChatId, " +
                ":telegramFirstname, :telegramLastname, :telegramNickname)";

        Map<String, Object> params = new HashMap<>();
        params.put("telegramUserId", telegramUser.getTelegramUserId());
        params.put("telegramChatId", telegramUser.getTelegramChatId());
        params.put("telegramFirstname", telegramUser.getTelegramFirstname());
        params.put("telegramLastname", telegramUser.getTelegramLastname());
        params.put("telegramNickname", telegramUser.getTelegramNickname());

        return hpkBotDbNamedParameterJdbcTemplate.update(sql, params) > 0;
    }

    public boolean updateUserModeAndCommandAndTeacherIdByUserId(long chatId, UserMode mode, String modeCommand, String teacherId){
        String sql = "UPDATE telegram_user SET mode=:mode, mode_command=:modeCommand, teacher_id=:teacherId " +
                "WHERE telegram_chat_id=:chatId";

        Map<String, String> params = new HashMap<>();
        params.put("chatId", String.valueOf(chatId));
        params.put("mode", mode.getAlias());
        params.put("modeCommand", modeCommand);
        params.put("teacherId", teacherId);

        return hpkBotDbNamedParameterJdbcTemplate.update(sql, params) > 0;
    }

    public boolean updateTelegramUserByUserId(TelegramUser telegramUser){
        String sql = "UPDATE telegram_user SET telegram_firstname=:telegramFirstname, " +
                "telegram_lastname=:telegramLastname, telegram_nickname=:telegramNickname, " +
                "last_activity=:lastActivity, blocked=:isBlocked, mode=:mode, mode_command=:modeCommand " +
                "WHERE id=:userId";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", String.valueOf(telegramUser.getId()));
        params.put("telegramFirstname", telegramUser.getTelegramFirstname());
        params.put("telegramLastname", telegramUser.getTelegramLastname());
        params.put("telegramNickname", telegramUser.getTelegramNickname());

        params.put("lastActivity", telegramUser.getLastActivity());
        params.put("isBlocked", telegramUser.isBlocked());
        params.put("mode", telegramUser.getMode());
        params.put("modeCommand", telegramUser.getModeCommand());

        return hpkBotDbNamedParameterJdbcTemplate.update(sql, params) > 0;
    }

}
