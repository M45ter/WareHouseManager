package com.hnao.warehouse.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class BaseProxy
{
	protected Context _context;

	protected Boolean ValidateResult(String strResult) throws Exception
	{
		Boolean bReturn = false;
		if (null != strResult && 0 < strResult.length())
		{
			bReturn = true;
		}
		return bReturn;
	}
	
	protected List<String> ProcessResult(String strResult , String... strParams) throws JSONException
	{
		List<String> re = null;
		
		if(null != strParams && strParams.length>0)
		{
			JSONObject jobj = new JSONObject(strResult);
			
			re = new ArrayList<String>();
			
			for(String item : strParams)
			{
				re.add(jobj.getString(item));
			}
		}
		
		return re;
	}
	
	public Date GetServiceTime() throws Exception
	{
		Date result = new Date();
		
		return result;
	}
}
