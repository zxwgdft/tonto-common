package com.tonto2.common.excel.base;

public interface IRow {
	public ICell getCell(int cellIndex);
	public int getLastCellNum();
}
