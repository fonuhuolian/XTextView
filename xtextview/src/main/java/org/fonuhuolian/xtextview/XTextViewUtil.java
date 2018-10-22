package org.fonuhuolian.xtextview;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class XTextViewUtil {

    public static long sevenDaysTime = 604800000;
    public static long oneDayTime = 86399999;

    public static long getTodayEndTime() {

        long endTime = 0;

        long current = System.currentTimeMillis();

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
            String format = simpleDateFormat.format(current);

            String[] split = format.split(" ");
            String[] time = split[1].split(":");

            long startTime = current - Long.parseLong(time[0]) * 60 * 60 * 1000 - Long.parseLong(time[1]) * 60 * 1000 - Long.parseLong(time[2]) * 1000 - Long.parseLong(time[3]);
            endTime = startTime + oneDayTime;

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

        return endTime;
    }


    //获取指定毫秒数的对应星期
    public static String getWeek(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        String week = "";
        int cweek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (cweek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }
}
