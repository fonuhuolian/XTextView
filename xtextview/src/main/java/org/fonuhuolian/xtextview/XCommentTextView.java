package org.fonuhuolian.xtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.fonuhuolian.xtextview.listener.OnCommentClickListener;

/**
 * 评论TextView
 */
public class XCommentTextView extends AppCompatTextView {

    private CommentInfo mCommentInfo;
    private OnCommentClickListener mListener;

    /**
     * 昵称的颜色
     */
    private int mNameTextColor;

    /**
     * 回复的颜色
     */
    private int mContentTextColor;
    /**
     * 文字大小
     */
    private float textSize;
    /**
     * 是否需要回复人名之间的空格
     */
    private boolean isNeedSpace;

    /**
     * 构造方法（暂时只支持xml）
     */
    public XCommentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XCommentTextView);
        mNameTextColor = ta.getColor(R.styleable.XCommentTextView_x_comment_name_textColor, Color.parseColor("#586b95"));
        mContentTextColor = ta.getColor(R.styleable.XCommentTextView_x_comment_content_textColor, Color.parseColor("#181818"));
        textSize = ta.getDimension(R.styleable.XCommentTextView_x_comment_textSize, dip2px(15));
        isNeedSpace = ta.getBoolean(R.styleable.XCommentTextView_x_comment_answer_space, false);
        ta.recycle();
    }

    /**
     * 设置评论的数据
     *
     * @param index     评论数据的下标(所有评论list的此条评论对应的index)
     * @param leftName  左边的人名(评论人)
     * @param rightName 右边的人名(被回复人)
     * @param comment   评论内容
     * @param isAnswer  是否是回复的评论
     * @param mListener 监听事件
     */
    public XCommentTextView setData(int index, String leftName, String rightName, String comment, boolean isAnswer, OnCommentClickListener mListener) {

        this.mCommentInfo = new CommentInfo(index, leftName, rightName, comment, isAnswer);

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        this.setTextColor(mContentTextColor);
        this.mListener = mListener;
        this.setText(getCommentString());
        //设置选中文本的高亮颜色
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        TextCommentMovementMethod movement = new TextCommentMovementMethod();
        movement.setListener(mListener);
        movement.setPosition(index);
        Drawable background = this.getBackground();
        movement.setBackground(background == null ? getResources().getDrawable(android.R.color.transparent) : background, getResources().getDrawable(R.color.x_press_color));
        this.setMovementMethod(movement);
        return this;
    }


    // 具体的人名转换为SpannableStringBuilder
    private SpannableStringBuilder getCommentString() {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String leftName = mCommentInfo.getLeftName();
        String rightName = mCommentInfo.getRightName();
        String comment = mCommentInfo.getComment();
        boolean answer = mCommentInfo.isAnswer();

        if (answer) {

            if (leftName == null)
                leftName = "";

            int start = builder.length();
            int end = start + leftName.length();

            builder.append(leftName);
            builder.setSpan(new CommentClickSpan(mCommentInfo, true, mListener, mNameTextColor, textSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (isNeedSpace)
                builder.append(" 回复 ");
            else
                builder.append("回复");


            if (rightName == null)
                rightName = "";

            int start2 = builder.length();
            int end2 = start2 + rightName.length();
            builder.append(rightName);
            builder.setSpan(new CommentClickSpan(mCommentInfo, false, mListener, mNameTextColor, textSize), start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            builder.append("：");
            builder.append(comment);

        } else {

            if (leftName == null)
                leftName = "";

            int start = builder.length();
            int end = start + leftName.length();

            builder.append(leftName);
            builder.setSpan(new CommentClickSpan(mCommentInfo, true, mListener, mNameTextColor, textSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(": ");
            builder.append(comment);
        }

        return builder;
    }

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

}
