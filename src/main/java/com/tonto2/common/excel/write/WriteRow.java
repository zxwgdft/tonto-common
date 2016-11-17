package com.tonto2.common.excel.write;

import java.util.List;

import com.tonto2.common.utils.reflect.DataAccessor;


public abstract class WriteRow extends DataAccessor implements WriteComponent{
	
	List<WriteColumn> columns;
	
	List<WriteRow> subRows;	
	
	
	public List<WriteColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<WriteColumn> columns) {
		this.columns = columns;
	}

	public List<WriteRow> getSubRows() {
		return subRows;
	}

	public void setSubRows(List<WriteRow> subRows) {
		this.subRows = subRows;
	}
	
}
