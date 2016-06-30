package com.hnao.warehouse.domain;

public class AttrValue {
	public String Code;// key
	public String AttrDefCode;
	public String Text;// 显示
	public String Memo;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getAttrDefCode() {
		return AttrDefCode;
	}

	public void setAttrDefCode(String attrDefCode) {
		AttrDefCode = attrDefCode;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

}
