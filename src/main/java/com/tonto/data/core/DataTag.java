package com.tonto.data.core;

/**
 * 数据标签
 * @author TontoZhou
 *
 */
public class DataTag {
	
	/**
	 * 关键字，唯一
	 */
	private String key;
	
	/**
	 * 标签名称
	 */
	private String name;
	
	/**
	 * 数据类型
	 */
	private int dataType;
	
	/**
	 * 数据寻路地址，用于在获取到数据后再次对他寻路找到需要的子属性，寻路规则见{@link com.tonto.data.core.util.DataGetter}
	 */
	private String dataPath;
	
	/**
	 * 数据源
	 */
	private DataSource dataSource;
	
	/**
	 * 数据渲染
	 */
	private DataViewRender dataViewRender;


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getDataType() {
		return dataType;
	}


	public void setDataType(int dataType) {
		this.dataType = dataType;
	}


	public DataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public DataViewRender getDataViewRender() {
		return dataViewRender;
	}


	public void setDataViewRender(DataViewRender dataViewRender) {
		this.dataViewRender = dataViewRender;
	}


	public String getDataPath() {
		return dataPath;
	}


	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
}
