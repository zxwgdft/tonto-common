package com.tonto.common.excel.base;

import java.util.Date;

import com.tonto.common.excel.ValueFormatException;

public interface ICell {
	public Date getDate() throws ValueFormatException;
	public String getString() throws ValueFormatException;
	public Integer getInteger() throws ValueFormatException;
	public Boolean getBoolean() throws ValueFormatException;
	public Double getDouble() throws ValueFormatException;
	public Long getLong() throws ValueFormatException;
}
