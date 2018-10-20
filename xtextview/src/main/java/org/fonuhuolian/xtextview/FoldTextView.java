package org.fonuhuolian.xtextview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 折叠TextView
 */
public class FoldTextView extends LinearLayout implements View.OnClickListener {

    // 折叠状态时的文字提示
    private String foldedTextHints = "全文";
    // 展开状态时的文字提示
    private String unFoldedTextHints = "收起";
    // 行数限制
    private int limitRows = 6;
    // 字体大小
    private int wordsSize = 15;
    // 字体颜色
    private int wordsColor = 0xffff0000;
    // 文字提示大小
    private int hintWordsSize = 15;
    // 文字提示的颜色
    private int hintWordsColor = 0xffff0000;
    // 动画持续时间
    private int duration = 300;
    // 文字内容
    private String s = "";
    // 文字内容控件
    private TextView contentTv;
    // 文字提示控件
    private TextView hintTv;

    // 文本实际高度
    private int targetHeight = 0;
    // 文本折叠触发高度
    private int shortHeight = 0;
    // 是否是展开状态(默认非展开状态)
    private boolean isExpanded = false;
    // 是否可以响应点击事件
    private boolean isCanClick = false;
    // 是否支持折叠(有可能达不到折叠触发的高度)
    private boolean isSupportFlod;
    // 监听事件
    private OnExpandedChangeListener listener;

    public FoldTextView(Context context) {
        this(context, null);
    }

    public FoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttrs(context, attrs);

        initView(context);
    }

    // 初始化
    private void initView(final Context context) {

        // 垂直
        setOrientation(VERTICAL);

        // 初始化内容控件
        contentTv = new TextView(context);
        contentTv.setText(s);
        contentTv.setTextColor(wordsColor);
        contentTv.setVisibility(INVISIBLE);
        contentTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, wordsSize);
        addView(contentTv);

        // 获取触发折叠的高度(此值只需要测量一次)
        shortHeight = getShortHeight();


        // 初始化hint控件
        hintTv = new TextView(context);
        hintTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, hintWordsSize);
        hintTv.setTextColor(hintWordsColor);
        addView(hintTv);

        // 测量高度
        measureHight();

        hintTv.setOnClickListener(this);
    }

    /**
     * 得到属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FoldTextView);
        wordsColor = ta.getColor(R.styleable.FoldTextView_wordsColor, wordsColor);
        hintWordsColor = ta.getColor(R.styleable.FoldTextView_hintWordsColor, hintWordsColor);
        String folded_s = ta.getString(R.styleable.FoldTextView_foldedTextHints);
        foldedTextHints = TextUtils.isEmpty(folded_s) ? foldedTextHints : folded_s;
        String unFolded_s = ta.getString(R.styleable.FoldTextView_unFoldedTextHints);
        unFoldedTextHints = TextUtils.isEmpty(unFolded_s) ? unFoldedTextHints : unFolded_s;
        s = ta.getString(R.styleable.FoldTextView_textContent);
        limitRows = ta.getInteger(R.styleable.FoldTextView_limitRows, limitRows);
        wordsSize = ta.getInteger(R.styleable.FoldTextView_wordsSize, wordsSize);
        duration = ta.getInteger(R.styleable.FoldTextView_animDuration, duration);
        hintWordsSize = ta.getInteger(R.styleable.FoldTextView_hintWordsSize, hintWordsSize);
        ta.recycle();
    }


    /**
     * 折叠触发时的高度
     */
    public int getShortHeight() {
        int measuredWidth = contentTv.getMeasuredWidth();
        TextView copyTextView = new TextView(getContext());
        copyTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, wordsSize);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
        copyTextView.setMaxLines(limitRows);
        copyTextView.setLines(limitRows);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(2000, MeasureSpec.AT_MOST);
        copyTextView.measure(widthMeasureSpec, heightMeasureSpec);
        return copyTextView.getMeasuredHeight();
    }


    /**
     * 测量实际高度
     */
    private void measureHight() {

        // 只要调用测量就不可点击
        isCanClick = false;

        contentTv.setVisibility(INVISIBLE);

        contentTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        contentTv.post(new Runnable() {
            @Override
            public void run() {

                // 测量内容的实际高度
                targetHeight = contentTv.getHeight();
                // 重新绘制页面
                resetPage();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (isCanClick && isSupportFlod)
            setShowOrHint();
    }


    /**
     * 必须获得高度之后才能调用此方法
     */
    private void resetPage() {


        ViewGroup.LayoutParams params = contentTv.getLayoutParams();


        // 是否显现提示文字
        if (targetHeight > shortHeight) {

            // 支持折叠
            hintTv.setVisibility(VISIBLE);
            isSupportFlod = true;
        } else {

            // 不支持折叠
            hintTv.setVisibility(GONE);
            isSupportFlod = false;
        }


        if (isSupportFlod) {

            hintTv.setText(isExpanded ? unFoldedTextHints : foldedTextHints);

            params.height = isExpanded ? targetHeight : shortHeight;

        } else {

            params.height = targetHeight;

            if (isExpanded) {

                isExpanded = false;

                if (listener != null)
                    listener.expandState(false);
            }
        }


        contentTv.setLayoutParams(params);

        isCanClick = true;

        contentTv.setVisibility(VISIBLE);
    }

    private void setShowOrHint() {

        isCanClick = false;

        int startHight;
        int endHeight;

        if (isExpanded) {
            startHight = targetHeight;
            endHeight = shortHeight;
        } else {
            startHight = shortHeight;
            endHeight = targetHeight;
        }

        final ViewGroup.LayoutParams params = contentTv.getLayoutParams();

        //值动画
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(startHight, endHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) valueAnimator.getAnimatedValue();
                params.height = value;
                contentTv.setLayoutParams(params);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isCanClick = true;
                isExpanded = !isExpanded;
                hintTv.setText(isExpanded ? unFoldedTextHints : foldedTextHints);

                if (listener != null)
                    listener.expandState(isExpanded);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public void setText(String text) {

        contentTv.setText(text);
        measureHight();
    }

    public void setText(@StringRes int stringResId) {

        contentTv.setText(stringResId);
        measureHight();
    }


    // 设置 伸展状态的监听
    public void setOnExpandedListener(OnExpandedChangeListener onExpanedChangeListener) {
        this.listener = onExpanedChangeListener;
    }


    /**
     * 设置状态（如果文字内容不支持扩展 扩展状态不发生变更）
     */
    public void setExpanded(boolean isExpanded) {


        // 用户设置的与全局变量不同才需要更改
        if ((this.isExpanded && !isExpanded && isSupportFlod) || (!this.isExpanded && isExpanded && isSupportFlod)) {

            isCanClick = false;
            this.isExpanded = isExpanded;

            ViewGroup.LayoutParams params = contentTv.getLayoutParams();
            hintTv.setText(isExpanded ? unFoldedTextHints : foldedTextHints);
            params.height = isExpanded ? targetHeight : shortHeight;
            contentTv.setLayoutParams(params);

            isCanClick = true;

            if (listener != null)
                listener.expandState(this.isExpanded);
        }
    }
}
