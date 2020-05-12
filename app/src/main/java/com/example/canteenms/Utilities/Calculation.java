package com.example.canteenms.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Calculation {

    public static Integer intInString(String s)
    {
        String clean = s.replaceAll("\\D+", "");
        return Integer.parseInt(clean);
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.UK);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
