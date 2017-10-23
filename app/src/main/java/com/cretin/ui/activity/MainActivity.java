package com.cretin.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cretin.R;
import com.cretin.core.di.qualifier.ClientVersionCode;
import com.cretin.ui.base.BaseActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.ui.base.KV;
import com.cretin.ui.base.LocalStorageKeys;
import com.cretin.ui.fragment.home.CommonHomeFragment;
import com.cretin.ui.fragment.home.HomeFragment;
import com.cretin.ui.fragment.user.MeFragment;
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
    @Bind( R.id.ll_left )
    LinearLayout llLeft;
    @Bind( R.id.ll_right )
    LinearLayout llRight;
    @Bind( R.id.selection_container )
    LinearLayout selectionContainer;
    @Bind( R.id.send_container )
    RelativeLayout sendContainer;
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
    @Bind( R.id.container_rg_group )
    RelativeLayout containerRgGroup;

    private Animation animation1;
    private Animation animation2;

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

            // 加载动画
            animation1 = AnimationUtils.loadAnimation(this,
                    R.anim.send_selection_enter);
            animation2 = AnimationUtils.loadAnimation(this,
                    R.anim.send_selection_exit);

            //防止事件透穿
            sendContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
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


    @OnClick( {R.id.iv_add, R.id.ll_left, R.id.ll_right} )
    public void onViewClicked(View view) {
        switch ( view.getId() ) {
            case R.id.ll_left:
                //文字类型

                break;
            case R.id.ll_right:

                //图片类型
                break;
            case R.id.iv_add:
                if ( sendContainer.getVisibility() == View.INVISIBLE ) {
                    sendContainer.setVisibility(View.VISIBLE);
                    selectionContainer.startAnimation(animation1);
                    // 开始动画
                } else {
                    selectionContainer.startAnimation(animation2);
                    new Thread() {
                        public void run() {
                            //这儿是耗时操作，完成之后更新UI；
                            try {
                                Thread.sleep(300);
                            } catch ( InterruptedException e ) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    //更新UI
                                    sendContainer.setVisibility(View.INVISIBLE);
                                }

                            });
                        }
                    }.start();
                }
                break;
        }
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
                fragment = CommonHomeFragment.newInstance(CommonHomeFragment.TYPE_CONTENT, false);
            } else if ( position == 2 ) {
                fragment = CommonHomeFragment.newInstance(CommonHomeFragment.TYPE_IMG, false);
            } else if ( position == 3 ) {
                fragment = new MeFragment();
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
