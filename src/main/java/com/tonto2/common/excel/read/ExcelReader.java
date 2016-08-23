package com.tonto2.common.excel.read;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tonto2.common.base.property.PropertyValidate;

import com.tonto2.common.excel.base.ICell;
import com.tonto2.common.excel.base.IRow;
import com.tonto2.common.excel.base.ISheet;
import com.tonto2.common.excel.read.annotation.ReadPropertyConvert;
import com.tonto2.common.excel.read.exception.CellValueException;
import com.tonto2.common.excel.read.exception.ExcelReadException;

public abstract class ExcelReader<T> {

	private Map<String, ReadColumn> columnsMap;
	private List<ReadColumn> columns;

	/*
	 * 根据类型找到对应的{@link ReadPropertyConvert}和{@link PropertyValidate}方法
	 */
	private Map<Class<?>, ReadPropertyConvert<?>> classConvertCacheMap;
	private Map<Class<?>, PropertyValidate> classValidateCacheMap;

	/*
	 * 根据名称找到对应的{@link ReadPropertyConvert}和{@link PropertyValidate}方法
	 */
	private Map<String, ReadPropertyConvert<?>> nameConvertCacheMap;
	private Map<String, PropertyValidate> nameValidateCacheMap;

	// 工作簿
	private ISheet sheet;
	// 当前所在行号
	private int currentRowIndex;
	// 最大行号
	private int lastRowIndex;

	// 是否在读取一行记录的数据错误的情况下继续下一行
	private boolean continueIfDataError = false;

	// 需要转化的对象类
	private Class<T> clazz;

	private T createInstance() throws ExcelReadException {

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ExcelReadException("无法创建[" + clazz + "]转化的实例，确认是否有无参数的构造函数存在！", e);
		}
	}

	/***
	 * 
	 * @return
	 * @throws ExcelReadException
	 *             解析数据时未指明的其他错误
	 */
	public T readRow() throws ExcelReadException {
		int rowindex = currentRowIndex++;

		IRow row = sheet.getRow(rowindex);

		if (row == null)
			return null;

		T obj = createInstance();

		Map
		
		for (ReadColumn column : columns) {
			int cellIndex = column.getCellIndex();
			ICell cell = row.getCell(cellIndex);

			ReadPropertyConvert<?> convert = column.getConvert();
			if (convert == null)
				convert = nameConvertCacheMap.get(column.getId());
			if (convert == null)
				convert = classConvertCacheMap.get(column.getType());

			Object value = null;

			try {
				if (convert == null)
					value = column.convertValue(cell);
				else
					value = convert.convert(cell);

				PropertyValidate validate = column.getValidate();
				if(validate == null)
					validate = nameValidateCacheMap.get(column.getId());
				if(validate == null)
					validate = classValidateCacheMap.get(column.getType());
				
				if(validate == null)
				{
					boolean valid = column.validateValue(value);
					if(!valid)
						
				}
				else
				{
					
				}
				
				if (value != null)
					column.fillValue(obj, value);
			} catch (ExcelReadException e) {
				throw new CellValueException(rowindex, column, e.getMessage());
			}

		}

		 

		return obj;
	}

	public List<T> readRows() throws ExcelReadException, CellValueException {
		List<T> resultList = new ArrayList<T>(lastRowIndex - currentRowIndex + 1);
		for (; currentRowIndex <= lastRowIndex;) {
			T obj = null;

			try {
				obj = readRow();
			} catch (CellValueException e) {
				if (!continueIfDataError)
					throw e;
				else {
					e.printStackTrace();
					continue;
				}
			}

			if (obj != null)
				resultList.add(obj);
		}

		return resultList;
	}

	/**
	 * 是否在读取一行记录的数据错误的情况下继续下一行
	 */
	public boolean isContinueIfDataError() {
		return continueIfDataError;
	}

	/**
	 * 是否在读取一行记录的数据错误的情况下继续下一行
	 */
	public void setContinueIfDataError(boolean continueIfDataError) {
		this.continueIfDataError = continueIfDataError;
	}

}
