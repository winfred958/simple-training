package com.winfred.training.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatetimeTest {

    int max = 158;

    public static void main(String[] args) {
        int max = 158;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        calendar.add(Calendar.DAY_OF_MONTH, max);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println(simpleDateFormat.format(calendar.getTime()));

    }
}
