package org.fonuhuolian.xtextview.listener;

/**
 * 点赞点击的监听
 */
public interface OnCommentClickListener {
    void onLeftNameClick(int position, String name);

    void onRightNameClick(int position, String name);

    void onClick(int position);
}
