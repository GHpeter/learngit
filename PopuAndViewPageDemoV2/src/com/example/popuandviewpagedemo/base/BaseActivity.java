/**   
 * �ļ�����BaseActivity.java
 * �������ڣ�2015-3-24   
 * Copyright (c) 2015 by Peter.��Ȩ����.
 */

package com.example.popuandviewpagedemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

/**
 * ��Ŀ���ƣ�ListViewFrashDemo1<br>
 * �����ƣ�BaseActivity <br>
 * �������� Ӧ�ó���Activity�Ļ��� <br>
 * �����ˣ�Peter(���) <br>
 * ����ʱ�䣺2015-3-24 ����9:19:20 <br>
 * �޸��ˣ� <br>
 * �޸�ʱ�䣺 <br>
 * �޸ı�ע��
 * 
 * @version V1.0
 */

public class BaseActivity extends Activity {
	// �Ƿ�����ȫ��
	private boolean allowFullScreen = true;

	// �Ƿ���������
	private boolean allowDestroy = true;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allowFullScreen = true;
		// ���Activity����ջ
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// ����Activity&�Ӷ�ջ���Ƴ�
		AppManager.getAppManager().finishActivity(this);
	}

	public boolean isAllowFullScreen() {
		return allowFullScreen;
	}

	/**
	 * �����Ƿ����ȫ��
	 * 
	 * @param allowFullScreen
	 */
	public void setAllowFullScreen(boolean allowFullScreen) {
		this.allowFullScreen = allowFullScreen;
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
			view.onKeyDown(keyCode, event);
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
