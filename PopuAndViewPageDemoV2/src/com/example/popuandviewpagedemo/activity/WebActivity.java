/**   
 * 文件名：WebActivity.java
 * 创建日期：2015-3-20   
 * Copyright (c) 2015 by Peter.版权所有.
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
 * 项目名称：JsonNews<br>
 * 类名称：WebActivity <br>
 * 类描述： TODO(请输入类的描述) <br>
 * 创建人：Peter(李春福) <br>
 * 创建时间：2015-3-20 下午3:05:38 <br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
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
		// 适应屏幕
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
		// 返回
		case R.id.img_back:
			finish();
			break;
		case R.id.img_web_share:
			shareText(context, "分享", "资源分享：\t" + url + "\n[该消息来自商报客户端]");
			break;
		case R.id.send:
			if (et_discuss.getText().toString().length() == 0) {
				Toast.makeText(WebActivity.this, "亲，还是说点什么吧！",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						WebActivity.this,
						"您评论的内容：" + et_discuss.getText().toString()
								+ "\n感谢您的评论！", Toast.LENGTH_LONG).show();
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
