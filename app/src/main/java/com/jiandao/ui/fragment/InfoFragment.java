package com.jiandao.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiandao.R;
import com.jiandao.core.di.HttpSubscriber;
import com.jiandao.core.di.SubscriberOnNextListener;
import com.jiandao.data.api.model.InfoModel;
import com.jiandao.data.api.model.ResultModel;
import com.jiandao.data.api.service.TopService;
import com.jiandao.ui.base.BaseFragment;
import com.jiandao.util.ToastHelper;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import retrofit2.Retrofit;
import rx.Subscription;

public class InfoFragment extends BaseFragment {
    public static final String TAG = "InfoFragment";
    @Inject
    TopService _topService;
    @Inject
    Picasso _picasso;
    @Inject
    ToastHelper _toastHelper;
    @Inject
    Retrofit _retrofit;
    @Bind( R.id.tv_content )
    TextView tvContent;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_info;
    }

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        setMainTitle("测试标题");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refrehPageData() {
        Subscription subscribe = binds(_topService.list()).subscribe(new HttpSubscriber<ResultModel<InfoModel>>(new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                ResultModel<InfoModel> result = ( ResultModel<InfoModel> ) o;
                StringBuffer strBuffer = new StringBuffer();
                for ( InfoModel.NewListBean newS :
                        result.getData().getNew_list() ) {
                    strBuffer.append(newS.getDisc() + "    " + newS.getImg() + "\n");
                }
                tvContent.setText(strBuffer.toString());
            }

            @Override
            public void onCompleted() {
                showContentView();
            }

            @Override
            public void onError(Throwable e, int type) {

            }
        }, this));
        addSubscription(subscribe);
    }


}
