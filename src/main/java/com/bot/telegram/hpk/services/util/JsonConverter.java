package com.bot.telegram.hpk.services.util;

import com.bot.telegram.hpk.component.model.bot.BtnPayload;
import com.google.gson.Gson;

/**
 * Converter for converting CallbackData to String and vice-versa.
 */
public class JsonConverter {
    private static Gson gson = new Gson();

    public static String objToString(BtnPayload obj ) {
        return gson.toJson(obj);
    }

    public static BtnPayload fromJsonToObj(String json ) {
        return gson.fromJson(json, BtnPayload.class);
    }
}
