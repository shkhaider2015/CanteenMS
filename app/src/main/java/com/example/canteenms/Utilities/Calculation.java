package com.example.canteenms.Utilities;

import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Calculation {

    private static final String TAG = "Calculation";

    public static Integer intInString(String s)
    {
        String clean = s.replaceAll("\\D+", "");
        return Integer.parseInt(clean);
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        String timeFormate = "";
        long currentTime = Calendar.getInstance().getTimeInMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        int nowYear = now.get(Calendar.YEAR)-2000;
        int nowMonth = now.get(Calendar.MONTH);
        int nowDate = now.get(Calendar.DAY_OF_MONTH);
        int nowDayOfWeek = now.get(Calendar.DAY_OF_WEEK);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        int year = calendar.get(Calendar.YEAR)-2000;
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);

        Log.d(TAG, "getDate: YEAR : " + year + " and MONTH : " + month + " and Date : " + date + "and Day of week : " + dayOfWeek);

        if (nowYear == year)
        {
            if (nowMonth == month)
            {
                if (nowDate == date)
                {
                    timeFormate = "Today " + hours + ":" + minutes;
                }
                else if ((date + 1) == nowDate)
                {
                    timeFormate = "Yesterday " + hours + ":" + minutes;
                }
                else
                {
                    timeFormate = getMonth(month) + " " + date;
                }
            }
            else
            {
                timeFormate = getMonth(month) + " " + date;
            }

        }
        else
        {
            timeFormate = "20" + year + " " + getMonth(month);
        }


        return timeFormate;
    }

    public static String getMonth(int month)
    {
        String monthName = "";
        switch (month)
        {
            case 0:
                monthName = "Jan";
                break;
            case 1:
                monthName = "Feb";
                break;
            case 2:
                monthName = "Mar";
                break;
            case 3:
                monthName = "Apr";
                break;
            case 4:
                monthName = "May";
                break;
            case 5:
                monthName = "Jun";
                break;
            case 6:
                monthName = "Jul";
                break;
            case 7:
                monthName = "Aug";
                break;
            case 8:
                monthName = "Sep";
                break;
            case 9:
                monthName = "Oct";
                break;
            case 10:
                monthName = "Nov";
                break;
            case 11:
                monthName = "Dec";
                break;
        }

        return monthName;
    }

    public static String getDay(int day)
    {
        String dayOfWeek = "";

        switch (day)
        {
            case 0:
                dayOfWeek = "Mon";
                break;
            case 1:
                dayOfWeek = "Tue";
                break;
            case 2:
                dayOfWeek = "Wed";
                break;
            case 3:
                dayOfWeek = "Tus";
                break;
            case 4:
                dayOfWeek = "Fri";
                break;
            case 5:
                dayOfWeek = "Sat";
                break;
            case 6:
                dayOfWeek = "Sun";
                break;
        }

        return dayOfWeek;
    }


}
