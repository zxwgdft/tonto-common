package com.tonto.common.util.ducument.interfaces;

import java.util.List;

/**
 * 属性
 * @author TontoZhou
 *
 */
public interface Attribution {
	
	/**
	 * ID 必须小于40个字符
	 * @return
	 */
	public String getId();
	public String getName();
	public String getType();	
	public String getIsMandatory();
	public String getDescription();
	public String getDefinition();

	public Boolean getIsObject();
	public Boolean getIsList();
	
	public List<Attribution> getAttributions();
	
}
