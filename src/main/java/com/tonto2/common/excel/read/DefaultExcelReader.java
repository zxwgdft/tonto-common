package com.tonto2.common.excel.read;

import com.tonto2.common.excel.base.ISheet;

public class DefaultExcelReader<T> extends ExcelReader<T> {

	/**
	 * 创建目标类型的EXCEL读入器，会根据注解
	 * {@link com.tonto2.common.excel.read.annotation.ReadProperty}自动生成CELL对应列
	 * 
	 * @param target
	 */
	public DefaultExcelReader(Class<T> target, ISheet sheet) {
		this(target, sheet, 0);
	}

	public DefaultExcelReader(Class<T> target, ISheet sheet, int startRow) {
		super(target, sheet, startRow);
		this.columns = DefaultReadColumn.createReadColumn(target, null);
	}

}
