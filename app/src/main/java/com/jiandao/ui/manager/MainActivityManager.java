package com.jiandao.ui.manager;

import android.os.Bundle;
import android.view.View;

import com.jiandao.R;
import com.jiandao.ui.base.BackFragmentActivity;
import com.jiandao.ui.base.BaseFragment;
import com.jiandao.ui.fragment.InfoFragment;


public class MainActivityManager extends BackFragmentActivity<Bundle> {

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        BaseFragment fragment = null;
        if ( InfoFragment.TAG.equals(tag_fragment)) {
            fragment = new InfoFragment();
        }
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }
}
