package com.bot.telegram.hpk.services.util;

import com.bot.telegram.hpk.component.model.bot.enums.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CurrentWeekService {

    /**
     * Defines number of week for provided date.
     * @param date provided date to define number of week.
     * @return number of week.
     */
    public static int getWeekNumber( final Date date ) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime( date );

        return calendar.get( Calendar.WEEK_OF_YEAR );
    }

    public static String retrieveWeekName( final Date date ) {
        return getWeekNumber( date ) % 2 == 0 ? "Знаменнику" : "Чисельнику";
    }

    public static WeekType retrieveWeekOriginName(final Date date ) {
        return getWeekNumber( date ) % 2 == 0 ? WeekType.LOW_WEEK : WeekType.TOP_WEEK;
    }

    public static String retrieveWeekOriginNameForRequest(final Date date ) {
        return getWeekNumber( date ) % 2 == 0
                ? WeekType.LOW_WEEK.getFriendlyName().toLowerCase()
                : WeekType.TOP_WEEK.getFriendlyName().toLowerCase();
    }


    public static WeekType retrieveReversedWeekOrigiName( final Date date ) {
        return getWeekNumber( date ) % 2 == 1 ? WeekType.LOW_WEEK : WeekType.TOP_WEEK;
    }

    public static String retrieveWorkingWeekDates() {
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);

        LocalDate firstDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek)); // first day
        LocalDate lastDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

        return firstDate.format(formatter) + " - " + lastDate.format(formatter);
    }

}
