package com.hnao.warehouse.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PageReturns<T> {

	@SerializedName("Total")
	public int total;

	@SerializedName("PageData")
	public List<T> pageData;
}
