package com.tonto.common.base.annotation;

import com.tonto.common.excel.ValueFormatException;

public interface PropertyConvert<T> {
	public T convert(Object obj) throws ValueFormatException;
}
