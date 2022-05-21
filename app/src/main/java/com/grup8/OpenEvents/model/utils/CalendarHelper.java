package com.grup8.OpenEvents.model.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class CalendarHelper {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static Calendar getCalendar(String datetime) throws ParseException {
        if(datetime.equals("null")) return null;

        Calendar date = Calendar.getInstance();
        date.setTime(Objects.requireNonNull(sdf.parse(datetime)));
        return date;
    }

    public static String getString(Calendar calendar){
        if(calendar == null) return null;
        return sdf.format(calendar.getTime());
    }
}
