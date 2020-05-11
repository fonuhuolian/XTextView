package org.fonuhuolian.xtextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// TODO 时间格式化文本
public class XTimeTextView extends AppCompatTextView {


    public XTimeTextView(Context context) {
        this(context, null);
    }

    public XTimeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextByTime(long time, TimeFormatStyle style) {


        if (style == TimeFormatStyle.TIME_STYYLE5) {

            // 与今天的时间差
            long now = System.currentTimeMillis();
            // 大于今天的差值
            long difference = XTextViewUtil.getTodayStartTime() - time;
            // 今天的差值
            long todayDifference = now - time;

            if (difference > 0) {
                // 昨天及以前

                // 2天前 。。。
                if (difference - XTextViewUtil.oneDayTime2 > 0) {

                    int temp = difference % XTextViewUtil.oneDayTime2 == 0 ? 0 : 1;

                    long days = difference / XTextViewUtil.oneDayTime2 + temp;

                    this.setText(days + "天前");
                } else {
                    // 昨天
                    this.setText("昨天");
                }

            } else {

                if (todayDifference >= 0) {
                    // 分钟以前

                    if (todayDifference - XTextViewUtil.minute59 >= 0) {
                        // 小时前
                        int hour = (int) (todayDifference / XTextViewUtil.oneHour);

                        if (hour == 0)
                            hour = 1;

                        this.setText(hour + "小时前");
                    } else {

                        long minute = todayDifference / 60000 + 1;

                        this.setText(minute + "分钟前");
                    }
                } else {
                    this.setText("现在");
                }
            }

        } else {

            long todayEndTime = XTextViewUtil.getTodayEndTime();
            long todayStartTime = XTextViewUtil.getTodayStartTime();

            // 今天23:59:59:999之后
            if (time > todayEndTime) {
                // 显示年月日
                String format = new SimpleDateFormat(style.getFormat()).format(time);
                this.setText(format);
            } else if (XTextViewUtil.getThisWeekStartTime() > time) {
                // 此周前（本周星期二之前） 为了保证 今天是周一 能显示昨天
                // 显示年月日
                // 昨天的优先级 大于 昨天是上周日的情况
                if (time > todayStartTime - XTextViewUtil.oneDayTime && time < todayStartTime) {
                    int i = style.getFormat().indexOf(" ");
                    String formatStyle = style.getFormat().substring(i + 1);
                    String format = new SimpleDateFormat(formatStyle).format(time);
                    // 显示昨天
                    this.setText("昨天 " + format);
                } else {
                    String format = new SimpleDateFormat(style.getFormat()).format(time);
                    this.setText(format);
                }
            } else {
                // 显示星期 时分
                int i = style.getFormat().indexOf(" ");
                String formatStyle = style.getFormat().substring(i + 1);
                String format = new SimpleDateFormat(formatStyle).format(time);

                if (todayStartTime - XTextViewUtil.oneDayTime > time) {
                    // 显示星期
                    this.setText(XTextViewUtil.getWeek(time) + " " + format);
                } else if (time > todayStartTime - XTextViewUtil.oneDayTime && time < todayStartTime) {
                    // 显示昨天
                    this.setText("昨天 " + format);
                } else {
                    // 显示
                    this.setText("" + format);
                }
            }

        }
    }

    // TODO 短提示 今天（12：00） 昨天  星期一 星期二 2020/03/05
    public void setTextByShortTime(long time, TimeFormatStyle style) {

        if (time == 0) {
            this.setText("");
            return;
        }

        long todayEndTime = XTextViewUtil.getTodayEndTime();

        // 今天23:59:59:999之后
        if (time > todayEndTime) {
            String format = new SimpleDateFormat(style.getFormat()).format(time);
            this.setText(format);
        } else if (XTextViewUtil.getThisWeekStartTime() > time) {
            // 此周前（本周星期一之前）
            // 显示年月日
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(time));
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DATE);
            this.setText(year + "/" + month + "/" + day);
        } else {
            // 显示星期 时分
            if (todayEndTime - XTextViewUtil.oneDayTime * 2 > time) {
                // 显示星期
                this.setText(XTextViewUtil.getWeek(time));
            } else if (todayEndTime - XTextViewUtil.oneDayTime > time) {
                // 显示昨天
                this.setText("昨天");
            } else {
                // 显示今天
                String format = new SimpleDateFormat(style.getFormat()).format(time);
                this.setText(format);
                String[] split = format.split(" ");
                int i = split[0].length() + 1;
                this.setText(format.substring(i, format.length()));
            }
        }
    }
}
