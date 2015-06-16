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
 * @date 2013-7-23 ����03:12:54
 * 
 */
public class PupupViewManager {
	private PopupWindow popupWindowm;
	private Context context;
	
	public PupupViewManager(Context context) {
		this.context = context;
	}

	/**
	 * pupupwindow ����
	 * @param x λ��
	 * @param y λ��
	 * @param popupView Ҫ��ʾ�Ĳ���
	 * @param v Ҫ���صĲ���
	 */
	public PopupWindow showPopupWindowm(int x, int y,View popupView,View v) {
		if (popupWindowm != null) {
			popupWindowm.dismiss();
		}
		// ������ʾ����
		ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		sa.setDuration(200);
		//LinearLayout.LayoutParams.MATCH_PARENT
		// ����һ������
		popupWindowm = new PopupWindow(popupView, 200, 300);
		popupWindowm.setBackgroundDrawable(new BitmapDrawable());
		popupWindowm.setFocusable(true);
        //���ڵ�λ��
		popupWindowm
				.showAtLocation(v, Gravity.LEFT | Gravity.TOP, x, y+10);
		popupView.startAnimation(sa);
		return popupWindowm;
	}

}