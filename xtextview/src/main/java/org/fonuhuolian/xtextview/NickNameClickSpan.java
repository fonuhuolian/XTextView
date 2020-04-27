package org.fonuhuolian.xtextview;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import org.fonuhuolian.xtextview.listener.OnPraiseClickListener;

// TODO 昵称的点击事件(此类不对外提供)
final class NickNameClickSpan extends ClickableSpan {

    private boolean mPressed;

    private PraiseInfo mPraiseInfo;
    private OnPraiseClickListener mListener;
    private int mTextColor;
    private float mTextSize;

    NickNameClickSpan(PraiseInfo praiseInfo, OnPraiseClickListener listener, int textColor, float textSize) {
        this.mPraiseInfo = praiseInfo;
        this.mListener = listener;
        this.mTextColor = textColor;
        this.mTextSize = textSize;
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
        // 去掉下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (mPraiseInfo != null && mListener != null)
            mListener.onClick(mPraiseInfo.getIndex(), mPraiseInfo.getNickname());
    }
}
