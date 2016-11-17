package com.tonto2.common.excel.write;

import org.apache.poi.ss.usermodel.Workbook;

import com.tonto2.common.excel.write.exception.ExcelWriteException;

public class DefaultExcelWriter<T> extends ExcelWriter<T>{

	public DefaultExcelWriter(Workbook workbook, Class<T> target) throws ExcelWriteException {
		super(workbook, null, 0, 1, null, null);;		
		setWriteRow(DefaultWriteRow.createWriteRow(target, null, null));
		
		
	}

}
