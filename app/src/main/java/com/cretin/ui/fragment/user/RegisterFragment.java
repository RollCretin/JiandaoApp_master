package com.cretin.ui.fragment.user;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cretin.BaseApplication;
import com.cretin.R;
import com.cretin.core.di.HttpSubscriber;
import com.cretin.core.di.SubscriberOnNextListener;
import com.cretin.data.api.model.GetCodeModel;
import com.cretin.data.api.service.UserService;
import com.cretin.ui.base.BackFragmentActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.util.ToastHelper;
import com.cretin.www.clearedittext.view.ClearEditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment {
    public static final String TAG = "RegisterFragment";
    @Bind( R.id.ed_username )
    ClearEditText edUsername;
    @Bind( R.id.ed_password )
    ClearEditText edPassword;
    @Bind( R.id.ed_password_confirm )
    ClearEditText edPasswordConfirm;
    @Bind( R.id.ed_code )
    ClearEditText edCode;
    @Bind( R.id.tv_back_login )
    TextView tvBackLogin;
    @Bind( R.id.tv_register )
    TextView tvRegister;
    @Bind( R.id.tv_get_code )
    TextView tvCode;
    @Inject
    ToastHelper _toastHelper;
    @Inject
    UserService _userService;
    private Handler handlerCode = BaseApplication.getHandler();
    private int countDown = 60;// 倒计时秒数
    private String code;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if ( countDown > 1 ) {
                countDown--;
                if ( tvCode != null ) {
                    tvCode.setText("还剩" + countDown + "S");
                    tvCode.setEnabled(false);
                }
                if ( handlerCode != null )
                    handlerCode.postDelayed(this, 1000);
            } else if ( countDown == 1 ) {
                countDown = 60;
                if ( tvCode != null ) {
                    tvCode.setEnabled(true);
                    tvCode.setText("重新发送");
                }
            } else if ( countDown <= 0 ) {
                countDown = 60;
                if ( tvCode != null ) {
                    tvCode.setEnabled(true);
                    tvCode.setText("获取验证码");
                }
            }
        }
    };

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        setMainTitle("账号注册");
        showContentView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refrehPageData() {

    }

    @OnClick( {R.id.tv_back_login, R.id.tv_register, R.id.tv_get_code} )
    public void onViewClicked(View view) {
        switch ( view.getId() ) {
            case R.id.tv_back_login:
                (( BackFragmentActivity ) mActivity).removeFragment();
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_get_code:
                getCode();
                break;
        }
    }

    //获取验证码
    private void getCode() {
        String userName = edUsername.getText().toString().trim();
        if ( TextUtils.isEmpty(userName) ) {
            _toastHelper.show("请输入手机号");
            return;
        }
        showDialog("发送验证码...");
        addSubscription(binds(_userService.getCode(userName)).subscribe(
                new HttpSubscriber<GetCodeModel>(new SubscriberOnNextListener<GetCodeModel>() {
                    @Override
                    public void onNext(GetCodeModel o) {
//                        if ( o.isIsOk() ) {
//                            _toastHelper.show("验证码发送成功");
//                            tvCode.setEnabled(false);
//                            handlerCode.postDelayed(runnable, 1000);
//                            code = o.getData();
//                        } else {
//                            _toastHelper.show(o.getMessage());
//                        }
                    }

                    @Override
                    public void onCompleted() {
                        stopDialog();
                    }

                    @Override
                    public void onError(Throwable e, int type) {
                        tvCode.setEnabled(true);
                    }
                }, this)));

    }

    //注册
    private void register() {
        String userName = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString();
        String passwordConfirm = edPasswordConfirm.getText().toString();
        String codeStr = edCode.getText().toString();
        if ( TextUtils.isEmpty(userName) ) {
            _toastHelper.show("请输入手机号");
            return;
        }
        if ( TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm) ) {
            _toastHelper.show("密码不能为空");
            return;
        }
        if ( !password.equals(passwordConfirm) ) {
            _toastHelper.show("两次输入的密码不一致");
            return;
        }
        if ( TextUtils.isEmpty(codeStr) ) {
            _toastHelper.show("请输入验证码");
            return;
        }
        showDialog("正在注册");
        addSubscription(binds(_userService.register(userName, password, codeStr)).
                subscribe(new HttpSubscriber<GetCodeModel>(new SubscriberOnNextListener<GetCodeModel>() {
                    @Override
                    public void onNext(GetCodeModel o) {
//                        _toastHelper.show(o.getMessage());
//                        if ( o.isIsOk() ) {
//                            (( BackFragmentActivity ) mActivity).removeFragment();
//                        }
                    }

                    @Override
                    public void onCompleted() {
                        stopDialog();
                    }

                    @Override
                    public void onError(Throwable e, int type) {

                    }
                }, this)));
    }

}
