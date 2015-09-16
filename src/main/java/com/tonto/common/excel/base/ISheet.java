package com.tonto.common.excel.base;

public interface ISheet {
	public IRow getRow(int rowIndex);
	public int getLastRowNum();
}
