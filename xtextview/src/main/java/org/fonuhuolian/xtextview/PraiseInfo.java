package org.fonuhuolian.xtextview;

// TODO 人名的实体类(不对外提供)
final class PraiseInfo {

    // 下标
    private int index;
    private String nickname;

    PraiseInfo(int index, String nickname) {
        this.index = index;
        this.nickname = nickname;
    }

    int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
