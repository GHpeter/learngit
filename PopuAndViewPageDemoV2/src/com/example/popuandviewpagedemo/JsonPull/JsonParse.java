/**   
 * 文件名：JsonParse.java
 * 创建日期：2014-5-19   
 * Copyright (c) 2014 by Peter.版权所有.
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
 * 项目名称：JsonBookDemo<br>
 * 类名称：JsonParse <br>
 * 类描述： TODO(请输入类的描述) <br>
 * 创建人：Administrator 创建时间：2014-5-19 下午2:58:35 <br>
 * 修改人： 修改时间： <br>
 * 修改备注：
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
