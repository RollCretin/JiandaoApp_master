package com.cretin.ui.fragment.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cretin.R;
import com.cretin.core.EventBus;
import com.cretin.core.di.HttpSubscriber;
import com.cretin.core.di.SubscriberOnNextListener;
import com.cretin.data.api.ApiDefaultConfig;
import com.cretin.data.api.model.ResultModel;
import com.cretin.data.api.model.UserModel;
import com.cretin.data.api.service.UserService;
import com.cretin.interfaces.eventbus.NotifyMeChange;
import com.cretin.ui.base.BackFragmentActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.ui.base.KV;
import com.cretin.ui.base.LocalStorageKeys;
import com.cretin.ui.manager.MainActivityManager;
import com.cretin.util.ToastHelper;
import com.cretin.utils.ViewUtils;
import com.cretin.view.CircleImageView;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;

import static com.cretin.R.id.rl_me;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @Bind( R.id.iv_portrait )
    CircleImageView ivPortrait;
    @Bind( R.id.tv_username )
    TextView tvUsername;
    @Bind( R.id.ll_avatar )
    LinearLayout llAvatar;
    @Bind( R.id.iv_setting )
    ImageView ivSetting;
    @Bind( rl_me )
    RelativeLayout rlMe;
    @Bind( R.id.tv_cache )
    TextView tvCache;
    @Bind( R.id.ll_clearcache )
    LinearLayout llClearcache;
    @Bind( R.id.tv_version )
    TextView tvVersion;
    @Bind( R.id.ll_banbengengxin )
    LinearLayout llBanbengengxin;
    @Bind( R.id.tv_fankui )
    TextView tvFankui;
    @Bind( R.id.tv_about )
    TextView tvAbout;
    @Bind( R.id.tv_exit )
    TextView tvExit;
    @Bind( R.id.swiperefresh )
    SwipeRefreshLayout swiperefresh;
    private UserModel userModel;

    @Inject
    public UserService _userService;
    @Inject
    public ToastHelper _toastHelper;

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        showContentView();
        hidTitleView();
        if ( mActivity.isKitkat ) {
            rlMe.setPadding(0, ViewUtils.getStatusBarHeights(mActivity), 0, 0);
        }
        swiperefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo();
            }
        });
    }

    @Override
    protected void initData() {
        getInfo();
    }

    @Override
    protected void refrehPageData() {

    }

    /**
     * 获取个人信息
     */
    private void getInfo() {
        userModel = KV.get(LocalStorageKeys.APP_USER_INFO);
        if ( userModel != null ) {
            tvUsername.setText(userModel.getUsername());
            if( !TextUtils.isEmpty(userModel.getAvatar())){
                Glide.with(mActivity).load(ApiDefaultConfig.BASE_ENDPOINT+userModel.getAvatar()).into(ivPortrait);
            }
            tvExit.setVisibility(View.VISIBLE);
        } else {
            //没有登录
            tvExit.setVisibility(View.GONE);
            tvUsername.setText("登录");
            ivPortrait.setImageResource(R.mipmap.default_avatar);
        }
        swiperefresh.setRefreshing(false);
    }

    @OnClick( {R.id.ll_avatar, R.id.iv_setting, R.id.ll_clearcache, R.id.ll_banbengengxin, R.id.tv_fankui, R.id.tv_about,R.id.tv_exit} )
    public void onViewClicked(View view) {
        switch ( view.getId() ) {
            case R.id.ll_avatar:
                userModel = KV.get(LocalStorageKeys.APP_USER_INFO);
                if ( userModel != null ) {

                } else {
                    //没有登录
                    Intent intent = new Intent(mActivity, MainActivityManager.class);
                    intent.putExtra(BackFragmentActivity.TAG_FRAGMENT, LoginFragment.TAG);
                    startActivity(intent);
                }
                break;
            case R.id.iv_setting:
                break;
            case R.id.ll_clearcache:
                break;
            case R.id.ll_banbengengxin:
                break;
            case R.id.tv_fankui:
                break;
            case R.id.tv_about:
                break;
            case R.id.tv_exit:
                //退出登录
                new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("确定退出登录？")
                        .setConfirmText("退出")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                logout(sweetAlertDialog);
                            }
                        })
                        .show();

                break;
        }
    }

    @Subscribe
    public void notifyMeChange(NotifyMeChange event) {
       getInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    /**
     * 退出登录
     * @param sweetAlertDialog
     */
    private void logout(final SweetAlertDialog sweetAlertDialog){
        Subscription subscribe = binds(_userService.logout()).subscribe(new HttpSubscriber<ResultModel>
                (new SubscriberOnNextListener<ResultModel>() {
                    @Override
                    public void onNext(ResultModel o) {
                        _toastHelper.show(o.getMessage());
                        if ( o.getCode() == 1 ) {
                            KV.remove(LocalStorageKeys.APP_USER_INFO);
                            KV.remove(LocalStorageKeys.APP_USER_ID);
                            getInfo();
                        } else {
                            _toastHelper.show(o.getMessage());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        stopDialog();
                        showContentView();
                        sweetAlertDialog.dismissWithAnimation();
                    }

                    @Override
                    public void onError(Throwable e, int type) {
                        stopDialog();
                        showErrorView();
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, this));
        addSubscription(subscribe);
    }
}
