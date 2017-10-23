package com.cretin.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.cretin.R;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import uk.co.senab.photoview.PhotoView;

public class ShowBigPicActivity extends AppCompatActivity {
    private PhotoView photoView;
    private ImageView imageView;
    private TextView tvDes;
    private ImageView ivlose;
    private ScrollView scrollView;
    private String imageUrl = "";
    private CircleProgressDialog circleProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_pic);
        //全屏显示图片
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        photoView = ( PhotoView ) findViewById(R.id.photoView);
        imageView = ( ImageView ) findViewById(R.id.imageView);
        ivlose = ( ImageView ) findViewById(R.id.iv_close);
        tvDes = ( TextView ) findViewById(R.id.tv_details);
        scrollView = ( ScrollView ) findViewById(R.id.scrollView);

        ivlose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        circleProgressDialog = new CircleProgressDialog(this);
        //可对对话框的大小、进度条的颜色、宽度、文字的颜色、内容等属性进行设置
        circleProgressDialog.setDialogSize(15);
        circleProgressDialog.setProgressColor(Color.parseColor("#FFFFFF"));
        circleProgressDialog.setTextColor(Color.parseColor("#FFFFFF"));
        circleProgressDialog.setText("正在加载...");
        circleProgressDialog.showDialog();  //显示对话框

        //通过后缀判断图片类型
        imageUrl = getIntent().getStringExtra("image_url");
        String details = getIntent().getStringExtra("details");
        if ( !TextUtils.isEmpty(details) )
            tvDes.setText(details);
        if ( !TextUtils.isEmpty(imageUrl) ) {
            if ( imageUrl.endsWith(".gif")
                    || imageUrl.endsWith(".giF")
                    || imageUrl.endsWith(".gIf")
                    || imageUrl.endsWith(".gIF")
                    || imageUrl.endsWith(".Gif")
                    || imageUrl.endsWith(".GiF")
                    || imageUrl.endsWith(".GIf")
                    || imageUrl.endsWith(".GIF") ) {
                //gif图片
                scrollView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageUrl)
                        .listener(new RequestListener() {
                            @Override
                            public boolean onException(Exception arg0, Object arg1,
                                                       Target arg2, boolean arg3) {
                                circleProgressDialog.dismiss();
                                Toast.makeText(ShowBigPicActivity.this,
                                        "图片加载错误", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Object arg0, Object arg1,
                                                           Target arg2, boolean arg3, boolean arg4) {
                                circleProgressDialog.dismiss();
                                return false;
                            }
                        })
                        .into(new GlideDrawableImageViewTarget(imageView, Integer.MAX_VALUE));
            } else {
                Glide.with(this).load(imageUrl)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                circleProgressDialog.dismiss();
                                Toast.makeText(ShowBigPicActivity.this,
                                        "图片加载错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResourceReady(Bitmap mBitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inJustDecodeBounds = true;//只测量
                                float height = mBitmap.getHeight();
                                float width = mBitmap.getWidth();
                                //再拿到屏幕的宽
                                WindowManager windowManager = getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                float screenWidth = display.getWidth();
                                float screenHeight = display.getHeight();
                                //计算如果让照片是屏幕的宽，选要乘以多少？
                                float scale = screenWidth / width;
                                if ( scale == 0 ) {
                                    scale = 1;
                                }
                                //这个时候。只需让图片的宽是屏幕的宽，高乘以比例
                                int displayHeight = ( int ) (height * scale);//要显示的高，这样避免失真
                                //最终让图片按照宽是屏幕 高是等比例缩放的大小
                                if ( displayHeight < screenHeight ) {
                                    findViewById(R.id.scrollView).setEnabled(false);
                                    displayHeight = ( int ) screenHeight;
                                } else {
                                    photoView.setZoomable(false);
                                }
                                LinearLayout.LayoutParams layoutParams =
                                        new LinearLayout.LayoutParams(( int ) screenWidth, displayHeight);
                                photoView.setLayoutParams(layoutParams);
                                photoView.setImageBitmap(mBitmap);
                                circleProgressDialog.dismiss();
                            }
                        });
            }
        }
    }
}
