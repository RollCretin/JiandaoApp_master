package com.cretin.ui.manager;

import android.os.Bundle;
import android.view.View;

import com.cretin.R;
import com.cretin.ui.base.BackFragmentActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.ui.fragment.user.LoginFragment;


public class MainActivityManager extends BackFragmentActivity<Bundle> {

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        BaseFragment fragment = null;
        if ( LoginFragment.TAG.equals(tag_fragment)) {
            fragment = new LoginFragment();
        }
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }
}
