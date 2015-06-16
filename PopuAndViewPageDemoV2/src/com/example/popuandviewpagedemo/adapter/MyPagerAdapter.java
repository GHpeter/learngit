package com.example.popuandviewpagedemo.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager适配器 ViewPager不是直接与Views关联而是与一个key对象关联起来。
 * 这个key对象用来记录并且在这个适配器中唯一地标示某个页面。
 */
public class MyPagerAdapter extends PagerAdapter {
	public List<View> mListViews;

	public MyPagerAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
	}

	// 移除view对象
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(mListViews.get(arg1));
	}

	// 在这次更新结束之前
	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	// 创建view对象和将这个对象添加到父ViewGroup中
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(mListViews.get(arg1), 0);
		return mListViews.get(arg1);
	}

	// 方法可以用于判断某个view是否对应某个key对象
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	// 如果ViewPager的页面内容改变，
	// 那么系统就会调用这个ViewPager所对应的PagerAdapter的startUpdate()方法。
	@Override
	public void startUpdate(View arg0) {
	}
}
