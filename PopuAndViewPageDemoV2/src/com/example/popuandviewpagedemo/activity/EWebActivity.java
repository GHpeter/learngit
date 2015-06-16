/**   
 * �ļ�����WebActivity.java
 * �������ڣ�2015-3-20   
 * Copyright (c) 2015 by Peter.��Ȩ����.
 */

package com.example.popuandviewpagedemo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.popuandviewpagedemo.R;
import com.example.popuandviewpagedemo.Contacts.URL;
import com.example.popuandviewpagedemo.base.BaseActivity;
import com.example.popuandviewpagedemo.definedViews.ProgressWebView;

/**
 * ��Ŀ���ƣ�JsonNews<br>
 * �����ƣ�WebActivity <br>
 * �������� TODO(�������������) <br>
 * �����ˣ�Peter(���) <br>
 * ����ʱ�䣺2015-3-20 ����3:05:38 <br>
 * �޸��ˣ� <br>
 * �޸�ʱ�䣺 <br>
 * �޸ı�ע��
 * 
 * @version V1.0
 */

public class EWebActivity extends BaseActivity implements OnClickListener {

	private ProgressWebView webView;
	StringBuffer url;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.e_webview_layout);

		initViews();

		WebSettings settings = webView.getSettings();
		// ��Ӧ��Ļ
		settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		settings.setUseWideViewPort(true);
		settings.setSupportZoom(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);

		url = new StringBuffer(URL.ElectronicNewIp);
		webView.loadUrl(url.toString());
	}

	private void initViews() {
		webView = (ProgressWebView) findViewById(R.id.webview);
	}

	@Override
	public void onClick(View v) {
	}

}
