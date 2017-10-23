package com.cretin.ui.fragment.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cretin.R;
import com.cretin.core.di.HttpSubscriber;
import com.cretin.core.di.SubscriberOnNextListener;
import com.cretin.data.api.ApiDefaultConfig;
import com.cretin.data.api.model.JokesContentModel;
import com.cretin.data.api.model.JokesImgModel;
import com.cretin.data.api.model.ResultModel;
import com.cretin.data.api.service.JokesService;
import com.cretin.ui.activity.ShowBigPicActivity;
import com.cretin.ui.base.BaseFragment;
import com.cretin.util.ToastHelper;
import com.cretin.view.ItemButtomDecoration;
import com.wx.goodview.GoodView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.Subscription;

import static com.cretin.R.id.tv_zan;

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

    /**
     * @param type
     * @param isRec 是否推荐
     * @return
     */
    public static CommonHomeFragment newInstance(int type, boolean isRec) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putBoolean("isRec", isRec);
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
        if ( getArguments().getBoolean("isRec") ) {
            hidTitleView();
        } else {
            if ( getArguments().getInt("type") == TYPE_CONTENT ) {
                //文字
                hidBackTv();
                setMainTitle("段子大全");
            } else {
                //图片
                hidBackTv();
                setMainTitle("搞笑趣图");
            }
        }
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
            Observable<ResultModel<JokesContentModel>> binds = null;
            if ( getArguments().getBoolean("isRec") ) {
                binds = binds(_jokesService.getJokesRecList(page));
            } else {
                binds = binds(_jokesService.getJokesList(page));
            }
            Subscription subscribe = binds.subscribe(new HttpSubscriber<ResultModel<JokesContentModel>>
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
                            showContentView();
                        }

                        @Override
                        public void onError(Throwable e, int type) {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                            showErrorView();
                        }
                    }, this));
            addSubscription(subscribe);
        } else {
            //图片段子
            Observable<ResultModel<JokesImgModel>> binds = null;
            if ( getArguments().getBoolean("isRec") ) {
                binds = binds(_jokesService.getJokesImgRecList(page));
            } else {
                binds = binds(_jokesService.getJokesImgList(page));
            }
            Subscription subscribe = binds.subscribe(new HttpSubscriber<ResultModel<JokesImgModel>>
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
                            showContentView();
                        }

                        @Override
                        public void onError(Throwable e, int type) {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                            showErrorView();
                        }
                    }, this));
            addSubscription(subscribe);
        }
    }

    class AnimationAdapter extends BaseQuickAdapter<JokesContentModel.JokesListBean, BaseViewHolder> {
        private GoodView goodView = new GoodView(mActivity);

        public AnimationAdapter(List list) {
            super(R.layout.item_reclcyerview_home_content, list);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final JokesContentModel.JokesListBean item) {
            helper.setText(R.id.richText, item.getContent());
            if ( item.getOrignUser() != null )
                helper.setText(R.id.tv_nickname, item.getOrignUser().getNickname());
            helper.setText(R.id.tv_create_time, item.getShowTime());
            helper.setText(tv_zan, item.getLikeCount() + "");

            //设置赞数
            final TextView tv_zan = helper.getView(R.id.tv_zan);
            helper.setText(R.id.tv_zan, item.getLikeCount() + "");
            //点赞
            if ( item.getHasSpot() == 0 ) {
                //没有点赞过
                tv_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.zan_woniu_small), null, null, null);
                tv_zan.setTextColor(Color.parseColor("#9b9b9b"));
            } else {
                //点赞过了
                tv_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.zan_hover_woniu_small), null, null, null);
                tv_zan.setTextColor(getResources().getColor(R.color.common_bg));
            }

            tv_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int state = item.getHasSpot();
                    if ( state == 0 ) {
                        //没有点赞
                        goodView.setTextInfo("点赞+1", getResources().getColor(R.color.common_bg), 12);
                        goodView.show(tv_zan);
                        zan(true, helper.getPosition());
                    } else {
                        //点赞了
                        goodView.setTextInfo("取消点赞-1", Color.parseColor("#9b9b9b"), 12);
                        goodView.show(tv_zan);
                        zan(false, helper.getPosition());
                    }
                }
            });
        }
    }

    class AnimationImageAdapter extends BaseQuickAdapter<JokesImgModel.JokesListBean, BaseViewHolder> {
        private GoodView goodView = new GoodView(mActivity);

        public AnimationImageAdapter(List list) {
            super(R.layout.item_reclcyerview_home_image, list);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final JokesImgModel.JokesListBean item) {
            //设置内容
            helper.setText(R.id.richText, item.getContent());
            //设置昵称和头像
            if ( item.getOrignUser() != null ) {
                helper.setText(R.id.tv_nickname, item.getOrignUser().getNickname());
                if ( !TextUtils.isEmpty(item.getOrignUser().getAvatar()) ) {
                    Glide.with(mActivity).load(item.getOrignUser().getAvatar())
                            .into(( ImageView ) helper.getView(R.id.iv_uservavtar));
                }
            }

            //创建时间
            helper.setText(R.id.tv_create_time, item.getShowTime());

            //展示图片
            ImageView imageView = helper.getView(R.id.iv_pic_single);
            String imageUrl = item.getImageUrl();
            if ( !imageUrl.startsWith("http") )
                imageUrl = ApiDefaultConfig.BASE_ENDPOINT + imageUrl;
            Glide.with(mActivity).load(imageUrl).into(new GlideDrawableImageViewTarget(imageView, Integer.MAX_VALUE));
            final String finalImageUrl = imageUrl;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ShowBigPicActivity.class);
                    intent.putExtra("image_url", finalImageUrl);
                    intent.putExtra("details", item.getContent());
                    startActivity(intent);
                }
            });


            //设置赞数
            final TextView tv_zan = helper.getView(R.id.tv_zan);
            helper.setText(R.id.tv_zan, item.getLikeCount() + "");
            //点赞
            if ( item.getHasSpot() == 0 ) {
                //没有点赞过
                tv_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.zan_woniu_small), null, null, null);
                tv_zan.setTextColor(Color.parseColor("#9b9b9b"));
            } else {
                //点赞过了
                tv_zan.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.zan_hover_woniu_small), null, null, null);
                tv_zan.setTextColor(getResources().getColor(R.color.common_bg));
            }

            tv_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int state = item.getHasSpot();
                    if ( state == 0 ) {
                        //没有点赞
                        goodView.setTextInfo("点赞+1", getResources().getColor(R.color.common_theme_color), 12);
                        goodView.show(tv_zan);
                        zan(true, helper.getPosition());
                    } else {
                        //点赞了
                        goodView.setTextInfo("取消点赞-1", Color.parseColor("#9b9b9b"), 12);
                        goodView.show(tv_zan);
                        zan(false, helper.getPosition());
                    }
                }
            });
        }
    }

    /**
     * 点赞或者取消点赞
     *
     * @param flag true 点赞  false 取消点赞
     */
    private void zan(final boolean flag, final int position) {
        if ( getArguments().getInt("type") == TYPE_CONTENT ) {
            //文本类型
            String jokes_id = list.get(position).getJokeId();
            final JokesContentModel.JokesListBean jokesListBean = list.get(position);
            jokesListBean.setHasSpot(flag ? 1 : 0);
            try {
                int digg_count = jokesListBean.getLikeCount();
                jokesListBean.setLikeCount(flag ? (digg_count + 1) : (digg_count - 1));
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            list.set(position, jokesListBean);
            adapter.notifyDataSetChanged();
            Observable<ResultModel> binds = null;
            if ( flag ) {
                binds = binds(_jokesService.likes(jokes_id));
            } else {
                binds = binds(_jokesService.unlikes(jokes_id));
            }
            Subscription subscribe = binds.subscribe(new HttpSubscriber<ResultModel>
                    (new SubscriberOnNextListener<ResultModel>() {
                        @Override
                        public void onNext(ResultModel o) {
                            if ( o.getCode() == 1 ) {
                                //操作成功

                            } else if ( o.getCode() == 2 ) {
                                //操作失败
                                JokesContentModel.JokesListBean jokesListBean1 = list.get(position);
                                jokesListBean1.setHasSpot(flag ? 0 : 1);
                                int digg_count = jokesListBean1.getLikeCount();
                                jokesListBean1.setLikeCount(flag ? (digg_count - 1) : (digg_count + 1));
                                list.set(position, jokesListBean1);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCompleted() {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                            showContentView();
                        }

                        @Override
                        public void onError(Throwable e, int type) {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                            showErrorView();
                        }
                    }, this));
            addSubscription(subscribe);
        } else {
            //图片模式
            String jokes_id = imgList.get(position).getJokeId();
            final JokesImgModel.JokesListBean jokesListBean = imgList.get(position);
            jokesListBean.setHasSpot(flag ? 1 : 0);
            try {
                int digg_count = jokesListBean.getLikeCount();
                jokesListBean.setLikeCount(flag ? (digg_count + 1) : (digg_count - 1));
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            imgList.set(position, jokesListBean);
            imgAdapter.notifyDataSetChanged();
            Observable<ResultModel> binds = null;
            if ( flag ) {
                binds = binds(_jokesService.imgLikes(jokes_id));
            } else {
                binds = binds(_jokesService.imgUnlikes(jokes_id));
            }
            Subscription subscribe = binds.subscribe(new HttpSubscriber<ResultModel>
                    (new SubscriberOnNextListener<ResultModel>() {
                        @Override
                        public void onNext(ResultModel o) {
                            if ( o.getCode() == 1 ) {
                                //操作成功

                            } else if ( o.getCode() == 2 ) {
                                //操作失败
                                JokesImgModel.JokesListBean jokesListBean1 = imgList.get(position);
                                jokesListBean1.setHasSpot(flag ? 0 : 1);
                                int digg_count = jokesListBean1.getLikeCount();
                                jokesListBean1.setLikeCount(flag ? (digg_count - 1) : (digg_count + 1));
                                imgList.set(position, jokesListBean1);
                                imgAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCompleted() {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                            showContentView();
                        }

                        @Override
                        public void onError(Throwable e, int type) {
                            stopDialog();
                            swipRefresh.setRefreshing(false);
                            showErrorView();
                        }
                    }, this));
            addSubscription(subscribe);
        }
    }
}
