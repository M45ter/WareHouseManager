package com.hnao.warehouse.domain;

public class ApplyItem extends ProductExtCol {
	public String Guid;
	public String ApplyGuid;
	public String ProductGuid;// 产品Guid
	// [Range(1,999,ErrorMessage="数量请控制在1~999")]
	public int Quantity;// 产品数量
	public String Memo;// 产品备注

	public String ProductName;
	
	public String ProductIdNo;
	
	public String ProductUnit;
}
