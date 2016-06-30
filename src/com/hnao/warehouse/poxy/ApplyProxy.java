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
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.ApplyItem;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.PageReturns;
import com.hnao.warehouse.domain.args.ApplyArgs;
import com.hnao.warehouse.domain.args.OperatorArgs;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ApplyProxy extends BaseProxy {

	private static ApplyProxy _instance;

	private static boolean configCheckCompany;

	public static ApplyProxy getInstance(Context context) {
		if (_instance == null) {
			_instance = new ApplyProxy();
			configCheckCompany = context.getResources().getBoolean(R.bool.bool_check_company);
		}
		_instance._context = context;
		return _instance;
	}

	public BizResult<Apply> AddApply(Apply apply) {
		BizResult<Apply> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gsonb.create();
			String strPost = gson.toJson(apply);
			Log.d("", "strPost = " + strPost);
			RequestParams params = new RequestParams();
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}
			params.setContentType("application/json");
			params.setBodyEntity(new StringEntity(strPost, "UTF-8"));
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);

			ResponseStream responseStream = http.sendSync(HttpMethod.POST, ComConst.SERVICE_URL + "Apply/AddApply",
					params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<Apply>();
				Type type = new TypeToken<BizResult<Apply>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

	public BizResult<PageReturns<Apply>> GetApplyList(ApplyArgs args) {
		BizResult<PageReturns<Apply>> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gsonb.create();
			String strPost = gson.toJson(args);
			Log.d("", "strPost = " + strPost);
			RequestParams params = new RequestParams();
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}
			params.setContentType("application/json");
			params.setBodyEntity(new StringEntity(strPost, "UTF-8"));
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);

			ResponseStream responseStream = http.sendSync(HttpMethod.POST, ComConst.SERVICE_URL + "Apply/GetApplyList",
					params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<PageReturns<Apply>>();
				Type type = new TypeToken<BizResult<PageReturns<Apply>>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

	public BizResult<Apply> EditApplyStatus(Apply apply) {
		BizResult<Apply> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gsonb.create();
			String strPost = gson.toJson(apply);
			Log.d("", "strPost = " + strPost);
			RequestParams params = new RequestParams();
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}
			params.setContentType("application/json");
			params.setBodyEntity(new StringEntity(strPost, "UTF-8"));
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);

			ResponseStream responseStream = http.sendSync(HttpMethod.POST,
					ComConst.SERVICE_URL + "Apply/EditApplyStatus", params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<Apply>();
				Type type = new TypeToken<BizResult<Apply>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

	public BizResult<List<ApplyItem>> GetApplyItemList(String guid) {
		BizResult<List<ApplyItem>> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
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
			params.addQueryStringParameter("guid", guid);

			ResponseStream responseStream = http.sendSync(HttpMethod.GET,
					ComConst.SERVICE_URL + "Apply/GetApplyItemList", params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<List<ApplyItem>>();
				Type type = new TypeToken<BizResult<List<ApplyItem>>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

	public BizResult<Apply> EditApply(Apply apply) {
		BizResult<Apply> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gsonb.create();
			String strPost = gson.toJson(apply);
			Log.d("", "strPost = " + strPost);
			RequestParams params = new RequestParams();
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}
			params.setContentType("application/json");
			params.setBodyEntity(new StringEntity(strPost, "UTF-8"));
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);

			ResponseStream responseStream = http.sendSync(HttpMethod.POST, ComConst.SERVICE_URL + "Apply/EditApply",
					params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<Apply>();
				Type type = new TypeToken<BizResult<Apply>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

	public BizResult<Boolean> DeleteApply(Apply apply) {
		BizResult<Boolean> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gsonb.create();
			String strPost = gson.toJson(apply);
			Log.d("", "strPost = " + strPost);
			RequestParams params = new RequestParams();
			if (configCheckCompany) {
				SharedPreferences sp = _context.getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);
				String sn = sp.getString(ComConst.SN, "");
				Log.d("", "sn = " + sn);
				if (!sn.equals("")) {
					params.addHeader(ComConst.HEADER_SN, sn);
				}
			}
			params.setContentType("application/json");
			params.setBodyEntity(new StringEntity(strPost, "UTF-8"));
			HttpUtils http = new HttpUtils();
			http.configSoTimeout(ComConst.READ_TIME_OUT);
			http.configTimeout(ComConst.CONNECT_TIME_OUT);

			ResponseStream responseStream = http.sendSync(HttpMethod.POST, ComConst.SERVICE_URL + "Apply/DeleteApply",
					params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<Boolean>();
				Type type = new TypeToken<BizResult<Boolean>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}

}
