package org.fonuhuolian.xtextview;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// TODO 时间格式化的工具类(不对外提供)
final class XTextViewUtil {

    static long oneDayTime = 86399999;
    static long oneDayTime2 = 86400000;
    static long minute59 = 3540000;
    static long oneHour = 3600000;

    static long getTodayStartTime() {

        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    public static long getTodayEndTime() {

        long endTime = 0;

        long current = System.currentTimeMillis();

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
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

    private static long getTodayEndTime(long current) {

        long endTime = 0;

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
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
    static String getWeek(long millis) {
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

    // 获取指定当前周开始时间
    static long getThisWeekStartTime() {

        long current = System.currentTimeMillis();
        long todayEndTime = getTodayEndTime(current);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(current);
        int weekDays = 0;
        int cweek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (cweek) {
            case 1:
                weekDays = 7;
                break;
            case 2:
                weekDays = 1;
                break;
            case 3:
                weekDays = 2;
                break;
            case 4:
                weekDays = 3;
                break;
            case 5:
                weekDays = 4;
                break;
            case 6:
                weekDays = 5;
                break;
            case 7:
                weekDays = 6;
                break;
        }
        return todayEndTime + 1 - weekDays * oneDayTime2;
    }
}
