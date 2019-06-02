package com.bot.telegram.hpk.services.util;

import com.bot.telegram.hpk.component.model.bot.enums.DayOfWeek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

/**
 * Utilize methods working on dates.
 */
public class DateUtils {

    public static String currentDayOfWeekShort() {
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Kiev"));
        java.time.DayOfWeek dow = date.getDayOfWeek();

        String dayName = "NONE";

        dayName = dow.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        if (dayName.equals("Sat") || dayName.equals("Sun")) {
            return "MON";
        }

        return dayName;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long nowMillis() {
        ZonedDateTime ldt = LocalDateTime.now().atZone(ZoneId.of("Europe/Kiev"));

        return ldt.toInstant().toEpochMilli();
    }

    public static Date convertToDate( final String date ) {
        //2018-06-26
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("Date convert error : " + date + ". " + e.getMessage());
        }
        return formattedDate;
    }

    public static String convertDateToISO( final Date date ) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(formatter.format(date));

        return localDate.toString();
    }

    public static String convertDateToString( final Date date ) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(formatter.format(date)).plusDays(1);

        StringBuilder sb = new StringBuilder();

        int numberOfMonth = localDate.getMonth().getValue();

        sb.append(DayOfWeek.values()[localDate.getDayOfWeek().getValue() - 1].getName())
                .append(" ")
                .append(localDate.getDayOfMonth() + "." +  (numberOfMonth < 10 ? "0" + numberOfMonth : numberOfMonth));

        return sb.toString();
    }

    public static String currentDateWithFormat(final DateTimeFormatter formatter ) {
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));

        return ldt.format(formatter);
    }
}
