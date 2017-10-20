package com.cretin.ui.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cretin.R;
import com.cretin.ui.adapter.TabAdapter;
import com.cretin.ui.base.BaseFragment;
import com.cretin.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @Bind( R.id.rb_all )
    RadioButton rbAll;
    @Bind( R.id.rb_finish )
    RadioButton rbFinish;
    @Bind( R.id.rg_group )
    RadioGroup rgGroup;
    @Bind( R.id.rela_title )
    RelativeLayout relaTitle;
    @Bind( R.id.viewpager )
    ViewPager viewpager;
    private List<Fragment> mFragments;
    private TabAdapter adapter;

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        hidTitleView();
        showContentView();
        if ( mActivity.isKitkat ) {
            relaTitle.setPadding(0, ViewUtils.getStatusBarHeights(mActivity), 0, 0);
        }
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(CommonHomeFragment.newInstance(CommonHomeFragment.TYPE_CONTENT));
        mFragments.add(CommonHomeFragment.newInstance(CommonHomeFragment.TYPE_IMG));
        adapter = new TabAdapter(mActivity.getSupportFragmentManager(), mFragments);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);

        rbAll.performClick();
        viewpager.setCurrentItem(0);

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.rb_all:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.rb_finish:
                        viewpager.setCurrentItem(1);
                        break;
                }
            }
        });

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch ( position ) {
                    case 0:
                        rbAll.performClick();
                        break;
                    case 1:
                        rbFinish.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void refrehPageData() {

    }
}
