package org.fonuhuolian.xtextview;

public enum TimeFormatStyle {

    TIME_STYYLE1("yyyy-MM-dd HH:mm"),
    TIME_STYYLE2("yyyy-MM-dd a hh:mm"),
    TIME_STYYLE3("yyyy年MM月dd日 HH:mm"),
    TIME_STYYLE4("yyyy年MM月dd日 a hh:mm"),
    TIME_STYYLE5("ago");

    private String format;

    TimeFormatStyle(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
