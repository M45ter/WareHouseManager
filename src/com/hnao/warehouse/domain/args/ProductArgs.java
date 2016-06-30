package com.hnao.warehouse.domain.args;

import java.util.List;

public class ProductArgs extends PageArgs {

	public int SearchType;

	public List<String> CategoryGuids;

	public String SearchText;

	public String StoreHouseGuid;

	public String ProductGuid;

}
