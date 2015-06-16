/**   
 * �ļ�����JsonParse.java
 * �������ڣ�2014-5-19   
 * Copyright (c) 2014 by Peter.��Ȩ����.
 */

package com.example.popuandviewpagedemo.JsonPull;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

import com.example.popuandviewpagedemo.bean.NewsBean;
import com.example.popuandviewpagedemo.bean.WeatherBean;

/**
 * ��Ŀ���ƣ�JsonBookDemo<br>
 * �����ƣ�JsonParse <br>
 * �������� TODO(�������������) <br>
 * �����ˣ�Administrator ����ʱ�䣺2014-5-19 ����2:58:35 <br>
 * �޸��ˣ� �޸�ʱ�䣺 <br>
 * �޸ı�ע��
 * 
 * @version V1.0
 */

public class JsonParse {
	public List<NewsBean> parser(String json) {
		List<NewsBean> newsBeans = new ArrayList<NewsBean>();
		try {
			JSONArray jay = new JSONArray(json);
			for (int i = 0; i < jay.length(); i++) {
				JSONObject temp = (JSONObject) jay.get(i);
				NewsBean news = new NewsBean();
				news.setId(temp.getString("id"));
				news.setDescription(temp.getString("description"));
				news.setInputtime(temp.getString("inputtime"));
				news.setThumb(temp.getString("thumb").replace("/thumb_120_80_",
						"/"));

				news.setTitle(temp.getString("title"));
				newsBeans.add(news);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return newsBeans;
	}

	public WeatherBean parseWeahter(String json) {
		WeatherBean bean = null;
		try {
			JSONObject object = new JSONObject(json);
			if (!object.isNull("errNum")) {

				int ret = object.getInt("errNum");
				if (ret == 0 && !object.isNull("retData")) {

					JSONObject jsonObject = new JSONObject(
							object.getString("retData"));
					if (jsonObject != null) {
						bean = new WeatherBean(jsonObject.toString());

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;

	}

}
