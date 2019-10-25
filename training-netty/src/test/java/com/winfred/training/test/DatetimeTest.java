package com.winfred.training.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatetimeTest {

    public static void main(String[] args) {
        int max = 158 + 15;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        System.out.println(simpleDateFormat.format(calendar.getTime()));


        calendar.add(Calendar.DAY_OF_MONTH, max);
        System.out.println(simpleDateFormat.format(calendar.getTime()));

    }
}
