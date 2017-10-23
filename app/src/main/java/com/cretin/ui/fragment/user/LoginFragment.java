package com.cretin.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cretin.R;
import com.cretin.core.EventBus;
import com.cretin.core.di.HttpSubscriber;
import com.cretin.core.di.SubscriberOnNextListener;
import com.cretin.data.api.model.ResultModel;
import com.cretin.data.api.model.UserModel;
import com.cretin.data.api.service.UserService;
import com.cretin.interfaces.eventbus.NotifyMeChange;
import com.cretin.ui.base.BackFragmentActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.ui.base.KV;
import com.cretin.ui.base.LocalStorageKeys;
import com.cretin.util.ToastHelper;
import com.cretin.www.clearedittext.view.ClearEditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
    public static final String TAG = "LoginFragment";
    @Bind( R.id.ed_username )
    ClearEditText edUsername;
    @Bind( R.id.ed_password )
    ClearEditText edPassword;
    @Bind( R.id.tv_register )
    TextView tvRegister;
    @Bind( R.id.tv_get_password )
    TextView tvGetPassword;
    @Bind( R.id.tv_login )
    TextView tvLogin;
    @Inject
    ToastHelper _toastHelper;
    @Inject
    UserService _topService;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        setMainTitle("账号登录");
        showContentView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refrehPageData() {

    }

    @OnClick( {R.id.tv_register, R.id.tv_get_password, R.id.tv_login} )
    public void onViewClicked(View view) {
        switch ( view.getId() ) {
            case R.id.tv_register:
                (( BackFragmentActivity ) mActivity).addFragment(new RegisterFragment(), true, true);
                break;
            case R.id.tv_get_password:

                break;
            case R.id.tv_login:
                login();
                break;
        }
    }

    //登录
    private void login() {
        String userName = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString();
        if ( TextUtils.isEmpty(userName) ) {
            _toastHelper.show("请输入手机号");
            return;
        }
        if ( TextUtils.isEmpty(password) ) {
            _toastHelper.show("请输入密码");
            return;
        }
        showDialog("正在登录");
        Subscription subscribe = binds(_topService.login(userName, password)).subscribe(new HttpSubscriber<ResultModel<UserModel>>
                (new SubscriberOnNextListener<ResultModel<UserModel>>() {
                    @Override
                    public void onNext(ResultModel<UserModel> o) {
                        _toastHelper.show(o.getMessage());
                        if ( o.getCode() == 1 ) {
                            if ( o.getData() != null ) {
                                KV.put(LocalStorageKeys.APP_USER_INFO, o.getData());
                                KV.put(LocalStorageKeys.APP_USER_ID, o.getData().getUserId());
                            }
                            (( BackFragmentActivity ) mActivity).closeAllFragment();
                            EventBus.getInstance().post(new NotifyMeChange());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        stopDialog();
                    }

                    @Override
                    public void onError(Throwable e, int type) {

                    }
                }, this));
        addSubscription(subscribe);
    }
}
