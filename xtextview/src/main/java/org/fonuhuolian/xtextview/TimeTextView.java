package org.fonuhuolian.xtextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;

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

    public void setTextByTime(String time, TimeFormatStyle style) {
        setTextByTime(Long.parseLong(time), style);
    }

    public void setTextByTime(long time, TimeFormatStyle style) {

        long todayEndTime = XTextViewUtil.getTodayEndTime();


        // 一周前或者今天23:59:59:999之后
        if (todayEndTime - XTextViewUtil.sevenDaysTime > time || time > todayEndTime) {
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
                // 显示昨天
                this.setText("" + format);
            }
        }
    }
}
