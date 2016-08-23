package com.tonto2.common.excel.read.exception;

import com.tonto2.common.excel.read.ReadColumn;

/**
 * 
 * Cell值异常
 * 
 * @author TontoZhou
 * 
 */
public class CellValueException extends ExcelReadException {

	private static final long serialVersionUID = 8689153131957489882L;

	private int row;
	private ReadColumn column;
	private String reason;

	/**
	 * 
	 * @param row  行号
	 * @param column	列
	 * @param reason	原因
	 */
	public CellValueException(int row, ReadColumn column, String reason) {
		super();
		this.row = row;
		this.column = column;
		this.reason = reason;

	}

	public String getMessage() {
		return "数据错误！[行数]：" + row + "/[名称]：" + column.getName() + "/[原因]：" + reason;
	}

	public int getRowIndex() {
		return row;
	}

	public int getCellIndex() {
		return column.getCellIndex();
	}

	public String getColumnName() {
		return column.getName();
	}

	public String getReason() {
		return reason;
	}

}
