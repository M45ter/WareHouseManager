package com.hnao.warehouse.domain;

public enum SheetTypeEnum {

	RunningLine(10),//流水线
	Process(11),//工序
	Sales(12), //销售
	Purchase(13), //采购
	ProductionPlan(14), //生产计划
	StationPlan(16), //工位计划
	InStore(17), //入库
	OutStore(18), //出库
	Retail(19), //零售
	ProcessGroup(20), //工序套餐编号
	OperatorUserNo(999), //员工工号
	CompanyUnit(15),//单位信息 
	MouldAssess(21),//模具评估 
	SalesRefund(22), //销售退货
	PurchaseRefund(23), //采购退货
	RetailRefund(24);//零售退货

	private int value;

	private SheetTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}
}
