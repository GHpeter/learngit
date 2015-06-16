/**   
 * 文件名：DateString.java
 * 创建日期：2015-3-24   
 * Copyright (c) 2015 by Peter.版权所有.
 */

package com.example.popuandviewpagedemo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 项目名称：ListViewFrashDemo1<br>
 * 类名称：DateString <br>
 * 类描述： TODO(请输入类的描述) <br>
 * 创建人：Peter(李春福) <br>
 * 创建时间：2015-3-24 下午3:43:17 <br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * 
 * @version V1.0
 */

public class DateString {

	private static String mYear;
	private static String mMonth;
	private static String mDay;
	private static String mWay;

	public static String StringData() {
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return " 周" + mWay;
	}

}
