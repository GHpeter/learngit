package com.example.popuandviewpagedemo.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;

/**
 * @ClassName: PupupViewManager
 * @Description: TODO(PopWindow) 
 * @author danni
 * @date 2013-7-23 下午03:12:54
 * 
 */
public class PupupViewManager {
	private PopupWindow popupWindowm;
	private Context context;
	
	public PupupViewManager(Context context) {
		this.context = context;
	}

	/**
	 * pupupwindow 管理
	 * @param x 位置
	 * @param y 位置
	 * @param popupView 要显示的布局
	 * @param v 要挂载的布局
	 */
	public PopupWindow showPopupWindowm(int x, int y,View popupView,View v) {
		if (popupWindowm != null) {
			popupWindowm.dismiss();
		}
		// 创建显示动画
		ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		sa.setDuration(200);
		//LinearLayout.LayoutParams.MATCH_PARENT
		// 创建一个窗体
		popupWindowm = new PopupWindow(popupView, 200, 300);
		popupWindowm.setBackgroundDrawable(new BitmapDrawable());
		popupWindowm.setFocusable(true);
        //窗口的位置
		popupWindowm
				.showAtLocation(v, Gravity.LEFT | Gravity.TOP, x, y+10);
		popupView.startAnimation(sa);
		return popupWindowm;
	}

}