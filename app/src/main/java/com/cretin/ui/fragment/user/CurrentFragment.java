package com.cretin.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cretin.R;
import com.cretin.data.api.service.UserService;
import com.cretin.ui.base.BaseFragment;
import com.cretin.util.ToastHelper;
import com.cretin.view.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends BaseFragment {
    public static final String TAG= "CurrentFragment";
    @Bind( R.id.recyclerview )
    RecyclerView recyclerview;
    @Bind( R.id.swip_refresh )
    SwipeRefreshLayout swipRefresh;

    @Inject
    ToastHelper _toastHelper;
    @Inject
    UserService _topService;

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_current;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        hidTitleView();
        showContentView();
    }

    @Override
    protected void initData() {
        List<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(1);
        res.add(1);
        res.add(1);
        res.add(1);
        res.add(1);
        res.add(1);
        AnimationAdapter adapter= new AnimationAdapter(res);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerview.addItemDecoration(new GridItemDecoration(mActivity, true));
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(true);
        adapter.setNotDoAnimationCount(3);
        recyclerview.setAdapter(adapter);

//        Subscription subscribe = binds(_topService.test("1")).subscribe(new HttpSubscriber<GetCodeModel>
//                (new SubscriberOnNextListener<GetCodeModel>() {
//                    @Override
//                    public void onNext(GetCodeModel o) {
//                        _toastHelper.show(o.getName());
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        stopDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e, int type) {
//
//                    }
//                }, this));
//        addSubscription(subscribe);
    }

    @Override
    protected void refrehPageData() {

    }

    class AnimationAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
        public AnimationAdapter(List list) {
            super(R.layout.item_reclcyerview_current, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
        }
    }
}
