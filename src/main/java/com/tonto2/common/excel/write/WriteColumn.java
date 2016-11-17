package com.tonto2.common.excel.write;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.tonto2.common.excel.write.annotation.ValueFormator;
import com.tonto2.common.utils.reflect.DataAccessor;

/**
 * 
 * 用于写Excel的列
 * 
 * @author TontZhou
 * 
 */
public abstract class WriteColumn extends DataAccessor implements WriteComponent{

	// ID
	String id;

	// 对应excel的列序号
	int cellIndex = -1;

	// 对应excel上的列名
	String name;

	// 默认空值显示
	String defaultEmptyValue = "";

	// 列宽
	int width = -1;

	// 自动适应宽度
	boolean autoWidth = false;

	// 自动换行
	boolean wrapText = true;

	// excel自带的格式化字符串
	String format = "";

	// 对齐方式,见{@link org.apache.poi.ss.usermodel.CellStyle}内参数
	int alignment = -1;
	
	// 值格式化接口，可以为NULL
	ValueFormator valueFormator;

	/**
	 * 
	 * <h2>获取填入的值</h2>
	 * <p>
	 * 优先考虑{@link com.tonto2.common.excel.write.annotation.ValueFormator}
	 * 实例的转化方法,该方法用于缺省情况下
	 * </p>
	 * 
	 * @param data
	 *            具体数据
	 * @return
	 */
	public abstract void setCellValue(Cell cell, Object data);

	/**
	 * 创建样式
	 * 
	 * @param workbook
	 *            工作簿
	 * @param commonCellStyle
	 *            基础样式，可以为NULL
	 * @return
	 */
	public abstract CellStyle getCellStyle(Workbook workbook, CellStyle commonCellStyle);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/** 列序号，起始0 */
	public int getCellIndex() {
		return cellIndex;
	}

	/** 列序号，起始0 */
	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	/** 列名 */
	public String getName() {
		return name;
	}

	/** 列名 */
	public void setName(String name) {
		this.name = name;
	}

	/** 默认值，当值为null的时候使用 */
	public String getDefaultEmptyValue() {
		return defaultEmptyValue;
	}

	/** 默认值，当值为null的时候使用 */
	public void setDefaultEmptyValue(String defaultEmptyValue) {
		this.defaultEmptyValue = defaultEmptyValue;
	}

	/** 列宽度,1代表一个英文字符 */
	public int getWidth() {
		return width;
	}

	/** 列宽度,1代表一个英文字符 */
	public void setWidth(int width) {
		this.width = width;
	}

	/** 是否自动适应宽度，优先级高于宽度 */
	public boolean isAutoWidth() {
		return autoWidth;
	}

	/** 是否自动适应宽度，优先级高于宽度 */
	public void setAutoWidth(boolean autoWidth) {
		this.autoWidth = autoWidth;
	}

	/** 是否自动换行 */
	public boolean isWrapText() {
		return wrapText;
	}

	/** 是否自动换行 */
	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
	}

	/** 格式化 */
	public String getFormat() {
		return format;
	}

	/** 格式化 */
	public void setFormat(String format) {
		this.format = format;
	}

	/** 对齐方式,见{@link org.apache.poi.ss.usermodel.CellStyle}内参数 */
	public int getAlignment() {
		return alignment;
	}

	/** 对齐方式,见{@link org.apache.poi.ss.usermodel.CellStyle}内参数 */
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	/** 值格式化接口，可以为NULL */
	public ValueFormator getValueFormator() {
		return valueFormator;
	}

	/** 值格式化接口，可以为NULL */
	public void setValueFormator(ValueFormator valueFormator) {
		this.valueFormator = valueFormator;
	}
	
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("\nID：").append(id).append("\n列名：").append(name).append("\n列序号：").append(cellIndex);
		return sb.toString();
	}

}
