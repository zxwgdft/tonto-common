package com.tonto.common.excel.base;

public interface IRow {
	public ICell getCell(int cellIndex);
	public int getLastCellNum();
}
