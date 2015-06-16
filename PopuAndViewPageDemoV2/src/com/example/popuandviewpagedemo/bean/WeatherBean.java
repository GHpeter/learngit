package com.example.popuandviewpagedemo.bean;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WeatherBean implements Serializable {

	// private String city;
	// private String citycode;
	// private String date;
	// private String time;
	// private String postCode;
	// private String longitude;
	// private String latitude;
	private String weather;
	private String temp;

	// private String l_tmp;
	// private String h_tmp;
	// private String WD;
	// private String WS;

	public WeatherBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeatherBean(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		jsonToBean(jsonObject);

	}

	private void jsonToBean(JSONObject jsonObject) throws JSONException {

		if (!jsonObject.isNull("temp")) {
			temp = jsonObject.getString("temp");
		}
		if (!jsonObject.isNull("weather")) {
			weather = jsonObject.getString("weather");
		}

	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public WeatherBean(String weather, String temp) {
		super();
		this.weather = weather;
		this.temp = temp;
	}

	@Override
	public String toString() {
		return "WeatherBean [weather=" + weather + ", temp=" + temp + "]";
	}

}
