package com.tonto.common.util.statistics;

/**
 * 统计规则
 * @author TontoZhou
 *
 */
public interface StatisticsRule {
	
	/**
	 * 判断是否符合统计规则
	 * @param fieldValue 属性值
	 * @param object	   对象
	 * @return
	 */
	public boolean compare(Object fieldValue, Object object);
	
}
