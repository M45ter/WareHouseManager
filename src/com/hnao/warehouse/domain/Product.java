package com.hnao.warehouse.domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private String CategoryName;

	private String KindName;

	private String UnitName;

	private String StoreHouseGuid;

	private String StoreHouseQuantity;

	private String ProcessGroupTitle;

	private String CompanyID;

	private String Guid;

	private String Title;

	private String TitleLetter;
	
	private String TitleView;

	private String IdNo;

	private String QrCode;

	private String PicUrl;

	private String CategoryGuid;

	private String Price;

	private String Kind;

	private String Unit;

	private String Origin;

	private String Brand;

	private String Memo;

	private Date CreateTime;

	private Date UpdateTime;

//	public byte[] TimeStamp;

	private String ProcessGroupID;

	private String ProductAttr;

	private ProductImage ProductImg;

	public ProductImage getProductImg() {
		if (ProductImg == null)
			ProductImg = new ProductImage();
		return ProductImg;
	}

	public void setProductImg(ProductImage productImg) {
		this.ProductImg = productImg;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public String getKindName() {
		return KindName;
	}

	public void setKindName(String kindName) {
		KindName = kindName;
	}

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public String getStoreHouseGuid() {
		return StoreHouseGuid;
	}

	public void setStoreHouseGuid(String storeHouseGuid) {
		StoreHouseGuid = storeHouseGuid;
	}

	public String getStoreHouseQuantity() {
		return StoreHouseQuantity;
	}

	public void setStoreHouseQuantity(String storeHouseQuantity) {
		StoreHouseQuantity = storeHouseQuantity;
	}

	public String getProcessGroupTitle() {
		return ProcessGroupTitle;
	}

	public void setProcessGroupTitle(String processGroupTitle) {
		ProcessGroupTitle = processGroupTitle;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getGuid() {
		return Guid;
	}

	public void setGuid(String guid) {
		Guid = guid;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getTitleLetter() {
		return TitleLetter;
	}

	public void setTitleLetter(String titleLetter) {
		TitleLetter = titleLetter;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getCategoryGuid() {
		return CategoryGuid;
	}

	public void setCategoryGuid(String categoryGuid) {
		CategoryGuid = categoryGuid;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getKind() {
		return Kind;
	}

	public void setKind(String kind) {
		Kind = kind;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getOrigin() {
		return Origin;
	}

	public void setOrigin(String origin) {
		Origin = origin;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

	public String getProcessGroupID() {
		return ProcessGroupID;
	}

	public void setProcessGroupID(String processGroupID) {
		ProcessGroupID = processGroupID;
	}

	public String getTitleView() {
		return TitleView;
	}

	public void setTitleView(String titleView) {
		TitleView = titleView;
	}

	
}
