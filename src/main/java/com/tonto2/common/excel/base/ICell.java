package com.tonto2.common.excel.base;

import java.util.Date;

import com.tonto2.common.base.property.ConvertException;

public interface ICell {
	public Date getDate() throws ConvertException;
	public String getString() throws ConvertException;
	public Integer getInteger() throws ConvertException;
	public Boolean getBoolean() throws ConvertException;
	public Double getDouble() throws ConvertException;
	public Long getLong() throws ConvertException;
}
