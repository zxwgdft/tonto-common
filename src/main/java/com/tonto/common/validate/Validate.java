package com.tonto.common.validate;

/**
 * 验证规则接口
 * @author TontoZhou
 *
 */
public interface Validate {
	/**
	 * 
	 * @param value		验证的值
	 * @param currentObj	当前验证的类
	 * @param validateObj		验证的完整类
	 * @return 返回错误信息，如果没有返回null
	 */
	public String validate(Object value,Object currentObj,Object validateObj);
}
