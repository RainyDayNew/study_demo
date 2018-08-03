package com.demo.resetmysoul.study_demo;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr. Huang on 2018/3/13.
 * Type:
 * des:
 */

public class ArTimeActivity extends BaseActivity {

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ar_time;
    }

    @Override
    protected void doTransaction() {
        String data = getResources().getString(R.string.create_time_k273);
        data = String.format(data,"2011-07-01");

    }
}
