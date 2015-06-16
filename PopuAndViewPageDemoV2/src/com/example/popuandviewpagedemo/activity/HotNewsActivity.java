package com.example.popuandviewpagedemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.popuandviewpagedemo.R;
import com.example.popuandviewpagedemo.Contacts.URL;
import com.example.popuandviewpagedemo.JsonPull.JsonParse;
import com.example.popuandviewpagedemo.adapter.NewsAdapter;
import com.example.popuandviewpagedemo.base.BaseActivity;
import com.example.popuandviewpagedemo.bean.NewsBean;
import com.example.popuandviewpagedemo.definedViews.CustomProgressDialog;
import com.example.popuandviewpagedemo.definedViews.ReFlashListView;
import com.example.popuandviewpagedemo.definedViews.ReFlashListView.IReflashListener;
import com.example.popuandviewpagedemo.utils.HttpUtl;

public class HotNewsActivity extends BaseActivity implements IReflashListener {
	List<NewsBean> news_list = new ArrayList<NewsBean>();
	List<NewsBean> yaowenLists = new ArrayList<NewsBean>();
	List<NewsBean> hunanLists = new ArrayList<NewsBean>();
	List<NewsBean> guoneiLists = new ArrayList<NewsBean>();
	List<NewsBean> hotLists = new ArrayList<NewsBean>();
	List<NewsBean> shehuiLists = new ArrayList<NewsBean>();
	List<NewsBean> zhoushiLists = new ArrayList<NewsBean>();
	NewsAdapter adapter;
	ReFlashListView listview;
	private String jsonYaoWen, jsonHuNan, jsonGuoNei, jsonHot, jsonSheHui,
			jsonZhoushi;
	private HttpUtl httpUtl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_main);
		listview = (ReFlashListView) findViewById(R.id.listview);
		listview.setInterface(this);
		adapter = new NewsAdapter(this, news_list);
		listview.setAdapter(adapter);
		handler.sendEmptyMessage(3);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = URL.HuNanNewsIp;
				httpUtl = new HttpUtl();
				if (httpUtl.hasInternet(HotNewsActivity.this)) {
					jsonYaoWen = httpUtl.getJson(URL.yaowenNewsIp);
					jsonHuNan = httpUtl.getJson(URL.HuNanNewsIp);
					jsonGuoNei = httpUtl.getJson(URL.GuoNeiNewsIp);
					jsonHot = httpUtl.getJson(URL.HOTNewsIP);
					jsonSheHui = httpUtl.getJson(URL.shehuiNewsIp);
					jsonZhoushi = httpUtl.getJson(URL.ZhoushiNewsIp);

					if (!TextUtils.isEmpty(jsonYaoWen)
							|| !TextUtils.isEmpty(jsonHuNan)
							|| !TextUtils.isEmpty(jsonGuoNei)
							|| !TextUtils.isEmpty(jsonSheHui)
							|| !TextUtils.isEmpty(jsonHot)
							|| !TextUtils.isEmpty(jsonZhoushi)) {
						handler.sendEmptyMessage(1);
					}

				} else {
					Toast.makeText(getApplication(), "当前没有网络，请稍后再试",
							Toast.LENGTH_LONG).show();
				}
				handler.sendEmptyMessage(2);
			}
		}).start();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int pos = arg2 - 1;
				Intent inten = new Intent(HotNewsActivity.this,
						WebActivity.class);
				inten.putExtra("flag", news_list.get(pos).getId());
				startActivity(inten);
			}
		});
	}

	CustomProgressDialog dialog;

	/**
	 * 加载动画
	 * 
	 * @param v
	 */
	public void showmeidialog(View v) {
		dialog = new CustomProgressDialog(this, "正在加载中", R.anim.frame);
		dialog.show();

	}

	public void showdismisdialog() {
		dialog.dismiss();

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				setData();
				break;
			case 2:
				showdismisdialog();
				break;
			case 3:
				showmeidialog(listview);
				break;
			default:
				break;
			}

		}

	};

	private void showList(List<NewsBean> news_list) {

		if (adapter != null && news_list.size() > 0)
			adapter.onDateChange(news_list);

	}

	private void setData() {
		yaowenLists = new JsonParse().parser(jsonYaoWen);
		hunanLists = new JsonParse().parser(jsonHuNan);
		guoneiLists = new JsonParse().parser(jsonGuoNei);
		hotLists = new JsonParse().parser(jsonHot);
		shehuiLists = new JsonParse().parser(jsonSheHui);
		zhoushiLists = new JsonParse().parser(jsonZhoushi);

		List<NewsBean> beans = new ArrayList<NewsBean>();
		beans.addAll(yaowenLists);
		beans.addAll(hunanLists);
		beans.addAll(guoneiLists);
		beans.addAll(hotLists);
		beans.addAll(shehuiLists);
		beans.addAll(zhoushiLists);
		int j = 0, n = 0, k = 0, l = 0, m = 0, f = 0;
		for (int i = 0; i < beans.size(); i++) {
			int result = i % 6;
			switch (result) {
			case 0:
				if (yaowenLists != null && yaowenLists.size() > j)
					news_list.add(yaowenLists.get(j++));

			case 1:
				if (hunanLists != null && hunanLists.size() > n)
					news_list.add(hunanLists.get(n++));

			case 2:
				if (guoneiLists != null && guoneiLists.size() > k)
					news_list.add(guoneiLists.get(k++));
			case 3:
				if (hotLists != null && hotLists.size() > l)
					news_list.add(hotLists.get(l++));
			case 4:
				if (shehuiLists != null && shehuiLists.size() > m)
					news_list.add(shehuiLists.get(m++));
			case 5:
				if (zhoushiLists != null && zhoushiLists.size() > f)
					news_list.add(zhoushiLists.get(f++));
			default:
				break;
			}
		}
		showList(news_list);

	}

	private void setReflashData() {
		handler.sendEmptyMessage(1);
		for (int i = 0; i < 1; i++) {
			yaowenLists = new JsonParse().parser(jsonYaoWen);
			hunanLists = new JsonParse().parser(jsonHuNan);
			guoneiLists = new JsonParse().parser(jsonGuoNei);
			hotLists = new JsonParse().parser(jsonHot);
			shehuiLists = new JsonParse().parser(jsonSheHui);
			zhoushiLists = new JsonParse().parser(jsonZhoushi);

			List<NewsBean> beans = new ArrayList<NewsBean>();
			beans.addAll(yaowenLists);
			beans.addAll(hunanLists);
			beans.addAll(guoneiLists);
			beans.addAll(hotLists);
			beans.addAll(shehuiLists);
			beans.addAll(zhoushiLists);
			int j = 0, n = 0, k = 0, l = 0, m = 0, f = 0;
			for (int t = 1; t < beans.size(); t++) {
				int result = t % 6;
				switch (result) {
				case 0:
					if (yaowenLists != null && yaowenLists.size() > j)
						news_list.add(yaowenLists.get(j++));

				case 1:
					if (hunanLists != null && hunanLists.size() > n)
						news_list.add(hunanLists.get(n++));

				case 2:
					if (guoneiLists != null && guoneiLists.size() > k)
						news_list.add(guoneiLists.get(k++));
				case 3:
					if (hotLists != null && hotLists.size() > l)
						news_list.add(hotLists.get(l++));
				case 4:
					if (shehuiLists != null && shehuiLists.size() > m)
						news_list.add(shehuiLists.get(m++));
				case 5:
					if (zhoushiLists != null && zhoushiLists.size() > f)
						news_list.add(zhoushiLists.get(f++));
				default:
					break;
				}
			}
		}
	}

	@Override
	public void onReflash() {
		// TODO Auto-generated method stub\
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 获取最新数据
				setReflashData();
				// 通知界面显示
				showList(news_list);
				// 通知listview 刷新数据完毕；
				listview.reflashComplete();
			}
		}, 2000);

	}
}
