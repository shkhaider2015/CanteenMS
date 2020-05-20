package com.example.canteenms.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Calculation {

    public static Integer intInString(String s)
    {
        String clean = s.replaceAll("\\D+", "");
        return Integer.parseInt(clean);
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy", Locale.UK);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        

        return formatter.format(calendar.getTime());
    }

    public static String getTime(long milliSeconds)
    {
        long minuts = TimeUnit.MILLISECONDS.toMinutes(milliSeconds);
        long hourse = TimeUnit.MILLISECONDS.toHours(milliSeconds);
        long day = TimeUnit.MILLISECONDS.toDays(milliSeconds);


        return "";
    }
}
