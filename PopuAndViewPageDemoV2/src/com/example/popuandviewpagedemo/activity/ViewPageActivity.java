package com.example.popuandviewpagedemo.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.youmi.android.spot.SpotManager;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.popuandviewpagedemo.R;
import com.example.popuandviewpagedemo.Contacts.URL;
import com.example.popuandviewpagedemo.JsonPull.JsonParse;
import com.example.popuandviewpagedemo.adapter.MyPagerAdapter;
import com.example.popuandviewpagedemo.base.BaseActivity;
import com.example.popuandviewpagedemo.bean.NewsBean;
import com.example.popuandviewpagedemo.bean.WeatherBean;
import com.example.popuandviewpagedemo.utils.DateString;
import com.example.popuandviewpagedemo.utils.HttpUtl;
import com.example.popuandviewpagedemo.utils.Lunar;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * @ClassName: PopuActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Peter
 * @date
 * 
 */
public class ViewPageActivity extends BaseActivity implements OnClickListener,
		Runnable {

	private Context context = null;
	private LocalActivityManager manager = null;
	private ViewPager mPager;// 页卡内容
	private List<View> listActivity; // Tab页面列表
	private ImageView cursor, more, head;// 动画图片
	private TextView tv_hot, tv_guoji, tv_joke, tv_fun, tv_finance, tv_city;// 页卡头标
	private TextView tv_lunarDay, tv_lunar, tv_weather;
	private Handler handler;
	private Lunar lunar;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW, moreW;// 动画图片宽度
	private PopupWindow popupWindow;
	private DrawerLayout mDrawerLayout;
	private ListView left_drawer;

	private static final String strs[] = { "我的收藏", "商报电子版", "活动", "反馈", "设置" };

	// ----------------定位信息------------------
	private LocationClient client = null;
	private BDLocationListener listener = new MyLocationListener();
	private String city;
	private String cityForWeather;
	private long exitTime;
	private String jsonString;
	private HttpUtl httpUtl;
	JsonParse json;

	WeatherBean weather;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_shownews_info);
		context = ViewPageActivity.this;
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);// 恢复保存的状态

		InitImageView();// 先初始化
		InitAllViews();
		InitViewPager();
		ShowPopuWindow();
		showTime();
		location();
		showYouMengUpdateService();

	}

	private Handler handler1 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				setDateForWeather();
				break;
			default:
				break;
			}

		}

	};

	private void setDateForWeather() {
		json = new JsonParse();
		weather = json.parseWeahter(jsonString);
		if (weather != null) {
			tv_weather.setText(weather.getTemp() + "℃    "
					+ weather.getWeather());
		}
	}

	private void showYouMengUpdateService() {
		UmengUpdateAgent.update(this);

		UmengUpdateAgent.setUpdateOnlyWifi(false); //
		// 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(updateListener);
		UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

			@Override
			public void OnDownloadStart() {
				Toast.makeText(ViewPageActivity.this, "开始下载",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void OnDownloadUpdate(int progress) {

			}

			@Override
			public void OnDownloadEnd(int result, String file) {

			}
		});
		UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

			@Override
			public void onClick(int status) {
				switch (status) {
				case UpdateStatus.Update:

					break;
				case UpdateStatus.Ignore:

					break;
				case UpdateStatus.NotNow:

					break;
				}
			}
		});
		UmengUpdateAgent.forceUpdate(ViewPageActivity.this);

	}

	UmengUpdateListener updateListener = new UmengUpdateListener() {
		@Override
		public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {

			switch (updateStatus) {
			case 0: // has update
				UmengUpdateAgent.showUpdateDialog(ViewPageActivity.this,
						updateInfo);

				break;
			case 1: // has no update
				Toast.makeText(ViewPageActivity.this, "当前已是最新版本",
						Toast.LENGTH_SHORT).show();
				break;

			case 2: // none wifi
				Toast.makeText(ViewPageActivity.this, "没有wifi连接， 只在wifi下更新",
						Toast.LENGTH_SHORT).show();
				break;
			case 3: // time out
				Toast.makeText(ViewPageActivity.this, "超时", Toast.LENGTH_SHORT)
						.show();
				break;
			case 4: // is updating

				break;
			}

		}
	};

	public void showTime() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				tv_lunarDay.setText((String) msg.obj);
			}
		};
		new Thread(this).start();
	}

	/**
	 * 初始化控件
	 */
	private void InitAllViews() {
		tv_hot = (TextView) findViewById(R.id.tv_hot);
		tv_guoji = (TextView) findViewById(R.id.tv_international);
		tv_joke = (TextView) findViewById(R.id.tv_joke);
		tv_fun = (TextView) findViewById(R.id.tv_fun);
		tv_finance = (TextView) findViewById(R.id.tv_finance);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_weather = (TextView) findViewById(R.id.tv_weather);

		tv_hot.setOnClickListener(new MyOnClickListener(0));
		tv_guoji.setOnClickListener(new MyOnClickListener(1));
		tv_joke.setOnClickListener(new MyOnClickListener(2));
		tv_fun.setOnClickListener(new MyOnClickListener(3));
		tv_finance.setOnClickListener(new MyOnClickListener(4));
		tv_lunarDay = (TextView) findViewById(R.id.tv_lunarDay);

		lunar = new Lunar(Calendar.getInstance());
		tv_lunar = (TextView) findViewById(R.id.tv_lunar);
		tv_lunar.setText("\t" + lunar.toChangeString()
				+ DateString.StringData());
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		left_drawer = (ListView) findViewById(R.id.left_drawer);
		left_drawer.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strs));
		left_drawer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Toast.makeText(ViewPageActivity.this,
							"当前点击的是第" + position + "项内容", Toast.LENGTH_LONG)
							.show();
					break;
				case 1:
					Intent eIntent = new Intent(ViewPageActivity.this,
							EWebActivity.class);
					startActivity(eIntent);
					break;
				case 2:
					Toast.makeText(ViewPageActivity.this,
							"当前点击的是第" + position + "项内容", Toast.LENGTH_LONG)
							.show();
					break;
				case 3:
					Toast.makeText(ViewPageActivity.this,
							"当前点击的是第" + position + "项内容", Toast.LENGTH_LONG)
							.show();
					break;
				case 4:
					Toast.makeText(ViewPageActivity.this,
							"当前点击的是第" + position + "项内容", Toast.LENGTH_LONG)
							.show();
					break;
				default:
					break;
				}
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_LONG).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		mPager.setEnabled(false);
		mPager.setFocusable(false);
		// 加载activity
		listActivity = new ArrayList<View>();
		Intent hotIntent = new Intent(context, HotNewsActivity.class);
		Intent internationIntent = new Intent(context,
				InternationalNewsActivity.class);
		Intent intentDuanzi = new Intent(context, DuanziNewsActivity.class);
		Intent intentYule = new Intent(context, YuLeNewsActivity.class);
		Intent intentCaijing = new Intent(context, CaiJingNewsActivity.class);
		listActivity.add(getView("intentOne", hotIntent));
		listActivity.add(getView("intentTwo", internationIntent));
		listActivity.add(getView("intentThree", intentDuanzi));
		listActivity.add(getView("intentFour", intentYule));
		listActivity.add(getView("intentFive", intentCaijing));
		mPager.setAdapter(new MyPagerAdapter(listActivity));
		mPager.setCurrentItem(0);
		// 绑定监听
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void ShowPopuWindow() {
		View popuView = getLayoutInflater().inflate(R.layout.more_news_item,
				null);
		popupWindow = new PopupWindow(popuView, LayoutParams.MATCH_PARENT, 120,
				true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
				(Bitmap) null));
		Button fangchan = (Button) popuView.findViewById(R.id.btn_fangchan);
		fangchan.setOnClickListener(this);
		Button car = (Button) popuView.findViewById(R.id.btn_car);
		car.setOnClickListener(this);
		Button edc = (Button) popuView.findViewById(R.id.btn_jiaoyu);
		edc.setOnClickListener(this);
		Button wangshi = (Button) popuView.findViewById(R.id.btn_wangshi);
		wangshi.setOnClickListener(this);
		Button pe = (Button) popuView.findViewById(R.id.btn_pe);
		pe.setOnClickListener(this);
		Button wenhua = (Button) popuView.findViewById(R.id.btn_wenhua);
		wenhua.setOnClickListener(this);
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		more = (ImageView) findViewById(R.id.iv_more);
		moreW = BitmapFactory.decodeResource(getResources(), R.drawable.more)
				.getWidth();
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = ((screenW - moreW) / 5 - bmpW) / 2;// 计算偏移量
		// Matrix ，中文里叫矩阵，高等数学里有介绍，在图像处理方面，主要是用于平面的缩放、平移、旋转等操作。
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);//
		cursor.setImageMatrix(matrix);// 设置动画初始位置
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				popupWindow.showAsDropDown(v);
			}
		});

		head = (ImageView) findViewById(R.id.iv_head);
		head.setOnClickListener(this);

	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			// 这个比较简洁，只有一行代码。
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);

			Log.e("index", "index=" + currIndex);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			mPager.requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
			mPager.requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_CANCEL:
			mPager.requestDisallowInterceptTouchEvent(true);
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 体育
		case R.id.btn_pe:
			Intent peIntent = new Intent(ViewPageActivity.this,
					PENewsActivity.class);
			startActivity(peIntent);
			break;
		// 汽车
		case R.id.btn_car:
			Intent carIntent = new Intent(ViewPageActivity.this,
					CarNewsActivity.class);
			startActivity(carIntent);
			break;
		// 文化
		case R.id.btn_wenhua:
			Intent webhuaIntent = new Intent(ViewPageActivity.this,
					WenxueNewsActivity.class);
			startActivity(webhuaIntent);
			break;
		// 教育
		case R.id.btn_jiaoyu:
			Intent edcIntent = new Intent(ViewPageActivity.this,
					JiaoYuNewsActivity.class);
			startActivity(edcIntent);
			break;
		// 房产
		case R.id.btn_fangchan:
			Intent fangchanIntent = new Intent(ViewPageActivity.this,
					FangChanNewsActivity.class);
			startActivity(fangchanIntent);
			break;
		// 网事

		case R.id.btn_wangshi:
			Intent wangshiIntent = new Intent(ViewPageActivity.this,
					WangShiNewsActivity.class);
			startActivity(wangshiIntent);
			break;
		// ------------------------------------------------------
		case R.id.iv_head:
			mDrawerLayout.openDrawer(Gravity.LEFT);
			break;
		}

	}

	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(this).onStop();
		if (client.isStarted()) {
			client.stop();
		}
		client.unRegisterLocationListener(listener);

		UmengUpdateAgent.setUpdateOnlyWifi(true);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.setUpdateListener(null);
		UmengUpdateAgent.setDownloadListener(null);
		UmengUpdateAgent.setDialogListener(null);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		try {
			while (true) {
				SimpleDateFormat sdf = new SimpleDateFormat("M月dd日 HH:mm:ss");
				String str = sdf.format(new Date());
				handler.sendMessage(handler.obtainMessage(100, str));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 开启定位
	 */
	public void location() {
		client = new LocationClient(getApplicationContext());
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		client.setLocOption(option);
		client.registerLocationListener(listener);
		client.start();
	}

	// 监听
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append(" 时间 : ");
			sb.append(location.getTime());
			sb.append("\n错误代码 : ");
			sb.append(location.getLocType());

			System.out.println("城市code：" + location.getCityCode());
			sb.append("\n纬度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			sb.append("\n半径 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\n速率 : ");
				sb.append(location.getSpeed());
				sb.append("\n卫星 : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\na地址 : ");
				sb.append(location.getAddrStr());
			}
			city = location.getCity();

			Log.i("MapLocation", sb.toString());
			tv_city.setText(city);
			System.out.println(city);
			cityForWeather = city.substring(0, city.length() - 1);

			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String url = URL.WeatherApi + cityForWeather;
					System.out.println("======>" + url);
					httpUtl = new HttpUtl();
					if (httpUtl.hasInternet(ViewPageActivity.this)) {
						jsonString = httpUtl.getJson(url);
						json = new JsonParse();
						handler1.sendEmptyMessage(1);
					} else {
						Toast.makeText(getApplication(), "当前没有网络，请稍后再试",
								Toast.LENGTH_LONG).show();
					}
				}
			}).start();

			System.out.println("--------" + cityForWeather);
			if (client != null) {
				client.stop();
			}
		}

	}

}