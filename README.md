# XTextView
多种扩展TextView

> 添加依赖

`root build.gradle `
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
`module build.gradle `
```
implementation 'com.github.fonuhuolian:XTextView:1.2.1'
```

> 混淆
```
-dontwarn org.fonuhuolian.xtextview.**
-keep class org.fonuhuolian.xtextview.**{*;}
```

> ①折叠textview

```
<org.fonuhuolian.xtextview.FoldTextView
    android:id="@+id/tv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:isSupportContentClick="true"
    app:animDuration="2000"
    app:foldedTextHints="全部"
    app:hintWordsColor="#0080ff"
    app:hintWordsSize="20"
    app:limitRows="2"
    app:textContent="123"
    app:unFoldedTextHints="折叠"
    app:wordsColor="#f50000"
    app:wordsSize="12" />
```

```
foldTextView = (FoldTextView) findViewById(R.id.tv);

// 设置文字内容
foldTextView.setText();
```

> 效果

![效果1](https://github.com/fonuhuolian/XTextView/blob/master/screenshots/a.png?raw=true)
![效果2](https://github.com/fonuhuolian/XTextView/blob/master/screenshots/b.png?raw=true)

> ②时间textview

```
<org.fonuhuolian.xtextview.XTimeTextView
    android:id="@+id/time"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```
XTimeTextView timeTextView = (XTimeTextView) findViewById(R.id.time);
long time = System.currentTimeMillis();
timeTextView.setTextByTime(time, TimeFormatStyle.TIME_STYYLE4);
```
> ③点赞textview
```
<org.fonuhuolian.xtextview.XPraiseTextView
    android:id="@+id/praise"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:x_praise_Icon="@drawable/x_heart"
    app:x_praise_textColor="@color/colorAccent"
    app:x_praise_textSize="15dp" />
```

```
// 填充数据
praise.setData(strings, new OnPraiseClickListener() {
    @Override
    public void onClick(int position, String name) {

    }
});
```
> ④评论textview
```
<org.fonuhuolian.xtextview.XCommentTextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:x_comment_content_textColor="@color/colorPrimary"
    app:x_comment_name_textColor="@color/colorAccent"
    app:x_comment_textSize="15dp" />
```

```
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
public XCommentTextView setData(int index, String leftName, String rightName, String comment, boolean isAnswer, OnCommentClickListener mListener);
```