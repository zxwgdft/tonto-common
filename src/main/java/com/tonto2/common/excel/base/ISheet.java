package com.tonto2.common.excel.base;

public interface ISheet {
	public IRow getRow(int rowIndex);
	public int getLastRowNum();
}
