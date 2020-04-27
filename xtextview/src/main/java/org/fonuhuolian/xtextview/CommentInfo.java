package org.fonuhuolian.xtextview;

// TODO 评论的实体类(不对外提供)
final class CommentInfo {

    // 下标
    private int index;
    private String leftName;
    private String rightName;
    private String comment;
    // 是否是回复消息
    private boolean isAnswer;
    // 是否是最后一条评论
    private boolean isLast;

    CommentInfo(int index, String leftName, String rightName, String comment, boolean isAnswer, boolean isLast) {
        this.index = index;
        this.leftName = leftName;
        this.rightName = rightName;
        this.comment = comment;
        this.isAnswer = isAnswer;
        this.isLast = isLast;
    }

    int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
