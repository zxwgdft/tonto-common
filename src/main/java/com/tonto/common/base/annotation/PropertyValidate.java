package com.tonto.common.base.annotation;

public interface PropertyValidate {
	/**
	 * 验证属性字段是否正确
	 * @param obj	解析excel一行数据所对应的实例对象
	 * @param value	excel数据对象的某一个属性字段的值，用于验证
	 * @return
	 */
	public boolean validate(Object obj,Object value);
}
