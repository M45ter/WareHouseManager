package com.hnao.warehouse.domain;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Message {

	@SerializedName("Guid")
	public String guid;

	@SerializedName("OperatorGuid")
	public String operatorGuid;

	@SerializedName("OperatorName")
	public String operatorName;

	@SerializedName("RoleGuid")
	public String roleGuid;

	@SerializedName("ToOperator")
	public String toOperator;

	@SerializedName("CreateTime")
	public Date createTime;

	@SerializedName("UpdateTime")
	public Date updateTime;

	@SerializedName("IsRead")
	public boolean isRead;

	@SerializedName("Messages")
	public String messages;

	@SerializedName("FullClassName")
	public String fullClassName;
}
