package org.fonuhuolian.xtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.fonuhuolian.xtextview.listener.OnPraiseClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 点赞TextView
 */
public class XPraiseTextView extends AppCompatTextView {

    private List<PraiseInfo> mPraiseInfos = new ArrayList<>();
    private OnPraiseClickListener mListener;
    private Context mContext;

    /**
     * 昵称的颜色
     */
    private int mNameTextColor;

    /**
     * 第一个显示的左侧图标
     */
    private int mIcon;

    /**
     * 文字大小
     */
    private float textSize;


    /**
     * 构造方法（暂时只支持xml）
     */
    public XPraiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XPraiseTextView);
        mIcon = ta.getResourceId(R.styleable.XPraiseTextView_x_praise_Icon, R.drawable.x_heart);
        mNameTextColor = ta.getColor(R.styleable.XPraiseTextView_x_praise_textColor, Color.parseColor("#586b95"));
        textSize = ta.getDimension(R.styleable.XPraiseTextView_x_praise_textSize, dip2px(15));
        ta.recycle();
    }

    /**
     * 设置点赞的数据
     */
    public XPraiseTextView setData(List<String> data, OnPraiseClickListener mListener) {

        this.mPraiseInfos.clear();

        for (int i = 0; i < data.size(); i++) {
            this.mPraiseInfos.add(new PraiseInfo(i, data.get(i)));
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        this.setTextColor(mNameTextColor);
        this.mListener = mListener;
        this.setText(getPraiseString());
        //设置选中文本的高亮颜色
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        this.setMovementMethod(new TextNameMovementMethod());
        return this;
    }


    // 具体的人名转换为SpannableStringBuilder
    private SpannableStringBuilder getPraiseString() {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("替");

        int praiseSize = mPraiseInfos.size();

        // for循环组装人名
        for (int mI = 0; mI < praiseSize; mI++) {

            // 昵称
            PraiseInfo praiseInfo = mPraiseInfos.get(mI);
            String nickname = praiseInfo.getNickname();
            int start = builder.length();
            int end = start + nickname.length();

            builder.append(nickname);
            if (mI != praiseSize - 1) {
                builder.append(", ");
            }
            builder.setSpan(new NickNameClickSpan(praiseInfo, mListener, mNameTextColor, textSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // 替换 预留位置
        builder.setSpan(new VerticalImageSpan(ContextCompat.getDrawable(mContext, mIcon), textSize),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

}
