package com.bot.telegram.hpk.component.dao.telegram;

import com.bot.telegram.hpk.component.model.bot.TelegramUser;
import com.bot.telegram.hpk.component.model.bot.enums.UserMode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TelegramUserWrapper implements RowMapper<TelegramUser> {
    @Override
    public TelegramUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        TelegramUser telegramUser = new TelegramUser();

        telegramUser.setId(rs.getInt("id"));
        telegramUser.setTelegramUserId(rs.getInt("telegram_user_id"));
        telegramUser.setTelegramChatId(rs.getLong("telegram_chat_id"));
        telegramUser.setTelegramFirstname(rs.getString("telegram_firstname"));
        telegramUser.setTelegramLastname(rs.getString("telegram_lastname"));
        telegramUser.setTelegramNickname(rs.getString("telegram_nickname"));

        telegramUser.setLastActivity(rs.getTimestamp("last_activity").toLocalDateTime());
        telegramUser.setMode(UserMode.valueOf(rs.getString("mode").toUpperCase()));
        telegramUser.setModeCommand(rs.getString("mode_command"));
        telegramUser.setBlocked(rs.getBoolean("blocked"));

        telegramUser.setTeacherId(rs.getString("teacher_id"));

        return telegramUser;
    }
}
