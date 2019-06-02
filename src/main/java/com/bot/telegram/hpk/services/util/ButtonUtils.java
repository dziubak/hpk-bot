package com.bot.telegram.hpk.services.util;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * Utils for building buttons.
 */
public class ButtonUtils {

    public static InlineKeyboardButton buildBackButton(final String text, final BtnPayload userCallBack ) {
        return new InlineKeyboardButton().setText(text).setCallbackData(JsonConverter.objToString(userCallBack));
    }

}
