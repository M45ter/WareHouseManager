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
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.Product;
import com.hnao.warehouse.domain.args.OperatorArgs;
import com.hnao.warehouse.domain.args.ProductArgs;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ProductProxy extends BaseProxy {

	private static ProductProxy _instance;
	
	private static boolean configCheckCompany;

	public static ProductProxy getInstance(Context context) {
		if (_instance == null) {
			_instance = new ProductProxy();
			configCheckCompany = context.getResources().getBoolean(R.bool.bool_check_company);
		}
		_instance._context = context;
		return _instance;
	}

	public BizResult<List<Product>> GetProductInfoList(ProductArgs productArgs) {
		BizResult<List<Product>> re = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			DateDeserializer dds = new DateDeserializer();
			gsonb.registerTypeAdapter(Date.class, dds);
			gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gsonb.create();
			String strPost = gson.toJson(productArgs);
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
					ComConst.SERVICE_URL + "Product/GetProductInfoList", params);
			String strResponse = responseStream.readString();
			Log.d("", "strResponse = " + strResponse);
			if (ValidateResult(strResponse)) {
				re = new BizResult<List<Product>>();
				Type type = new TypeToken<BizResult<List<Product>>>() {
				}.getType();
				re = gson.fromJson(strResponse, type);
			}
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return re;
	}
}
