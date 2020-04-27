package org.fonuhuolian.xtextview;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import org.fonuhuolian.xtextview.listener.OnCommentClickListener;

// TODO 昵称的点击事件(此类不对外提供)
final class CommentClickSpan extends ClickableSpan {

    private boolean mPressed;

    private CommentInfo mCommentInfo;
    private OnCommentClickListener mListener;
    private int mTextColor;
    private float mTextSize;
    private boolean mIsLeftClick;

    CommentClickSpan(CommentInfo commentInfo, boolean isLeftClick, OnCommentClickListener listener, int textColor, float textSize) {
        this.mCommentInfo = commentInfo;
        this.mListener = listener;
        this.mTextColor = textColor;
        this.mTextSize = textSize;
        this.mIsLeftClick = isLeftClick;
    }

    void setPressed(boolean isPressed) {
        this.mPressed = isPressed;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        // 按压的背景色选择器
        ds.bgColor = mPressed ? Color.parseColor("#B5B5B5") : Color.TRANSPARENT;
        // 文字颜色
        ds.setColor(mTextColor);
        // 文字大小
        ds.setTextSize(mTextSize);
        // 设置粗体
        ds.setFakeBoldText(true);
        // 去掉下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (mCommentInfo != null && mListener != null) {

            if (mIsLeftClick) {
                mListener.onLeftNameClick(mCommentInfo.getIndex(), mCommentInfo.getLeftName());
            } else {
                mListener.onRightNameClick(mCommentInfo.getIndex(), mCommentInfo.getRightName());
            }

        }
    }
}
