/**   
 * 文件名：ImageLoaderApplication.java
 * 创建日期：2015-4-9   
 * Copyright (c) 2015 by Peter.版权所有.
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
 * 项目名称：PopuAndViewPageDemo<br>
 * 类名称：ImageLoaderApplication <br>
 * 类描述： TODO(请输入类的描述) <br>
 * 创建人：Peter(李春福) <br>
 * 创建时间：2015-4-9 下午3:51:07 <br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * 
 * @version V1.0
 */

public class ImageLoaderApplication extends Application {

	private static ImageLoaderApplication instance;

	public static ImageLoaderApplication getInstance() {
		return instance;
	}
	// 得到MyApplication 对象
	public void onCreate() {
		super.onCreate();
		instance = this;
		initImageLoader(getApplicationContext());
	};
	// 初始化图片加载器
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
