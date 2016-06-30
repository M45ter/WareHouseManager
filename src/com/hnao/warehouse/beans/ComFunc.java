package com.hnao.warehouse.beans;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class ComFunc
{
	public static Drawable LoadImageFromWebOperations(String url)
    {
         try
         {
             InputStream is = (InputStream) new URL(url).getContent();
             Drawable d = Drawable.createFromStream(is, "src name");
             return d;
         }catch (Exception e) {
             System.out.println("Exc="+e);
             return null;
         }
     }
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	 */  
	public static int dip2px(Context context, float dpValue) {  
	    final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int) (dpValue * scale + 0.5f);  
	}  
	
	/** 
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	 */  
	public static int px2dip(Context context, float pxValue) {  
	    final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int) (pxValue / scale + 0.5f);  
	}  
	
	public static int byteToInt2(byte[] b)
	{
		return (((int) b[0]) << 24) + (((int) b[1]) << 16) + (((int) b[2]) << 8) + (int) b[3];
	}

	public static int getlenthofresult(byte[] deout)
	{
		int re = 0;
		if (deout.length <= 4)
		{
			return re;
		}
		else
		{
			byte[] lenth = new byte[4];
			System.arraycopy(deout, 0, lenth, 0, 4);
			re = byteToInt2(lenth);

			re = (((int) lenth[2]) << 8) + (int) ((byte) lenth[3] & 0xff);
		}

		return re;
	}

	public static String getdeOutStr(byte[] deout, int lenth)
	{
		String re = "";
		if(deout.length >= 4)
		{
			byte[] temp1 = new byte[deout.length - 4];
			byte[] temp2 = new byte[deout.length - 4];
			byte[] temp3 = new byte[lenth];
			byte[] temp4 = new byte[lenth];
			System.arraycopy(deout, 4, temp1, 0, deout.length - 4);
	
			if ((temp1.length % 4) == 0)
			{
	
				for (int i = 0; i < (temp1.length / 4); i++)
				{
					temp2[i * 4] = temp1[i * 4 + 3];
					temp2[i * 4 + 1] = temp1[i * 4 + 2];
					temp2[i * 4 + 2] = temp1[i * 4 + 1];
					temp2[i * 4 + 3] = temp1[i * 4];
				}
			}
			
			System.arraycopy(temp2, 0, temp3, 0, lenth);
	
			for (int i = 0; i < (temp3.length / 2); i++)
			{
				temp4[i * 2 + 0] = temp3[i * 2 + 1];
				temp4[i * 2 + 1] = temp3[i * 2 + 0];
			}
	
			try
			{
				re = new String(temp3, "utf-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return re;
	}

	public static String bytesToHexString(byte[] src)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0)
		{
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++)
		{
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
//			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 拼装时间格式
	 * 
	 * @param strDate
	 * @return
	 */
	public static String GetStringByDate(Date date, String formatString)
	{

		String DateString = "";
		if (date != null)
			DateString = new SimpleDateFormat(formatString, Locale.getDefault()).format(date);
		return DateString;
	}

	/**
	 * String→时间
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date GetDateByString(String dateString, String formatString)
	{

		Date d = new Date(1, 1, 1);// 默认值
		try
		{
			if (dateString != null && dateString.length() > 0)
				d = new SimpleDateFormat(formatString, Locale.getDefault()).parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 获取IP
	 * 
	 * @return
	 */
	public static String getLocalIpAddress()
	{
		try
		{
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getClass().equals(Inet4Address.class))
					{
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		}
		catch (SocketException ex)
		{
		}
		return null;
	}

	/**
	 * 在android手机上创建路径(必须一级一级创建)
	 * 
	 * @param path
	 */
	public static void createPath(String path)
	{
		File file = new File(path);
		if (!file.exists())
		{
			file.mkdir();
		}
	}

	/**
	 * SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean ExistSDCard()
	{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			return true;
		}
		else
			return false;
	}

	/**
	 * 获取外部存储根路径
	 * 
	 * @return
	 */
	public static File getStorageDirectory()
	{

		return Environment.getExternalStorageDirectory();
	}

	/**
	 * InputStream转String
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1)
		{
			baos.write(i);
		}
		return baos.toString();
	}

	public static String getExceptionMessage(Throwable e)
	{
		if (e == null)
		{
			return "Exception对象为空..";
		}
		String separator = System.getProperty("line.separator");

		StringBuilder str = new StringBuilder("错误消息：");
		str.append("\t");
		str.append(e.getMessage());
		str.append(separator);

		str.append("堆栈信息：");
		for (StackTraceElement element : e.getStackTrace())
		{
			str.append("\t");
			str.append(element.toString());
			str.append(separator);
		}

		return str.toString();
	}

	/**
	 * 将时间格式转换为Json格式 如:"\/Date(1368028800000)\/"
	 * 
	 * @param dtDate
	 * @return 如:"\/Date(1368028800000)\/"
	 */
	public static String DateToJsonFormat(Date dtDate)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("/Date(");
		// TODO:getTime默认是以GMT为准，所以这里合并上时区偏移量
		long lTmp = dtDate.getTime() - (dtDate.getTimezoneOffset() * 60000);
		sb.append(lTmp);
		sb.append(")/");
		return sb.toString();
	}

	/**
	 * 邮件地址验证
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 手机号码验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles)
	{
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

}
