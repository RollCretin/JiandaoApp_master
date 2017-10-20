package com.cretin.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @描述 TODO
 * @项目名称 App_imooc
 * @包名 com.android.imooc.draglayout
 * @类名 XLinearLayout
 * @author chenlin
 * @date 2015年5月22日 下午10:41:03
 */

public class XLinearLayout extends LinearLayout {
	private DragLayout mLayout;

	public XLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public XLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XLinearLayout(Context context) {
		super(context);
	}

	public void setDragLayout(DragLayout layout) {
		mLayout = layout;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 如果是当前是关闭状态，按之前的方法判断
		if (mLayout.getStatus() == DragLayout.Status.Close) {
			return super.onInterceptTouchEvent(ev);
		} else {
			// 不把事件传递给子控件
			return true;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 如果是当前是关闭状态，按之前的方法处理
		if (mLayout.getStatus() == DragLayout.Status.Close) {
			return super.onTouchEvent(event);
		} else {
			//手指抬起
			if (event.getAction() == MotionEvent.ACTION_UP) {
				mLayout.close();
			}
			// 不把事件传递给子控件
			return true;
		}
		
	}

}
