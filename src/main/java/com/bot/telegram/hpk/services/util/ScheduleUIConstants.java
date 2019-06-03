package com.bot.telegram.hpk.services.util;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for text constants.
 */
public class ScheduleUIConstants {
    public static final String BOT_START_MAIN = "Бот допоможе:\n"
            + "   " + EmojiParser.parseToUnicode(":calendar:") + " швидко та легко отримувати розклад занять\n"
            + "   " + EmojiParser.parseToUnicode(":envelope_with_arrow:") + " викладача та групи\n"
            + "\nВибери з меню внизу що саме тобі необхідно." + EmojiParser.parseToUnicode(":arrow_down:");

    public static final String ASK_TEACHER_NAME = EmojiParser.parseToUnicode(":arrow_right:" ) + " Відправ <b>прізвище</b> викладача.";
    public static final String FOUNDED_TEACHERS = EmojiParser.parseToUnicode( ":mag_right: Ось викладачі яких мені вдалось знайти." );
    public static final String CHANGE_TEACHER_NAME_TEXT = EmojiParser.parseToUnicode(":pencil2: " ) + "Змінити прізвище";

    public static final String ASK_GROUP_NAME = EmojiParser.parseToUnicode(":arrow_right:" ) + " Відправ мені <b>назву</b> групи.";
    public static final String FOUNDED_GROUPS = EmojiParser.parseToUnicode( ":mag_right: Ось групи які вдалось знайти." );
    public static final String CHANGE_GROUP_NAME_TEXT = EmojiParser.parseToUnicode(":pencil2: " ) + "Змінити назву групи";

    public static final String TYPE_AGAIN = "Ввести ще раз";

    public static final String CLOSE = EmojiParser.parseToUnicode(":no_entry_sign: " ) + "Закрити";

    public static final String WRITE_GROUP_MSG = EmojiParser.parseToUnicode(":scroll: ") + "Відправ мені <b>повну назву групи</b>, наприклад, <i>ФБ-12</i>." +
            " \nАбо вибери зі <i>списку</i>.";

    public static final String WRONG_GROUP_NAME = EmojiParser.parseToUnicode(":lock:" ) + "Не вдалось знайти групу, спробуй ще раз.\n<i>Приклад `ФБ-12`</i>.";

    public static Map<String, String> PAIR_TIME = new HashMap<>();
}
