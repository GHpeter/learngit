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

public class PENewsActivity extends BaseActivity implements IReflashListener {
	List<NewsBean> news_list = new ArrayList<NewsBean>();
	List<NewsBean> peLists = new ArrayList<NewsBean>();
	List<NewsBean> pesLists = new ArrayList<NewsBean>();

	NewsAdapter adapter;
	ReFlashListView listview;
	private String jsonPE;
	private String jsonPEs;
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
				httpUtl = new HttpUtl();
				if (httpUtl.hasInternet(PENewsActivity.this)) {
					jsonPE = httpUtl.getJson(URL.PENewsIp);
					jsonPEs = httpUtl.getJson(URL.PEsNewsIp);
					if (!TextUtils.isEmpty(jsonPE)
							&& !TextUtils.isEmpty(jsonPEs)) {
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
				Intent inten = new Intent(PENewsActivity.this,
						WebActivity.class);
				inten.putExtra("flag", news_list.get(pos).getId());
				// finish();
				startActivity(inten);
			}
		});
	}

	CustomProgressDialog dialog;

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
			setData();
		}

	};

	private void showList(List<NewsBean> news_list) {

		if (adapter != null && news_list.size() > 0)
			adapter.onDateChange(news_list);

	}

	private void setData() {
		pesLists = new JsonParse().parser(jsonPE);
		peLists = new JsonParse().parser(jsonPEs);

		List<NewsBean> beans = new ArrayList<NewsBean>();
		beans.addAll(peLists);
		beans.addAll(pesLists);
		int j = 0;
		int n = 0;
		for (int i = 1; i < beans.size(); i++) {
			if (i % 2 == 0) {
				if (peLists.size() > j) {
					news_list.add(peLists.get(j++));
				} else if (pesLists.size() > n) {
					news_list.add(pesLists.get(n++));
				}
			} else {
				if (pesLists.size() > n) {
					news_list.add(pesLists.get(n++));
				} else if (peLists.size() > j) {
					news_list.add(peLists.get(j++));
				}

			}
		}
		showList(news_list);

	}

	private void setReflashData() {
		handler.sendEmptyMessage(1);
		for (int i = 0; i < 1; i++) {

			pesLists = new JsonParse().parser(jsonPE);
			peLists = new JsonParse().parser(jsonPEs);
			List<NewsBean> beans = new ArrayList<NewsBean>();
			beans.addAll(peLists);
			beans.addAll(pesLists);
			int j = 0;
			int n = 0;
			for (int k = 1; k < beans.size(); k++) {
				if (i % 2 == 0) {
					if (peLists.size() > j) {
						news_list.add(peLists.get(j++));
					} else if (pesLists.size() > n) {
						news_list.add(pesLists.get(n++));
					}
				} else {
					if (pesLists.size() > n) {
						news_list.add(pesLists.get(n++));
					} else if (peLists.size() > j) {
						news_list.add(peLists.get(j++));
					}

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
