package com.hnao.warehouse.beans;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import android.util.Log;

public class DateDeserializer implements JsonDeserializer<Date> 
{
	@Override
	public Date deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException
	{
		String strTmp = json.getAsJsonPrimitive().getAsString();		
		if(strTmp.equals("null"))
		{
			return null;
		}
		else if(strTmp.contains("-")){
			if (strTmp.contains("T")){
				strTmp = strTmp.replace('T', ' ');
			}
			if (!strTmp.contains(".")){
				return ComFunc.GetDateByString(strTmp, "yyyy-MM-dd HH:mm:ss");
			}
//			Log.d("", "DateDeserializer deserialize strTmp = "+strTmp);
			return ComFunc.GetDateByString(strTmp, "yyyy-MM-dd HH:mm:ss.SSS");			
		}		
		else if(strTmp.contains(":"))
		{
			return ComFunc.GetDateByString(strTmp, ComConst.DATE_TIME_FORMAT);
		}
		else
		{
			return ComFunc.GetDateByString(strTmp, ComConst.DATE_FORMAT);
		}
	}
}
