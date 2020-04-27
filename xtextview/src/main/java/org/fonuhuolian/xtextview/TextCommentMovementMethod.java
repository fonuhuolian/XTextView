package org.fonuhuolian.xtextview;

import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

import org.fonuhuolian.xtextview.listener.OnCommentClickListener;

// TODO 评论的点击事件(不对外提供)
final class TextCommentMovementMethod extends LinkMovementMethod {

    private CommentClickSpan mClickSpan;

    private Drawable background;

    private OnCommentClickListener mListener;

    private Drawable pressDrawable;

    //记录开始按下的时间
    private long mStartTime;

    private int position;

    public void setListener(OnCommentClickListener listener) {
        this.mListener = listener;
    }

    void setBackground(Drawable background, Drawable pressDrawable) {
        this.background = background;
        this.pressDrawable = pressDrawable;
    }

    void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mStartTime = System.currentTimeMillis();
            mClickSpan = getTextSpan(widget, buffer, event);
            if (mClickSpan != null) {
                mClickSpan.setPressed(true);
                Selection.setSelection(buffer, buffer.getSpanStart(mClickSpan), buffer.getSpanEnd(mClickSpan));
            } else {
                widget.setBackgroundDrawable(pressDrawable);
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            CommentClickSpan touchNickNameClickSpan = getTextSpan(widget, buffer, event);
            if (mClickSpan != null && touchNickNameClickSpan != mClickSpan) {
                mClickSpan.setPressed(false);
                mClickSpan = null;
                Selection.removeSelection(buffer);
            } else {

                int height = widget.getHeight();
                int y = (int) event.getY();

                if (y < 0 || y > height) {
                    widget.setBackgroundDrawable(background);
                } else {
                    widget.setBackgroundDrawable(pressDrawable);
                }
            }
        } else {
            if (mClickSpan != null) {
                mClickSpan.setPressed(false);
                mClickSpan = null;
                Selection.removeSelection(buffer);
                /**
                 *  当用户长按span时，不响应相应的点击事件。判断规则为从开始到结束的时间是否大于500ms
                 */
                if (System.currentTimeMillis() - mStartTime < 500) {
                    super.onTouchEvent(widget, buffer, event);
                }
            } else {

                int height = widget.getHeight();
                int y = (int) event.getY();

                if (y > 0 && y <= height) {
                    if (mListener != null)
                        mListener.onClick(position);
                }
            }

            widget.setBackgroundDrawable(background);
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
    private CommentClickSpan getTextSpan(TextView widget, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();

        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        CommentClickSpan[] link = spannable.getSpans(off, off, CommentClickSpan.class);
        CommentClickSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }
}
