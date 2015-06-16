/**   
 * 文件名：SplashActivity.java
 * 创建日期：2015-3-24   
 * Copyright (c) 2015 by Peter.版权所有.
 */

package com.example.popuandviewpagedemo.activity;

import java.util.Timer;
import java.util.TimerTask;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.onlineconfig.OnlineConfigCallBack;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.popuandviewpagedemo.R;

/**
 * 项目名称：ListViewFrashDemo1<br>
 * 类名称：SplashActivity <br>
 * 类描述：显示欢迎界面并跳转到主界面 <br>
 * 创建人：Peter(李春福) <br>
 * 创建时间：2015-3-24 上午9:26:58 <br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * 
 * @version V1.0
 */

public class SplashActivity extends Activity {
	private static final String YouMiAPPID = "d673eb50fb682584";
	private static final String YouMiAPPSecret = "46dd98a6b759d6c2";
	String mykey = "mycustomkey"; // key
	String defaultValue = null; // 默认的 value，当获取不到在线参数时，会返回该值
	private static boolean isAdCanShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent splashIntent = new Intent(SplashActivity.this,
						ViewPageActivity.class);
				startActivity(splashIntent);
				finish();
			}
		};
		timer.schedule(task, 1000 * 2);

		showYouMiAd();
	}

	private void showYouMiAd() {
		AdManager.getInstance(this).init(YouMiAPPID, YouMiAPPSecret, false);

		// 如果仅仅使用开屏，需要取消注释以下注释，如果使用了开屏和插屏，则不需要。
		SpotManager.getInstance(this).loadSpotAds();
		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图�?
		SpotManager.getInstance(this).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);

		// 展示插播广告，可以不调用loadSpot独立使用
		SpotManager.getInstance(SplashActivity.this).showSpotAds(
				SplashActivity.this, new SpotDialogListener() {
					@Override
					public void onShowSuccess() {
						Log.i("YoumiAdDemo", "展示成功");
					}

					@Override
					public void onShowFailed() {
						Log.i("YoumiAdDemo", "展示失败");
					}

					@Override
					public void onSpotClosed() {
						Log.i("YoumiAdDemo", "展示关闭");
					}

				});
		
		AdManager.getInstance(this).asyncGetOnlineConfig(mykey, new OnlineConfigCallBack() {
		    @Override
		    public void onGetOnlineConfigSuccessful(String key, String value) {
		        // TODO Auto-generated method stub
		        // 获取在线参数成功
		    	if (key.equals(mykey)) {
		    		   if (value.equals("1")) {
		    		    // 这里设置广告开关——开启
		    		    isAdCanShow = true;
		    		   } else if (value.equals("0")) {
		    		    // 这里设置广告开关——关闭
		    		    isAdCanShow = false;
		    		   }
		    		  }

		    }

		    @Override
		    public void onGetOnlineConfigFailed(String key) {
		        // TODO Auto-generated method stub
		        // 获取在线参数失败，可能原因有：键值未设置或为空、网络异常、服务器异常
		    }
		});
		
	}
	private boolean showOfferWall() {
		 if (isAdCanShow) {
		  OffersManager.getInstance(this).showOffersWall();
		 }
		return true;
		}

	public void onBackPressed() {

		// 如果有需要，可以点击后退关闭插播广告。
		if (!SpotManager.getInstance(this).disMiss()) {
			// 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {

		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}
}
