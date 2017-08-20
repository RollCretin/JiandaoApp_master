package com.jiandao.ui.activity;

import android.view.View;

import com.jiandao.R;
import com.jiandao.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void injectMembers() {

    }

    @Override
    protected void initView(View view) {
        if ( view != null )
            setMainTitle("主页");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refrehPageData() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
