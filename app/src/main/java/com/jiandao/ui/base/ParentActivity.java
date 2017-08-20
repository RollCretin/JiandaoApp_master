package com.jiandao.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by cretin on 16/10/27.
 */

public abstract class ParentActivity extends RxAppCompatActivity {
    //记录下所有的Activity
    public final static List<ParentActivity> mActivities = new LinkedList<ParentActivity>();
    public static boolean isKitkat;
    public static ParentActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        synchronized ( mActivities ) {
            mActivities.add(this);
        }
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            isKitkat = true;
        }
        initView(null);
    }

    /**
     * 如果没有设置布局id 则不调用此方法
     *
     * @param view
     */
    protected abstract void initView(View view);

    @Override
    protected void onResume() {
        super.onResume();
        mActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized ( mActivities ) {
            mActivities.remove(this);
        }
        ButterKnife.unbind(this);
    }
}
