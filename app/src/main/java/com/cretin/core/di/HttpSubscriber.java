package com.cretin.core.di;

import android.content.Intent;
import android.util.MalformedJsonException;
import android.widget.Toast;

import com.cretin.data.api.model.ResultModel;
import com.cretin.ui.base.BackFragmentActivity;
import com.cretin.ui.base.BaseActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.ui.fragment.user.LoginFragment;
import com.cretin.ui.manager.MainActivityManager;
import com.cretin.util.ToastHelper;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by wjn on 2016/7/4.
 * 自定义网络请求观察者
 */
public class HttpSubscriber<T> extends Subscriber<T> {
    public static final int TYPE_NETWORK_ERROR = 1;
    public static final int TYPE_PARSE_ERROR = 2;
    public static final int TYPE_OTHER_ERROR = 3;
    public static final int TYPE_API_ERROR = 4;
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private BaseActivity baseActivity;
    private BaseFragment baseFragment;
    @Inject
    ToastHelper _toastHelper;

    public HttpSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, BaseFragment baseFragment) {
        //把SubscriberOnNextListener对象传进来做统一调用
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.baseFragment = baseFragment;
    }

    public HttpSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, BaseActivity baseActivity) {
        //把SubscriberOnNextListener对象传进来做统一调用
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.baseActivity = baseActivity;
    }

    @Override
    public void onCompleted() {
        if ( mSubscriberOnNextListener != null ) {
            mSubscriberOnNextListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if ( mSubscriberOnNextListener != null ) {
            if ( e instanceof SocketTimeoutException || e instanceof ConnectException || e instanceof UnknownHostException
                    ) {
                e.printStackTrace();
                mSubscriberOnNextListener.onError(e, TYPE_NETWORK_ERROR);
                if ( baseFragment != null ) {
                    baseFragment.showErrorView();
                }
                if ( baseActivity != null ) {
                    baseActivity.showErrorView();
                }
            } else if ( e instanceof MalformedJsonException || e instanceof JsonSyntaxException ) {
                e.printStackTrace();
                mSubscriberOnNextListener.onError(e, TYPE_PARSE_ERROR);
                if ( baseFragment != null ) {
                    baseFragment.showErrorView();
                }
                if ( baseActivity != null ) {
                    baseActivity.showErrorView();
                }
            } else if ( e instanceof HttpException ) {
                e.printStackTrace();
                mSubscriberOnNextListener.onError(e, TYPE_API_ERROR);
                if ( baseFragment != null ) {
                    baseFragment.showErrorView();
                }
                if ( baseActivity != null ) {
                    baseActivity.showErrorView();
                }
            } else {
                mSubscriberOnNextListener.onError(e, TYPE_OTHER_ERROR);
                e.printStackTrace();
            }
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        ResultModel result = ( ResultModel ) t;
        if ( result != null && result.getCode() == 2 ) {
            //去登陆
            if ( baseFragment != null ) {
                Intent intent = new Intent(baseFragment.getActivity(), MainActivityManager.class);
                intent.putExtra(BackFragmentActivity.TAG_FRAGMENT, LoginFragment.TAG);
                baseFragment.getActivity().startActivity(intent);
                Toast.makeText(baseFragment.getActivity(),  result.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if ( baseActivity != null ) {
                Intent intent = new Intent(baseActivity, MainActivityManager.class);
                intent.putExtra(BackFragmentActivity.TAG_FRAGMENT, LoginFragment.TAG);
                baseActivity.startActivity(intent);
                Toast.makeText(baseActivity, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        if ( mSubscriberOnNextListener != null ) {
            mSubscriberOnNextListener.onNext(t);
        }
    }
}
