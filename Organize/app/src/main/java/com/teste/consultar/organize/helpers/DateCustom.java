package com.teste.consultar.organize.helpers;

import java.text.SimpleDateFormat;

/**
 * Created by: Danilo A. Santos on 02/20/18.
 */
public class DateCustom {

    public static String getDateToday() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String getMonthYear(final String date) {
        String dateArray[] = date.split("/");
        return dateArray[1]+dateArray[2];
    }

}
