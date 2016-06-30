package com.hnao.warehouse.poxy;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hnao.warehouse.R;
import com.hnao.warehouse.base.BaseProxy;
import com.hnao.warehouse.beans.ComConst;
import com.hnao.warehouse.beans.DateDeserializer;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.SheetTypeEnum;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SerialNumberProxy extends BaseProxy {

	private static SerialNumberProxy _instance;
	
	private static boolean configCheckCompany;

	public static SerialNumberProxy getInstance(Context context) {
		if (_instance == null) {
			_instance = new SerialNumberProxy();
			configCheckCompany = context.getResources().getBoolean(R.bool.bool_check_company);
		}
		_instance._context = context;
		return _instance;
	}

	public BizResult<String> GetNextSerialNumber(SheetTypeEnum sheetType) {
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
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}
			params.addQueryStringParameter("sheetType", sheetType.toString());

			ResponseStream responseStream = http.sendSync(HttpMethod.GET,
					ComConst.SERVICE_URL + "SerialNumber/GetNextSerialNumber", params);

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

	public BizResult<Date> GetSystemDate() {
		BizResult<Date> re = null;
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
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}

			ResponseStream responseStream = http.sendSync(HttpMethod.GET,
					ComConst.SERVICE_URL + "SerialNumber/GetSystemDate", params);

			String strResponse = responseStream.readString();

			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<Date>();
				Type type = new TypeToken<BizResult<Date>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

}
