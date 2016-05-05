package com.tonto.data.core;

/**
 * 数据视图渲染
 * @author TontoZhou
 *
 */
public interface DataViewRender {
	
	/**
	 * 数据显示渲染
	 * @param data
	 * @return
	 */
	public String render(Object data);
}
