package com.hnao.warehouse.domain.args;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class ApplyArgs extends PageArgs {

	/// <summary>
	/// 动作(1入库，2出库，3调拨)
	/// </summary>
	@SerializedName("Action")
	public int action;

	@SerializedName("SearchText")
	public String searchText;

	@SerializedName("TimeFrom")
	public Date timeFrom;

	@SerializedName("TimeTo")
	public Date timeTo;

}
