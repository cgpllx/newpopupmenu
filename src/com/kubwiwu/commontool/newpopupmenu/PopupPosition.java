package com.kubwiwu.commontool.newpopupmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupPosition {
	private Context mContext;
	private WindowManager mWindowManager;

	private PopupWindow mPopupWindow;

	private int mWidth = 240;
	private int mhight=300;
	private float mScale;
	View contentView;
	public PopupPosition(View contentView) {
		mContext = contentView.getContext();
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(metrics);
		mScale = metrics.scaledDensity;


		mPopupWindow = new PopupWindow(mContext);
		mPopupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					mPopupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		this.contentView=contentView;
		mPopupWindow.setContentView(contentView);
		// setContentView(mInflater.inflate(R.layout.popup_menu, null));
	}

	/**
	 * Show popup menu.
	 */
	public void show() {
		show(null);
	}

	/**
	 * Show popup menu.
	 *
	 * @param anchor
	 */
	public void show(View anchor) {
		preShow();
		if (anchor == null) {
			View parent = ((Activity) mContext).getWindow().getDecorView();
			mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
			return;
		}
		int xPos, yPos;
		int[] location = new int[2];
		anchor.getLocationOnScreen(location);

		Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[0] + anchor.getHeight());

//		mContentView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		mContentView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootHeight = contentView.getMeasuredHeight();
		int screenHeight = mWindowManager.getDefaultDisplay().getHeight();

		// Set x-coordinate to display the popup menu
		xPos = anchorRect.centerX() - mPopupWindow.getWidth() / 2;
		
		int dyTop = anchorRect.top;
		int dyBottom = screenHeight + rootHeight;
		boolean onTop = dyTop > dyBottom;

		// Set y-coordinate to display the popup menu
		if (onTop) {
			yPos = anchorRect.top - rootHeight;
		} else {
			if (anchorRect.bottom > dyTop) {
				yPos = anchorRect.bottom - 20;
			} else {
				yPos = anchorRect.top - anchorRect.bottom + 50;
			}
		}
//		yPos=contentView.get
		mPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, anchorRect.centerY() - 0);
//		mPopupWindow.showAtLocation(anchor, Gravity.BOTTOM, xPos, yPos - 0);
	}

	private void preShow() {
		int width = (int) (mWidth * mScale);
		mPopupWindow.setWidth(width);
		mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		// mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.panel_background));
	}

	/**
	 * Dismiss the popup menu.
	 */
	public void dismiss() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

	/**
	 * Change the popup's width.
	 *
	 * @param width
	 */
	public void setWidth(int width) {
		mWidth = width;
	}
}
