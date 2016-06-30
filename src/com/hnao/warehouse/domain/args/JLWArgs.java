package com.hnao.warehouse.domain.args;

import com.google.gson.annotations.SerializedName;

public class JLWArgs {

	@SerializedName("IdNo")
	public String idNo;

	@SerializedName("MouldStockIdNo")
	public String mouldStockIdNo;

	@SerializedName("MouldStockTitle")
	public String mouldStockTitle;

	@SerializedName("ExistWork")
	public boolean existWork;

	/// <summary>
	/// 操作类型（0：默认 1：确定更换模具 2：后台发回过来，提示是否更换模具 8:接口调用成功，但是有提示 9:绑定成功，提示）
	/// </summary>
	@SerializedName("Action")
	public int action;

	@SerializedName("Message")
	public String message;
}
