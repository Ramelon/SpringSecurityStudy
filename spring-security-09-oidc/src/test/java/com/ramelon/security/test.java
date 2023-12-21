package com.ramelon.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: xzy
 * @time: 2023/12/12 10:08
 */
public class test {
    public static void main(String[] args) {
        System.out.println(latestQuarter(6, null));
    }

    public static List<String> latestQuarter(int n, Date now) {
        if (now == null) {
            now = new Date();
        }
        List<String> timeStr = new ArrayList<>();
        String time = String.valueOf(getYear(now) + "0" + (getMM(now) + 2) / 3);
        timeStr.add(time);
        for (int i = 1; i < n; i++) {
            now = monthAddNum(now, -3);
            time = String.valueOf(getYear(now) + "0" + (getMM(now) + 2) / 3);
            timeStr.add(time);
        }
        return timeStr;
    }

    public static int getMM(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);

    }

    public static Date monthAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }
}
