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

public class WebActivity extends BaseActivity implements OnClickListener {

	private ProgressWebView webView;
	private Context context;

	private ImageButton img_back, img_web_share, img_send;
	private EditText et_discuss;
	StringBuffer url;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webviewlayout);
		context = this;
		initViews();

		WebSettings settings = webView.getSettings();
		// ��Ӧ��Ļ
		settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		settings.setUseWideViewPort(true);
		settings.setSupportZoom(true);
		settings.setTextZoom(200);
		settings.setLoadWithOverviewMode(true);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		if (mDensity == 240) {
			settings.setDefaultZoom(ZoomDensity.FAR);

		} else if (mDensity == 160) {
			settings.setDefaultZoom(ZoomDensity.MEDIUM);
		} else if (mDensity == 120) {
			settings.setDefaultZoom(ZoomDensity.CLOSE);
		} else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
			settings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == DisplayMetrics.DENSITY_TV) {
			settings.setDefaultZoom(ZoomDensity.FAR);
		} else {
			settings.setDefaultZoom(ZoomDensity.MEDIUM);
		}

		settings.setJavaScriptEnabled(true);

		String id = getIntent().getStringExtra("flag");
		url = new StringBuffer(URL.WebAPi + id);
		// url = new StringBuffer(
		// "http://api.009yes.mobi:8080/index.php/appapi/letterdetails/99/22");
		webView.loadUrl(url.toString());
	}

	private void initViews() {
		webView = (ProgressWebView) findViewById(R.id.webview);
		img_back = (ImageButton) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		img_web_share = (ImageButton) findViewById(R.id.img_web_share);
		img_web_share.setOnClickListener(this);
		img_send = (ImageButton) findViewById(R.id.send);
		img_send.setOnClickListener(this);
		et_discuss = (EditText) findViewById(R.id.discuss);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// ����
		case R.id.img_back:
			finish();
			break;
		case R.id.img_web_share:
			shareText(context, "����", "��Դ����\t" + url + "\n[����Ϣ�����̱��ͻ���]");
			break;
		case R.id.send:
			if (et_discuss.getText().toString().length() == 0) {
				Toast.makeText(WebActivity.this, "�ף�����˵��ʲô�ɣ�",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						WebActivity.this,
						"�����۵����ݣ�" + et_discuss.getText().toString()
								+ "\n��л�������ۣ�", Toast.LENGTH_LONG).show();
				et_discuss.setText("");
			}
			break;
		default:
			break;
		}

	}

	public static void shareText(Context context, String title, String text) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(intent, title));
	}
}
