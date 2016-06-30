package com.hnao.warehouse.domain;

import android.content.Context;

public class User extends Operator {

	private static User instance;

	private Boolean IsLogin = false;

	private Context _context;

	public static User getInstance(Context context) {
		if (null == instance)
			instance = new User();
		instance._context = context;
		return instance;
	}

	public void login(Operator operator) {
		this.Guid = operator.Guid;
		this.UserName = operator.UserName;
		this.RealName = operator.RealName;
		this.Memo = operator.Memo;
		operatorRole = operator.operatorRole;
		IsLogin = true;
	}

	public void logout() {
		this.Guid = null;
		this.UserName = null;
		this.RealName = null;
		this.Memo = null;
		operatorRole = null;
		IsLogin = false;
	}

	public Boolean getIsLogin() {
		return this.IsLogin;
	}

	@Override
	public String toString() {
		return "User [Guid=" + Guid + ", UserName=" + UserName + ", RealName=" + RealName + ", Memo=" + Memo + "]";
	}



}
