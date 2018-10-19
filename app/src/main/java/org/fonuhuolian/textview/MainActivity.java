package org.fonuhuolian.textview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.fonuhuolian.xtextview.FoldTextView;

public class MainActivity extends AppCompatActivity {

    private FoldTextView foldTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        foldTextView = (FoldTextView) findViewById(R.id.tv);
    }

    public void onchange(View view) {

        foldTextView.setText("gitHub是一个面向开源及私有软件项目的托管平台，因为只支持git 作为唯一的版本库格式进行托管，故名gitHub。\n" +
                "gitHub于2008年4月10日正式上线，除了git代码仓库托管及基本的 Web管理界面以外，还提供了订阅、讨论组、文本渲染、在线文件编辑器、协作图谱（报表）、代码片段分享（Gist）等功能。目前，其注册用户已经超过350万，托管版本数量也是非常之多，其中不乏知名开源项目 Ruby on Rails、jQuery、python 等。\n" +
                "2018年6月4日，微软宣布，通过75亿美元的股票交易收购代码托管平台GitHub。 假发票的积分评级安排附近啪积分怕反扒积分扒鸡反扒附近加肥加大多多多飞机哦多久放假放假都放假大佛的简欧风景");
    }
}
