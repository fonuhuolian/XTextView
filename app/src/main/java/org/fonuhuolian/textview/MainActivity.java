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

        foldTextView.setText("二姐夫评价票反对附近的解放军到附近的发动机发票登记发票寄刀片飞机票的假发票的解放军到福建频道交付 假发票的积分评级安排附近啪积分怕反扒积分扒鸡反扒附近加肥加大多多多飞机哦多久放假放假都放假大佛的简欧风景");
    }
}
