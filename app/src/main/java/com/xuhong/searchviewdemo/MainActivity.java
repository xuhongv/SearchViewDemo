package com.xuhong.searchviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private RippleView mRippleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRippleView= (RippleView) findViewById(R.id.RippleView);
        mRippleView.startRippleAnimation();
        //mRippleView.stopRippleAnimation();//结束动画
        mRippleView.setAnimationProgressListener(new RippleView.AnimationListener() {
            @Override
            public void startAnimation() {
                //开始动画了
            }

            @Override
            public void EndAnimation() {
                //结束动画了
            }
        });
    }
}
