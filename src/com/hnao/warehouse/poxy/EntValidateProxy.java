package com.hnao.warehouse.poxy;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hnao.warehouse.base.BaseProxy;
import com.hnao.warehouse.beans.ComConst;
import com.hnao.warehouse.beans.DateDeserializer;
import com.hnao.warehouse.domain.BizResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.util.Log;

public class EntValidateProxy extends BaseProxy {

	private static EntValidateProxy _instance;

	public static EntValidateProxy getInstance(Context context) {
		if (_instance == null) {
			_instance = new EntValidateProxy();
		}
		_instance._context = context;
		return _instance;
	}
	
	public BizResult<String> GetOperatorDbConnection(String guid) {
		BizResult<String> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			Gson gson = gsonb.create();
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);
			http.configDefaultHttpCacheExpiry(0);// 及时清除缓存
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("guid", guid);		

			ResponseStream responseStream = http.sendSync(HttpMethod.GET,
					ComConst.SERVICE_URL + "EntValidate/GetOperatorDbConnection", params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<String>();
				Type type = new TypeToken<BizResult<String>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}
	
	public BizResult<String> GetOperatorDbConnectionByOperator(String userName, String pwd) {
		BizResult<String> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			Gson gson = gsonb.create();
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);
			http.configDefaultHttpCacheExpiry(0);// 及时清除缓存
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("userName", userName);
			params.addQueryStringParameter("pwd", pwd);

			ResponseStream responseStream = http.sendSync(HttpMethod.GET,
					ComConst.SERVICE_URL + "EntValidate/GetOperatorDbConnectionByOperator", params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<String>();
				Type type = new TypeToken<BizResult<String>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}
}
