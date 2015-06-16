/**   
 * �ļ�����ImageLoaderApplication.java
 * �������ڣ�2015-4-9   
 * Copyright (c) 2015 by Peter.��Ȩ����.
 */

package com.example.popuandviewpagedemo.base;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.widget.ImageView;

import com.example.popuandviewpagedemo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * ��Ŀ���ƣ�PopuAndViewPageDemo<br>
 * �����ƣ�ImageLoaderApplication <br>
 * �������� TODO(�������������) <br>
 * �����ˣ�Peter(���) <br>
 * ����ʱ�䣺2015-4-9 ����3:51:07 <br>
 * �޸��ˣ� <br>
 * �޸�ʱ�䣺 <br>
 * �޸ı�ע��
 * 
 * @version V1.0
 */

public class ImageLoaderApplication extends Application {

	private static ImageLoaderApplication instance;

	public static ImageLoaderApplication getInstance() {
		return instance;
	}
	// �õ�MyApplication ����
	public void onCreate() {
		super.onCreate();
		instance = this;
		initImageLoader(getApplicationContext());
	};
	// ��ʼ��ͼƬ������
	public void initImageLoader(Context context) {

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Config.RGB_565).showStubImage(R.drawable.shang)
				.showImageForEmptyUri(R.drawable.shang)
				.showImageOnFail(R.drawable.shang).build();

		ImageLoaderConfiguration configuration;
		configuration = new ImageLoaderConfiguration.Builder(context)
				.defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
		ImageLoader.getInstance().init(configuration);
	}

}
