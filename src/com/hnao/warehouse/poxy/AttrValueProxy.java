package com.hnao.warehouse.poxy;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hnao.warehouse.R;
import com.hnao.warehouse.base.BaseProxy;
import com.hnao.warehouse.beans.ComConst;
import com.hnao.warehouse.beans.DateDeserializer;
import com.hnao.warehouse.domain.AttrValue;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.args.AttrDefArgs;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AttrValueProxy extends BaseProxy {

	private static AttrValueProxy _instance;
	
	private static boolean configCheckCompany;

	public static AttrValueProxy getInstance(Context context) {
		if (_instance == null) {
			_instance = new AttrValueProxy();
			configCheckCompany = context.getResources().getBoolean(R.bool.bool_check_company);
		}
		_instance._context = context;
		return _instance;
	}

	public BizResult<List<AttrValue>> GetAttrValueList(String attrDefCode) {
		BizResult<List<AttrValue>> re = null;
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
			params.addQueryStringParameter("attrDefCode", attrDefCode);

			ResponseStream responseStream = http.sendSync(HttpMethod.GET,
					ComConst.SERVICE_URL + "AttrValue/GetAttrValueList", params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<List<AttrValue>>();
				Type type = new TypeToken<BizResult<List<AttrValue>>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}
}
