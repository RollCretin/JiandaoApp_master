package com.cretin.ui.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cretin.R;
import com.cretin.core.di.HttpSubscriber;
import com.cretin.core.di.SubscriberOnNextListener;
import com.cretin.data.api.model.JokesContentModel;
import com.cretin.data.api.model.JokesImgModel;
import com.cretin.data.api.model.ResultModel;
import com.cretin.data.api.service.JokesService;
import com.cretin.ui.base.BaseFragment;
import com.cretin.util.ToastHelper;
import com.cretin.view.ItemButtomDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonHomeFragment extends BaseFragment {
    public static final String TAG = "CommonHomeFragment";
    public static final int TYPE_CONTENT = 0;
    public static final int TYPE_IMG = 1;
    @Bind( R.id.recyclerview )
    RecyclerView recyclerview;
    @Bind( R.id.swip_refresh )
    SwipeRefreshLayout swipRefresh;
    @Inject
    ToastHelper _toastHelper;
    @Inject
    JokesService _jokesService;
    private List<JokesContentModel.JokesListBean> list;
    private List<JokesImgModel.JokesListBean> imgList;
    private AnimationAdapter adapter;
    private AnimationImageAdapter imgAdapter;
    private int currPage = 1;

    public static CommonHomeFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        CommonHomeFragment fragment = new CommonHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_common_home;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceStat) {
        hidTitleView();
        showContentView();
    }

    @Override
    protected void initData() {
        if ( getArguments().getInt("type") == TYPE_CONTENT ) {
            list = new ArrayList();
            adapter = new AnimationAdapter(list);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
            recyclerview.addItemDecoration(new ItemButtomDecoration(mActivity));
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(true);
            adapter.setNotDoAnimationCount(3);
            swipRefresh.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    currPage = 1;
                    addData(currPage);
                }
            });
            recyclerview.setAdapter(adapter);
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    addData(currPage);
                }
            }, recyclerview);
            addData(currPage);
        } else {
            imgList = new ArrayList();
            imgAdapter = new AnimationImageAdapter(imgList);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
            recyclerview.addItemDecoration(new ItemButtomDecoration(mActivity));
            imgAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            imgAdapter.isFirstOnly(true);
            imgAdapter.setNotDoAnimationCount(3);
            swipRefresh.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    currPage = 1;
                    addData(currPage);
                }
            });
            recyclerview.setAdapter(imgAdapter);
            imgAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    addData(currPage);
                }
            }, recyclerview);
            addData(currPage);
        }
    }

    @Override
    protected void refrehPageData() {
        addData(1);
    }

    private void addData(final int page) {
        currPage = page + 1;
        if ( getArguments().getInt("type") == TYPE_CONTENT ) {
            //文字段子
            Subscription subscribe = binds(_jokesService.getJokesList(page)).subscribe(new HttpSubscriber<ResultModel<JokesContentModel>>
                    (new SubscriberOnNextListener<ResultModel<JokesContentModel>>() {
                        @Override
                        public void onNext(ResultModel<JokesContentModel> o) {
                            if ( o.getCode() == 1 ) {
                                if ( o.getData() != null && !o.getData().getJokesList().isEmpty() ) {
                                    //请求成功
                                    if ( page <= 1 ) {
                                        list.clear();
                                    }
                                    list.addAll(o.getData().getJokesList());
                                    adapter.notifyDataSetChanged();

                                    if ( o.getData().getTotalPage() != page ) {
                                        adapter.loadMoreComplete();
                                    } else {
                                        adapter.loadMoreEnd();
                                    }
                                }
                            } else {
                                _toastHelper.show(o.getMessage());
                            }
                        }

                        @Override
                        public void onCompleted() {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e, int type) {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                        }
                    }, this));
            addSubscription(subscribe);
        } else {
            //图片段子
            Subscription subscribe = binds(_jokesService.getJokesImgList(page)).subscribe(new HttpSubscriber<ResultModel<JokesImgModel>>
                    (new SubscriberOnNextListener<ResultModel<JokesImgModel>>() {
                        @Override
                        public void onNext(ResultModel<JokesImgModel> o) {
                            if ( o.getCode() == 1 ) {
                                if ( o.getData() != null && !o.getData().getJokesList().isEmpty() ) {
                                    //请求成功
                                    if ( page <= 1 ) {
                                        imgList.clear();
                                    }
                                    imgList.addAll(o.getData().getJokesList());
                                    imgAdapter.notifyDataSetChanged();

                                    if ( o.getData().getTotalPage() != page ) {
                                        imgAdapter.loadMoreComplete();
                                    } else {
                                        imgAdapter.loadMoreEnd();
                                    }
                                }
                            } else {
                                _toastHelper.show(o.getMessage());
                            }
                        }

                        @Override
                        public void onCompleted() {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e, int type) {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                        }
                    }, this));
            addSubscription(subscribe);
        }
    }

    class AnimationAdapter extends BaseQuickAdapter<JokesContentModel.JokesListBean, BaseViewHolder> {
        public AnimationAdapter(List list) {
            super(R.layout.item_reclcyerview_home_content, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, JokesContentModel.JokesListBean item) {
            helper.setText(R.id.richText, item.getContent());
            if ( item.getOrignUser() != null )
                helper.setText(R.id.tv_nickname, item.getOrignUser().getNickname());
            helper.setText(R.id.tv_create_time, item.getShowTime());
            helper.setText(R.id.tv_zan, item.getLikeCount() + "");
        }
    }

    class AnimationImageAdapter extends BaseQuickAdapter<JokesImgModel.JokesListBean, BaseViewHolder> {
        public AnimationImageAdapter(List list) {
            super(R.layout.item_reclcyerview_home_image, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, JokesImgModel.JokesListBean item) {
            helper.setText(R.id.richText, item.getContent());
            if ( item.getOrignUser() != null )
                helper.setText(R.id.tv_nickname, item.getOrignUser().getNickname());
            helper.setText(R.id.tv_create_time, item.getShowTime());
            helper.setText(R.id.tv_zan, item.getLikeCount() + "");
            Glide.with(mActivity).load(item.getImageUrl()).into(new GlideDrawableImageViewTarget(( ImageView ) helper.getView(R.id.iv_pic_single), Integer.MAX_VALUE));
        }
    }
}
