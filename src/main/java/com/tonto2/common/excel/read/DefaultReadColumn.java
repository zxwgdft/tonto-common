package com.tonto2.common.excel.read;

import java.util.regex.Pattern;

import com.tonto2.common.excel.base.ICell;

public class DefaultReadColumn extends ReadColumn{

	// 是否可为空
	boolean nullable = true;
	// 正则校验值
	Pattern pattern;
	// 如果为字符串的话，字符串最小长度，null不检查
	Integer minLength;
	// 如果为字符串的话，字符串最大长度，null不检查
	Integer maxLength;
	
	
	@Override
	public boolean validateValue(Object value) {
		
		
		return true;
	}


	@Override
	public Object convertValue(ICell cell) {
		
		
		return null;
	}


	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void fillValue(Object object, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	

}
