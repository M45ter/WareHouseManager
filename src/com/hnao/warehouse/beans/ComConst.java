package com.hnao.warehouse.beans;

public class ComConst {
	// public static final String APK_FILE_NAME = "SecurtyPlatApp.apk";
	/**
	 * 服务器api域名
	 */
	// public static final String SERVICE_PROTOCAL_DOMAIN =
	// "http://www.chnai.com/";
	//通用外网
	public static final String SERVICE_PROTOCAL_DOMAIN = "http://123.157.241.146:8099/";
	//中博内网
//	public static final String SERVICE_PROTOCAL_DOMAIN = "http://192.168.18.213:8099/";
	
	public static final String SERVICE_URL = SERVICE_PROTOCAL_DOMAIN + "api/";
	public static final String OTHER_SERVER_URL = SERVICE_PROTOCAL_DOMAIN + "Others.aspx?Key=";
	public static final int CONNECT_TIME_OUT = 5000;
	public static final int READ_TIME_OUT = 15000;

	public static final String INTENT_EXTRA_SYSUSER = "sysUser";

	// 在这个平台下行与行之间的分隔符 相当于“\n”
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	// 广播关闭系统标识
	public static final String ACTION_SHUTDOWN = "SHUTDOWN";
	public static final String ACTION_M50BARCODE = "com.ge.action.barscan";
	// --- 書式 ---
	public static final String DATE_SEPARATOR = "/"; // 日付区切り文字
	public static final String DATE_FORMAT = "yyyy/MM/dd"; // 日付書式
	public static final String DATE_FORMAT_NOYEAR = "MM/dd"; // 日付書式
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_TIME_NOSECOND_FORMAT = "yyyy/MM/dd HH:mm";
	public static final String TIME_SEPARATOR = ":"; // 時刻区切り文字
	public static final String TIME_FORMAT = "HH:mm"; // 時刻書式

	// --- 格式 ---
	public static final String DATE_SEPARATOR_Bars = "-"; // 日付区切り文字
	public static final String DATE_FORMAT_Bars = "yyyy-MM-dd"; // 日付書式

	/**
	 * 刷新相关
	 */
	public static final int WHAT_DID_LOAD_DATA = 1; // Handler what 数据加载完毕
	public static final int WHAT_ON_REFRESH = 2; // Handler what 刷新中
	public static final int WHAT_DID_REFRESH = 3; // Handler what 已经刷新完
	public static final int WHAT_SET_HEADER_HEIGHT = 4;// Handler what 设置高度
	public static final int WHAT_DID_MORE = 5; // Handler what 已经获取完更多

	public static final int REQUEST_SCANNING = 1;
	public static final int REQUEST_LOGIN = 2;
	public static final int RESULT_SCANNING = 1;
	public static final int RESULT_LOGIN = 2;
	public static final int RESULT_REG = 3;
	public static final String RESULT_BACK = "ResultBack";

	// 其他相关
	public static Boolean DO_NOT_SHOW = false;
	public static final String SERIVCE_TEL = "tel:01088938200";

	public static final int RECONNECT = 1;

	public static final int UPDATE_FINISH = 5;

	/* 下载中 */
	public static final int DOWNLOAD = 1;
	/* 下载结束 */
	public static final int DOWNLOAD_FINISH = 2;
	/* 下载出错 */
	public static final int DOWNLOAD_ERROR = 3;

	public static final String SHAREP_KEY = "HN_SP";
	public static final String USERNAME_KEY = "HN_User_Name";
	public static final String PASSWORD_KEY = "HN_Password";
	public static final String SHAREP = "HN";
	public static final String GUIDE = "Guide";

	public static final String SP_LOGIN = "Login";

	public static final String SN_CHECK = "sn_check";

	public static final String SN = "sn";

	public static final String HEADER_SN = "EntSN";

	public static final int POLL_MESSAGE_TIME = 1 * 60 * 1000;
}
