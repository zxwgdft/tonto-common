package com.tonto2.common.excel.read.annotation;

import com.tonto2.common.excel.base.ICell;
import com.tonto2.common.excel.read.exception.ExcelReadException;

public interface ReadPropertyConvert<T> {
	public T convert(ICell cell) throws ExcelReadException;
	
}
