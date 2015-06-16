/**   
 * �ļ�����DateString.java
 * �������ڣ�2015-3-24   
 * Copyright (c) 2015 by Peter.��Ȩ����.
 */

package com.example.popuandviewpagedemo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * ��Ŀ���ƣ�ListViewFrashDemo1<br>
 * �����ƣ�DateString <br>
 * �������� TODO(�������������) <br>
 * �����ˣ�Peter(���) <br>
 * ����ʱ�䣺2015-3-24 ����3:43:17 <br>
 * �޸��ˣ� <br>
 * �޸�ʱ�䣺 <br>
 * �޸ı�ע��
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
		mYear = String.valueOf(c.get(Calendar.YEAR)); // ��ȡ��ǰ���
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// ��ȡ��ǰ�·�
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// ��ȡ��ǰ�·ݵ����ں���
		mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

		if ("1".equals(mWay)) {
			mWay = "��";
		} else if ("2".equals(mWay)) {
			mWay = "һ";
		} else if ("3".equals(mWay)) {
			mWay = "��";
		} else if ("4".equals(mWay)) {
			mWay = "��";
		} else if ("5".equals(mWay)) {
			mWay = "��";
		} else if ("6".equals(mWay)) {
			mWay = "��";
		} else if ("7".equals(mWay)) {
			mWay = "��";
		}
		return " ��" + mWay;
	}

}
