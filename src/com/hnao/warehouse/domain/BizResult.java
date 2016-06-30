package com.hnao.warehouse.domain;

public class BizResult<T>
{
	 /// <summary>
    /// 接口调用是否成功
    /// </summary>
    public Boolean Success ;
    /// <summary>
    /// 返回的错误信息
    /// </summary>
    public String Message ;
    /// <summary>
    /// 返回的数据（泛型）
    /// </summary>
    public T Data ;
}
