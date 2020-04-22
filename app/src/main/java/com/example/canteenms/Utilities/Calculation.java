package com.example.canteenms.Utilities;

public class Calculation {

    public static Integer intInString(String s)
    {
        String clean = s.replaceAll("\\D+", "");
        return Integer.parseInt(clean);
    }
}
