package com.example.popuandviewpagedemo.definedViews;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 带进度框的webview Created by iosteam on 13-6-21.
 */
public class ProgressWebView extends WebView {

	private ProgressBar progressbar;

	private LinearLayout linearLayout;

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null,
				R.attr.progressBarStyleLarge);
		linearLayout = new LinearLayout(context);

		LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		linearLayout.setLayoutParams(LP_FW);
		linearLayout.setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams LP_WE = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		progressbar.setLayoutParams(LP_WE);

		linearLayout.addView(progressbar);
		addView(linearLayout);
		setWebChromeClient(new WebChromeClient());
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				linearLayout.setVisibility(GONE);
			} else {
				if (linearLayout.getVisibility() == GONE)
					linearLayout.setVisibility(VISIBLE);
			}
			super.onProgressChanged(view, newProgress);
		}

	}


}
