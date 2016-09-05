package com.tonto2.common.excel.read;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tonto2.common.base.property.PropertyValidate;

import com.tonto2.common.excel.base.ICell;
import com.tonto2.common.excel.base.IRow;
import com.tonto2.common.excel.base.ISheet;
import com.tonto2.common.excel.read.annotation.ReadPropertyConvert;
import com.tonto2.common.excel.read.exception.CellValueException;
import com.tonto2.common.excel.read.exception.ExcelReadException;

public class ExcelReader<T> {
	
	protected List<ReadColumn> columns;

	/*
	 * 根据类型找到对应的{@link ReadPropertyConvert}和{@link PropertyValidate}方法
	 */
	private Map<Class<?>, ReadPropertyConvert<?>> classConvertCacheMap = new HashMap<>();
	private Map<Class<?>, PropertyValidate> classValidateCacheMap = new HashMap<>();;

	/*
	 * 根据名称找到对应的{@link ReadPropertyConvert}和{@link PropertyValidate}方法
	 */
	private Map<String, ReadPropertyConvert<?>> nameConvertCacheMap = new HashMap<>();;
	private Map<String, PropertyValidate> nameValidateCacheMap = new HashMap<>();;
	
	/**
	 * 增加指定类型转换器
	 * @param convert
	 * @param clazz
	 */
	public void addPropertyConvert(ReadPropertyConvert<?> convert , Class<?> clazz){
		classConvertCacheMap.put(clazz, convert);
	}
	
	/**
	 * 增加指定属性的转换器（可以路径寻找子类）
	 * @param convert
	 * @param name
	 */
	public void addPropertyConvert(ReadPropertyConvert<?> convert , String name){
		nameConvertCacheMap.put(name, convert);
	}
	
	/**
	 * 增加指定类型的验证器
	 * @param validate
	 * @param clazz
	 */
	public void addPropertyConvert(PropertyValidate validate , Class<?> clazz){
		classValidateCacheMap.put(clazz, validate);
	}
	
	/**
	 * 增加指定属性的验证器（可以路径寻找子类）
	 * @param validate
	 * @param name
	 */
	public void addPropertyConvert(PropertyValidate validate , String name){
		nameValidateCacheMap.put(name, validate);
	}
	

	// 工作簿
	protected ISheet sheet;
	// 当前所在行号
	protected int currentRowIndex;
	// 最大行号
	protected int lastRowIndex;

	// 是否在读取一行记录的数据错误的情况下继续下一行
	protected boolean continueIfDataError = false;

	// 需要转化的对象类
	protected Class<T> target;
	
	public ExcelReader()
	{
		
	}
	
	public ExcelReader(Class<T> target, ISheet sheet, int startRow)
	{
		this.target = target;
		this.currentRowIndex = startRow;		
		this.sheet = sheet;	
		this.lastRowIndex = sheet.getLastRowNum();		
	}
	
	
	/**
	 * 创建实例
	 * @return
	 * @throws ExcelReadException
	 */
	protected T createInstance() throws ExcelReadException {

		try {
			return target.newInstance();
		} catch (Exception e) {
			throw new ExcelReadException("无法创建[" + target + "]转化的实例，确认是否有无参数的构造函数存在！", e);
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
		int size = columns.size();
		
		List<A> cache = new ArrayList<>();
		
		for (int i = 0; i<size; i++) {
			
			ReadColumn column = columns.get(i);
			
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
					String validMessage = column.validateValue(value);
					if(validMessage != null)
						throw new CellValueException(rowindex, column, validMessage);						
				}
				else
				{
					//特殊验证需要在填完所有值后进行
					cache.add(new A(column,value,validate));
				}
				
				if (value != null)
					column.fillValue(obj, value);
			} 
			catch(CellValueException e)
			{
				throw e;
			}
			catch (ExcelReadException e) {
				throw new CellValueException(rowindex, column, e.getMessage());
			}
		}
		
		for(A a: cache)
		{
			if(!a.validate.validate(obj, a.value))
				throw new CellValueException(rowindex, a.column, a.validate.getMessage());			
		}
		
		return obj;
	}

	public List<T> readRows() throws ExcelReadException {
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
	
	
	class A{
		
		private A(ReadColumn column,Object value, PropertyValidate validate)
		{
			this.column = column;
			this.value = value;
			this.validate = validate;
		}
		
		ReadColumn column;
		PropertyValidate validate;
		Object value;
		
	}
	
}
