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
    }
}
