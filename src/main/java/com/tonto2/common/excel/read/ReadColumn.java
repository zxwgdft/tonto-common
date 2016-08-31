package com.tonto2.common.excel.read;

import com.tonto2.common.base.property.PropertyConvert;
import com.tonto2.common.base.property.PropertyValidate;
import com.tonto2.common.excel.base.ICell;
import com.tonto2.common.excel.read.annotation.ReadPropertyConvert;
import com.tonto2.common.excel.read.exception.ExcelReadException;

/**
 * 
 * 读取列对象
 * 
 * @author TontoZhou
 *
 */
public abstract class ReadColumn {

	// ID
	String id;
	
	// 对应excel的列序号
	int cellIndex = -1;
		
	// 对应excel上的列名
	String name;
	
	// 是否数组
	boolean isArray;
	
	
	// 转化值方法
	ReadPropertyConvert<?> convert;
	// 校验值方法
	PropertyValidate validate;

	/**
	 * 获取值类型
	 * 
	 * @return
	 */
	public abstract Class<?> getType();
	
	/**
	 * <h2>转化值</h2>
	 * <p>优先考虑{@link PropertyConvert}实例的验证方法,该方法用于缺省情况下</p> 
	 * 
	 * 
	 * @param cell
	 * @throws ExcelReadException
	 */
	public abstract Object convertValue(ICell cell) throws ExcelReadException;
	
	/**
	 * <h2>填值</h2>
	 * 
	 * @param object
	 * @param value
	 */
	public abstract void fillValue(Object object, Object value) throws ExcelReadException;
	
	/**
	 * <h2>检验值</h2>
	 * <p>优先考虑{@link PropertyValidate}实例的验证方法,该方法用于缺省情况下</p>
	 * 
	 * @param object
	 * @return 验证成功返回NULL，否则返回错误信息
	 */
	public abstract String validateValue(Object value);
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public PropertyValidate getValidate() {
		return validate;
	}

	public void setValidate(PropertyValidate validate) {
		this.validate = validate;
	}

	public ReadPropertyConvert<?> getConvert() {
		return convert;
	}

	public void setConvert(ReadPropertyConvert<?> convert) {
		this.convert = convert;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	
	
	

}
