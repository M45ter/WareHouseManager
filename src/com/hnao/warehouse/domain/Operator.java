package com.hnao.warehouse.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Operator {
	public String Guid;// key
	public String UserName;
	public String RealName;// 显示
	public String Memo;

	@SerializedName("OperatorRole")
	public List<OperatorRole> operatorRole;
	
	@Override
	public String toString() {
		return "Operator [Guid=" + Guid + ", UserName=" + UserName + ", RealName=" + RealName + ", Memo=" + Memo + "]";
	}

}
