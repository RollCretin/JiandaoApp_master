package com.cretin.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cretin.R;
import com.cretin.core.di.qualifier.ClientVersionCode;
import com.cretin.ui.base.BaseActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.ui.base.KV;
import com.cretin.ui.base.LocalStorageKeys;
import com.cretin.ui.fragment.InfoFragment;
import com.cretin.ui.fragment.home.HomeFragment;
import com.cretin.view.NoScrollViewPager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    @Inject
    @ClientVersionCode
    int _versionCode;
    @Bind( R.id.view_pager )
    NoScrollViewPager viewPager;
    @Bind( R.id.rb_home )
    RadioButton rbHome;
    @Bind( R.id.rb_product )
    RadioButton rbProduct;
    @Bind( R.id.rb_message )
    RadioButton rbMessage;
    @Bind( R.id.rb_me )
    RadioButton rbMe;
    @Bind( R.id.rg_group )
    RadioGroup rgGroup;
    @Bind( R.id.iv_add )
    ImageView ivAdd;

    private Map<Integer, BaseFragment> mFragments = new HashMap();

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected void initView(View view) {
        if ( view != null ) {
            hidTitleView();
            showContentView();
            viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
            //ViewPager缓存4个界面
            viewPager.setOffscreenPageLimit(4);
            rgGroup.check(R.id.rb_home);
        }
    }

    protected void initEvent() {
        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.rb_home:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_product:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_message:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_me:
                        viewPager.setCurrentItem(3, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        if ( KV.get(LocalStorageKeys.IS_FIRST_START_IN, true) || KV.get(LocalStorageKeys.APP_VERSION_CODE, 1) < _versionCode ) {
            KV.put(LocalStorageKeys.APP_VERSION_CODE, _versionCode);
            KV.put(LocalStorageKeys.IS_FIRST_START_IN, false);
        }
    }

    @Override
    protected void refrehPageData() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @OnClick( R.id.iv_add )
    public void onViewClicked() {
        startActivity(new Intent(this,ShowBigPicActivity.class));
    }


    private class MainAdapter extends FragmentStatePagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        // 每个条目返回的fragment
        //  0
        @Override
        public Fragment getItem(int position) {
            return createFragment(position);
        }

        // 一共有几个条目
        @Override
        public int getCount() {
            return 4;
        }

        // 返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    public BaseFragment createFragment(int position) {
        BaseFragment fragment;
        fragment = mFragments.get(position);
        //在集合中取出来Fragment
        if ( fragment == null ) {  //如果再集合中没有取出来 需要重新创建
            if ( position == 0 ) {
                fragment = new HomeFragment();
            } else if ( position == 1 ) {
                fragment = new InfoFragment();
            } else if ( position == 2 ) {
                fragment = new InfoFragment();
            } else if ( position == 3 ) {
                fragment = new InfoFragment();
            }
            if ( fragment != null ) {
                mFragments.put(position, fragment);// 把创建好的Fragment存放到集合中缓存起来
            }
            return fragment;
        } else {
            return fragment;
        }
    }
}
