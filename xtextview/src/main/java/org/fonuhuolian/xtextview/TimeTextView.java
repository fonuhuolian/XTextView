package org.fonuhuolian.xtextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTextView extends AppCompatTextView {


    public TimeTextView(Context context) {
        this(context, null);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextByTime(long time, TimeFormatStyle style) {

        long todayEndTime = XTextViewUtil.getTodayEndTime();


        // 此周前（本周星期一之前）或者今天23:59:59:999之后
        if (XTextViewUtil.getThisWeekStartTime() > time || time > todayEndTime) {
            // 显示年月日
            String format = new SimpleDateFormat(style.getFormat()).format(time);
            this.setText(format);
        } else {
            // 显示星期 时分
            int i = style.getFormat().indexOf(" ");
            String formatStyle = style.getFormat().substring(i + 1);
            String format = new SimpleDateFormat(formatStyle).format(time);

            if (todayEndTime - XTextViewUtil.oneDayTime * 2 > time) {
                // 显示星期
                this.setText(XTextViewUtil.getWeek(time) + " " + format);
            } else if (todayEndTime - XTextViewUtil.oneDayTime > time) {
                // 显示昨天
                this.setText("昨天 " + format);
            } else {
                // 显示
                this.setText("" + format);
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
