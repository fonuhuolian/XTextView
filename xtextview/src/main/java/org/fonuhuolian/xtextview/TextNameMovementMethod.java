package org.fonuhuolian.xtextview;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

// TODO 文本姓名的点击事件(不对外提供)
final class TextNameMovementMethod extends LinkMovementMethod {

    private NickNameClickSpan mNickNameClickSpan;

    //记录开始按下的时间
    private long mStartTime;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mStartTime = System.currentTimeMillis();
            mNickNameClickSpan = getTextSpan(widget, buffer, event);
            if (mNickNameClickSpan != null) {
                mNickNameClickSpan.setPressed(true);
                Selection.setSelection(buffer, buffer.getSpanStart(mNickNameClickSpan), buffer.getSpanEnd(mNickNameClickSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            NickNameClickSpan touchNickNameClickSpan = getTextSpan(widget, buffer, event);
            if (mNickNameClickSpan != null && touchNickNameClickSpan != mNickNameClickSpan) {
                mNickNameClickSpan.setPressed(false);
                mNickNameClickSpan = null;
                Selection.removeSelection(buffer);
            }
        } else {
            if (mNickNameClickSpan != null) {
                mNickNameClickSpan.setPressed(false);
                mNickNameClickSpan = null;
                Selection.removeSelection(buffer);
                /**
                 *  当用户长按span时，不响应相应的点击事件。判断规则为从开始到结束的时间是否大于500ms
                 */
                if (System.currentTimeMillis() - mStartTime < 500) {
                    super.onTouchEvent(widget, buffer, event);
                }
            }
        }
        return true;
    }

    /**
     * 得到匹配的span
     *
     * @param widget
     * @param spannable
     * @param event
     * @return
     */
    private NickNameClickSpan getTextSpan(TextView widget, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();

        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        NickNameClickSpan[] link = spannable.getSpans(off, off, NickNameClickSpan.class);
        NickNameClickSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }
}
