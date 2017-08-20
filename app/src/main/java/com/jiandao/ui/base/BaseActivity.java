package com.jiandao.ui.base;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiandao.BaseApplication;
import com.jiandao.R;
import com.jiandao.core.di.ApplicationComponent;
import com.jiandao.core.di.HasComponent;
import com.jiandao.core.di.Injectable;
import com.jiandao.interfaces.OnTitleAreaCliclkListener;
import com.jiandao.utils.ViewUtils;
import com.jiandao.view.CustomProgressDialog;
import com.trello.rxlifecycle.ActivityEvent;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by grubber on 2017/1/6.
 */
public abstract class BaseActivity extends ParentActivity
        implements HasComponent<ApplicationComponent>, Injectable {
    private CompositeSubscription mCompositeSubscription;
    private CustomProgressDialog dialog;
    private TextView tvMainTitle;
    private TextView tvMainBack;
    private ImageView ivMainRight;
    private TextView tvMainRight;

    private RelativeLayout relaLoadContainer;
    private LinearLayout loadContainerLoading;
    private LinearLayout loadContainerFailed;
    private View lineDivider;
    private LinearLayout llMainTitle;


    private AnimationDrawable animationDrawable;
    private OnTitleAreaCliclkListener onTitleAreaCliclkListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.layout_base_activity, null);
        setContentView(view);
        initHeadView(view);
        initContentView(view);
        if ( isKitkat ) {
            view.findViewById(R.id.ll_main_title).setPadding(0, ViewUtils.getStatusBarHeights(this), 0, 0);
        }
        injectMembers();
        initData();
        refrehPageData();
        initEvent();
    }

    protected void addSubscription(Subscription s) {
        if ( this.mCompositeSubscription == null ) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    private void initContentView(View view) {
        FrameLayout container = ( FrameLayout ) view.findViewById(R.id.main_container);
        relaLoadContainer = ( RelativeLayout ) view.findViewById(R.id.load_container);
        loadContainerLoading = ( LinearLayout ) view.findViewById(R.id.load_container_loading);
        loadContainerFailed = ( LinearLayout ) view.findViewById(R.id.load_container_failed);
        llMainTitle = ( LinearLayout ) view.findViewById(R.id.ll_main_title);
        lineDivider = view.findViewById(R.id.line_divider);
        ImageView imageView = ( ImageView ) view
                .findViewById(R.id.loading_image);
        animationDrawable = ( AnimationDrawable ) imageView
                .getBackground();
        animationDrawable.start();
        if ( getContentViewId() != 0 ) {
            View v = getLayoutInflater().inflate(getContentViewId(), null);
            ButterKnife.bind(this, v);
            container.addView(v, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            initView(v);
        }
        loadContainerFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressView();
                refrehPageData();
            }
        });
    }

    //隐藏title栏的分界线
    public void hidLineDivider() {
        if ( lineDivider.getVisibility() == View.VISIBLE )
            lineDivider.setVisibility(View.GONE);
    }

    public View getHeadView() {
        return llMainTitle;
    }

    //展示主视图
    public void showContentView() {
        //隐藏掉整个加载中和错误页面
        if ( relaLoadContainer != null &&
                relaLoadContainer.getVisibility() == View.VISIBLE ) {
            relaLoadContainer.setVisibility(View.GONE);
        }
        if ( animationDrawable != null
                && animationDrawable.isRunning() )
            animationDrawable.stop();
    }

    //显示正在加载视图
    public void showProgressView() {
        relaLoadContainer.setVisibility(View.VISIBLE);
        hidErrorView();
        if ( loadContainerLoading != null && loadContainerLoading.getVisibility() == View.GONE ) {
            loadContainerLoading.setVisibility(View.VISIBLE);
        }
        if ( animationDrawable != null )
            animationDrawable.start();
    }

    //显示正在加载视图
    private void hidProgressView() {
        relaLoadContainer.setVisibility(View.VISIBLE);
        if ( loadContainerLoading != null && loadContainerLoading.getVisibility() == View.VISIBLE ) {
            loadContainerLoading.setVisibility(View.GONE);
        }
        if ( animationDrawable != null
                && animationDrawable.isRunning() )
            animationDrawable.stop();
    }

    //隐藏返回按钮
    public void hidBackTv() {
        if ( tvMainBack != null && tvMainBack.getVisibility() == View.VISIBLE )
            tvMainBack.setVisibility(View.GONE);
    }

    //显示加载错误
    public void showErrorView() {
        relaLoadContainer.setVisibility(View.VISIBLE);
        hidProgressView();
        if ( loadContainerFailed != null && loadContainerFailed.getVisibility() == View.GONE ) {
            loadContainerFailed.setVisibility(View.VISIBLE);
        }
    }

    //显示加载错误
    private void hidErrorView() {
        if ( loadContainerFailed != null && loadContainerFailed.getVisibility() == View.GONE ) {
            loadContainerFailed.setVisibility(View.VISIBLE);
        }
    }

    //初始化头部视图
    private void initHeadView(View view) {
        if ( view != null ) {
            tvMainTitle = ( TextView ) view.findViewById(R.id.tv_title_info);
            tvMainBack = ( TextView ) view.findViewById(R.id.tv_back);
            ivMainRight = ( ImageView ) view.findViewById(R.id.iv_right);
            tvMainRight = ( TextView ) view.findViewById(R.id.tv_right);
            tvMainBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    if ( onTitleAreaCliclkListener != null )
                        onTitleAreaCliclkListener.onTitleAreaClickListener(v);
                }
            });
            ivMainRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( onTitleAreaCliclkListener != null )
                        onTitleAreaCliclkListener.onTitleAreaClickListener(v);
                }
            });
            tvMainRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( onTitleAreaCliclkListener != null )
                        onTitleAreaCliclkListener.onTitleAreaClickListener(v);
                }
            });
        }
    }

    @Override
    public ApplicationComponent getComponent() {
        return BaseApplication.from(this).getComponent();
    }

    protected <T> Observable<T> binds(Observable<T> observable) {
        return observable.compose(this.<T>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Observable<T> binds(Observable<T> observable, ActivityEvent
            activityEvent) {
        return observable.compose(this.<T>bindUntilEvent(activityEvent))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onDestroy() {
        if ( this.mCompositeSubscription != null ) {
            this.mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg, boolean cancelable) {
        if ( dialog == null ) {
            dialog = CustomProgressDialog.createDialog(this);
            if ( msg != null && !msg.equals("") ) {
                dialog.setMessage(msg);
            }
        }
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg) {
        showDialog(msg, true);
    }

    /**
     * 关闭对话框
     */
    public void stopDialog() {
        if ( dialog != null && dialog.isShowing() ) {
            dialog.dismiss();
        }
    }

    protected abstract void initData();

    /**
     * 将整个页面需要在刷新时加载的数据操作都放在这个里面 当出现无网络界面
     * 点击屏幕刷新数据的时候会调用里面的方法 在数据加载完请记得调用showContent方法
     */
    protected abstract void refrehPageData();

    protected abstract int getContentViewId();

    protected void initEvent() {

    }

    //获取标题栏的几个控件 供用户自己设计
    public TextView getMainTitle() {
        return tvMainTitle;
    }

    public TextView getLeftBackView() {
        return tvMainBack;
    }

    public TextView getRightTv() {
        return tvMainRight;
    }

    public ImageView getRightIv() {
        return ivMainRight;
    }

    public void setOnTitleAreaCliclkListener(OnTitleAreaCliclkListener onTitleAreaCliclkListener) {
        this.onTitleAreaCliclkListener = onTitleAreaCliclkListener;
    }

    //设置Title
    protected void setMainTitle(String title) {
        if ( !TextUtils.isEmpty(title) )
            tvMainTitle.setText(title);
    }

    //设置TitleColor
    protected void setMainTitleColor(String titleColor) {
        if ( !TextUtils.isEmpty(titleColor) )
            setMainTitleColor(Color.parseColor(titleColor));
    }

    //设置TitleColor
    protected void setMainTitleColor(int titleColor) {
        tvMainTitle.setTextColor(titleColor);
    }

    //设置右边TextView颜色
    protected void setMainTitleRightColor(int tvRightColor) {
        tvMainRight.setTextColor(tvRightColor);
    }

    //设置右边TextView颜色
    protected void setMainTitleRightColor(String tvRightColor) {
        if ( !TextUtils.isEmpty(tvRightColor) )
            setMainTitleRightColor(Color.parseColor(tvRightColor));
    }

    //设置右边TextView大小
    protected void setMainTitleRightSize(int size) {
        tvMainRight.setTextSize(size);
    }

    //设置右边TextView内容
    protected void setMainTitleRightContent(String content) {
        if ( !TextUtils.isEmpty(content) ) {
            if ( tvMainRight.getVisibility() == View.GONE )
                tvMainRight.setVisibility(View.VISIBLE);
            tvMainRight.setText(content);
        }
    }

    //设置又边ImageView资源
    protected void setMainRightIvRes(int res) {
        if ( ivMainRight.getVisibility() == View.GONE )
            ivMainRight.setVisibility(View.VISIBLE);
        ivMainRight.setImageResource(res);
    }
}


