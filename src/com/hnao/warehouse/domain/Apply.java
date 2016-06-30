package com.hnao.warehouse.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Apply implements Serializable{
	public String Guid;
	public String IdNo;// 单据编号
	
	public String RelateIdNo;//关联编号
	public String Status;
	public String StatusName;
	
	public String Type;// 入库类型
	
	public String TypeName;
	/// <summary>
	/// 动作(1入库，2出库，3调拨)
	/// </summary>
	public int Action;
	public String HandlerGuid;// 经手人
	public String FromStoreGuid;
	public String ToStoreGuid;// 收货仓库
	public String FromStoreName;
	public String ToStoreName;
	public String Memo;// 备注
	public Date ApplyTime;// 单据日期	
//	public String ApplyTime;// 单据日期
	public Date CreateTime;
	public Date UpdateTime;
	// public byte[] TimeStamp;
	public String TimeStamp;
	
	public String LogisticsPrice;
	
	public String LogisticsCompany;
	
	public String LogisticsNo;
	

	public List<ApplyItem> ApplyItem = new ArrayList<ApplyItem>();

}
