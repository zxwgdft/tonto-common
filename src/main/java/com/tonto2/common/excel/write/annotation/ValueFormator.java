package com.tonto2.common.excel.write.annotation;

/**
 * 
 * 格式化接口
 * 
 * @author TontZhou
 *
 */
public interface ValueFormator {
	/**
	 * 格式化方法
	 * @param obj
	 * @return
	 */
	public String format(Object obj);
}
