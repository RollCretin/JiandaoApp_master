package com.cretin.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class ShowBigPicActivity extends AppCompatActivity implements View.OnTouchListener {
    private final Rect mRect = new Rect();
    private BitmapRegionDecoder mDecoder;
    private ImageView mView;
    private DisplayMetrics dm;
    private int screenHeight;
    private int screenWidth;
    //private int downX;
    private int downY;
    private int startY;
    private int showHeight;
    private int imgHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DisplayMetrics();

        mView = new ImageView(this);
        mView.setAdjustViewBounds(true);
        mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mView.setScaleType(ImageView.ScaleType.CENTER);
        mView.setOnTouchListener(this);

        setContentView(mView);
        Glide.with(this).load("http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/11/9C435181D978A3C7C54BEB2CBB296AC5.jpg")
                .asBitmap().toBytes()
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        try {
                            mDecoder = BitmapRegionDecoder.newInstance(bytes, 0, bytes.length, true);
                            mView.post(new Runnable() {

                                @Override
                                public void run() {
                                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                                    screenHeight = dm.heightPixels;
                                    screenWidth = dm.widthPixels;

                                    mRect.set(0, 0, screenWidth, screenHeight);
                                    Bitmap bm = mDecoder.decodeRegion(mRect, null);
                                    mView.setImageBitmap(bm);

                                    showHeight = screenHeight;
                                    startY = 0;

                                    imgHeight = mDecoder.getHeight();
                                }
                            });

                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;

        switch ( action ) {
            case MotionEvent.ACTION_DOWN:
                //downX = (int) event.getX();;
                downY = ( int ) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //setImageRegion(x, y);
                int x = ( int ) event.getX();
                int y = ( int ) event.getY();

                int deltaY = downY - y;
                downY = y;

                System.out.println(showHeight);
                System.out.println(deltaY);
                if ( showHeight <= screenHeight && deltaY < 0 ) {
                    break;
                } else if ( showHeight >= imgHeight + 500 && deltaY > 0 ) {
                    //System.out.println("else if");
                    break;
                }
                showHeight += deltaY;
                startY += deltaY;
                mRect.set(0, startY, screenWidth, showHeight);
                Bitmap bm = mDecoder.decodeRegion(mRect, null);
                mView.setImageBitmap(bm);
                //System.out.println(deltaY);

                break;
        }
        return true;
    }
}
