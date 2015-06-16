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

public class WenxueNewsActivity extends BaseActivity implements
		IReflashListener {
	List<NewsBean> news_list = new ArrayList<NewsBean>();
	List<NewsBean> wenxueLists = new ArrayList<NewsBean>();
	List<NewsBean> wenhuaLists = new ArrayList<NewsBean>();
	NewsAdapter adapter;
	ReFlashListView listview;
	private String jsonWenXue;
	private String jsonWenHua;
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
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				httpUtl = new HttpUtl();
				if (httpUtl.hasInternet(WenxueNewsActivity.this)) {
					jsonWenXue = httpUtl.getJson(URL.WENXUENewsIp);
					jsonWenHua = httpUtl.getJson(URL.WenHuaNewsIp);
					if (!TextUtils.isEmpty(jsonWenXue)
							&& !TextUtils.isEmpty(jsonWenHua)) {
						handler.sendEmptyMessage(1);
					}
				} else {
					Toast.makeText(getApplication(), "��ǰû�����磬���Ժ�����",
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
				Intent inten = new Intent(WenxueNewsActivity.this,
						WebActivity.class);
				inten.putExtra("flag", news_list.get(pos).getId());
				startActivity(inten);
			}
		});
	}

	CustomProgressDialog dialog;

	public void showmeidialog(View v) {
		dialog = new CustomProgressDialog(this, "���ڼ�����", R.anim.frame);
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

		wenxueLists = new JsonParse().parser(jsonWenXue);
		wenhuaLists = new JsonParse().parser(jsonWenHua);

		List<NewsBean> beans = new ArrayList<NewsBean>();
		beans.addAll(wenxueLists);
		beans.addAll(wenhuaLists);
		int j = 0;
		int n = 0;
		for (int i = 1; i < beans.size(); i++) {
			if (i % 2 == 0) {
				if (wenxueLists.size() > j) {
					news_list.add(wenxueLists.get(j++));
				} else if (wenhuaLists.size() > n) {
					news_list.add(wenhuaLists.get(n++));
				}
			} else {
				if (wenhuaLists.size() > n) {
					news_list.add(wenhuaLists.get(n++));
				} else if (wenxueLists.size() > j) {
					news_list.add(wenxueLists.get(j++));
				}

			}
		}
		showList(news_list);

	}


	private void setReflashData() {
		handler.sendEmptyMessage(1);
		for (int i = 0; i < 1; i++) {

			wenxueLists = new JsonParse().parser(jsonWenXue);
			wenhuaLists = new JsonParse().parser(jsonWenHua);
			List<NewsBean> beans = new ArrayList<NewsBean>();
			beans.addAll(wenxueLists);
			beans.addAll(wenhuaLists);
			int j = 0;
			int n = 0;
			for (int k = 1; k < beans.size(); k++) {
				if (i % 2 == 0) {
					if (wenxueLists.size() > j) {
						news_list.add(wenxueLists.get(j++));
					} else if (wenhuaLists.size() > n) {
						news_list.add(wenhuaLists.get(n++));
					}
				} else {
					if (wenhuaLists.size() > n) {
						news_list.add(wenhuaLists.get(n++));
					} else if (wenxueLists.size() > j) {
						news_list.add(wenxueLists.get(j++));
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
				// ��ȡ��������
				setReflashData();
				// ֪ͨ������ʾ
				showList(news_list);
				// ֪ͨlistview ˢ��������ϣ�
				listview.reflashComplete();
			}
		}, 2000);

	}
}