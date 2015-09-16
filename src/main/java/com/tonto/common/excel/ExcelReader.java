package com.tonto.common.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.Property;
import com.tonto.common.base.annotation.PropertyConvert;
import com.tonto.common.base.annotation.PropertyType;
import com.tonto.common.base.annotation.PropertyValidate;
import com.tonto.common.base.annotation.Validate;
import com.tonto.common.excel.base.ICell;
import com.tonto.common.excel.base.IRow;
import com.tonto.common.excel.base.ISheet;

public class ExcelReader<T> {
		
	private Map<String,FieldSet> fieldCacheMap;
	private Map<Class<?>,PropertyConvert<?>> convertCacheMap;
	private Map<Class<?>,PropertyValidate> validateCacheMap;
	
	private ISheet sheet;
	private int currentRowIndex;
	private int lastRowIndex;
	private int cellSize;
	
	private List<FieldSet> validFieldSetList;
	private Class<T> clazz;
	
	/**是否在读取一行记录的数据错误的情况下继续下一行*/
	private boolean continueIfDataError=false;
	/**
	 * 
	 * @param clazz 需要被读取映射到的Class
	 * @param sheet excel资源
	 * @throws ExcelReadException 不能创建{@link PropertyConvert}实例时会抛出
	 */
	public ExcelReader(Class<T> clazz,ISheet sheet) throws ExcelReadException
	{	
		this(clazz,sheet,-1);		
	}
	
	public ExcelReader(Class<T> clazz,ISheet sheet,int titleRowIndex) throws ExcelReadException
	{
		fieldCacheMap=new HashMap<String,FieldSet>();
		
		
		if(sheet==null)
			throw new ExcelReadException("Sheet不能为Null");
		if(clazz==null)
			throw new ExcelReadException("Class不能为Null");
		
		this.clazz=clazz;
		this.sheet=sheet;
		
		lastRowIndex=sheet.getLastRowNum();
		currentRowIndex=0;
		
		// 父类属性也扫描
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Property property = field.getAnnotation(Property.class);
			if (property != null) {
				// 暴力反射
				field.setAccessible(true);

			
				String name = property.name();
				
				if ("".equals(name)) {
					name = field.getName();
				}
				
				FieldSet fieldset=new FieldSet();
				fieldset.field=field;
				fieldset.type=property.type();
				fieldset.nullable=property.nullable();
				
				if(property.minLength()>0)
					fieldset.minLength=property.minLength();
				
				if(property.maxLength()>0)
					fieldset.maxLength=property.maxLength();
				
				String regex=property.regex();
				if(regex!=null&&!"".equals(regex))
					fieldset.pattern=Pattern.compile(regex);
				
				Convert convert=field.getAnnotation(Convert.class);
				if(convert !=null)
				{
					Class<?> cla=convert.convert();
					
					if(convertCacheMap==null)
						convertCacheMap=new HashMap<Class<?>,PropertyConvert<?>>();
					
					PropertyConvert<?> propertyconvert=convertCacheMap.get(cla);
					
					if(propertyconvert==null)
					{
						try {
							propertyconvert=(PropertyConvert<?>) cla.newInstance();
						} catch (Exception e) {
							e.printStackTrace();
							throw new ExcelReadException("不能创建"+cla.getName()+"的实例");
						}
						
						convertCacheMap.put(cla, propertyconvert);
					}	
					
					fieldset.convert=propertyconvert;
				}
				
				Validate validate=field.getAnnotation(Validate.class);
				if(validate !=null)
				{
					Class<?> cla=validate.validate();
					
					if(validateCacheMap==null)
						validateCacheMap=new HashMap<Class<?>,PropertyValidate>();
					
					PropertyValidate valueValidate=validateCacheMap.get(cla);
					
					if(valueValidate==null)
					{
						try {
							valueValidate=(PropertyValidate) cla.newInstance();
						} catch (Exception e) {
							e.printStackTrace();
							throw new ExcelReadException("不能创建"+cla.getName()+"的实例");
						}
						
						validateCacheMap.put(cla, valueValidate);
					}
					
					fieldset.validate=valueValidate;
				}
				
				fieldCacheMap.put(name, fieldset);
			}
		}	
		
		cellSize=fieldCacheMap.keySet().size();
		
		if(cellSize<=0)
			throw new ExcelReadException("没有任何需要读取的属性");
		
		validFieldSetList=new ArrayList<FieldSet>(cellSize);
		
		boolean result=false;
		
		if(titleRowIndex>0&&titleRowIndex<lastRowIndex)
		{
			result=readColumn(titleRowIndex);
			if(result)
				currentRowIndex=++titleRowIndex;
		}
		
		if(!result)
		{
			result=readColumn();
		}
		
		if(!result)
			throw new ExcelReadException("没有找到任何对应的属性列");
		
		if(fieldCacheMap.size()!=validFieldSetList.size())
			throw new ExcelReadException("没有找到全部对应的属性列");
	}
	
	/**
	 * 顺序读取列
	 * @return
	 */
	private boolean readColumn()
	{
		for(;currentRowIndex<=lastRowIndex;)
		{
			if(readColumn(currentRowIndex++))
				return true;
		}
		return false;
	}
	
	/**
	 * 如果找到一个Cell的内容匹配到属性，则确认读取到了所有列，并且把列序号设置到FieldSet中
	 * @param rowIndex
	 * @return
	 */
	private boolean readColumn(int rowIndex)
	{
		boolean result=false;
		IRow row=sheet.getRow(rowIndex);
		for(int i=0;i<cellSize;i++)
		{
			ICell cell=row.getCell(i);
			if(cell==null)
				continue;
			String name;
			try {
				name = cell.getString();
				FieldSet fieldset=fieldCacheMap.get(name);
				if(fieldset!=null)
				{
					fieldset.name=name;
					fieldset.cellIndex=i;
					validFieldSetList.add(fieldset);
					result=true;
				}
			} catch (ValueFormatException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	/***
	 * 
	 * @return
	 * @throws ExcelReadException	解析数据时未指明的其他错误
	 * @throws ValueFormatException cell的值格式错误时候抛出
	 */
	public T readRow() throws ExcelReadException, ValueFormatException
	{
		int rowindex=currentRowIndex++;
		
		IRow row=sheet.getRow(rowindex);
		
		if(row==null)
			return null;
		
		T obj=null;
		
		try {
			obj=clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcelReadException("无法创建转化的实例，确认是否有无参数的构造函数存在！");
		} 
		
		for(int i=0;i<validFieldSetList.size();i++)
		{
			FieldSet fieldset=validFieldSetList.get(i);
			
			ICell cell=row.getCell(fieldset.cellIndex);
			
			Object value=null;
			
			if(cell!=null)
			{
				if(fieldset.convert!=null)
				{
					try{
						value=fieldset.convert.convert(cell.getString());				
					}
					catch(ValueFormatException e)
					{
						throw new ValueFormatException(rowindex,fieldset.name,e.getReason()); 
					}
				}
				else
				{
					PropertyType type=fieldset.type;
					if(type==PropertyType.DATE)
						value=cell.getDate();
					else if(type==PropertyType.DOUBLE)
						value=cell.getDouble();
					else if(type==PropertyType.INTEGER)
						value=cell.getInteger();
					else if(type==PropertyType.LONG)
						value=cell.getLong();
					else if(type==PropertyType.BOOLEAN)
						value=cell.getBoolean();
					else 
						value=cell.getString();	
				}
			}
			
			if(value==null)
			{
				if(!fieldset.nullable)
					throw new ValueFormatException(rowindex,fieldset.name,"不能为空");
				continue;				
			}
			
			if(value instanceof String)
			{
				String s=(String) value;
				int size=s.length();
				if(fieldset.minLength!=null&&size<fieldset.minLength)
				{
					throw new ValueFormatException(rowindex,fieldset.name,"长度不能少于"+fieldset.minLength);
				}	
				
				if(fieldset.maxLength!=null&&size>fieldset.maxLength)
				{
					throw new ValueFormatException(rowindex,fieldset.name,"长度不能超过"+fieldset.maxLength);
				}
			}
			
			if(fieldset.pattern!=null)
			{
				if(!fieldset.pattern.matcher(value.toString()).matches())
					throw new ValueFormatException(rowindex,fieldset.name,"格式错误"); 
			}

			
			try {
				fieldset.field.set(obj, value);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExcelReadException("无法把"+fieldset.name+"的值赋值到实例中");
			} 
		}
		
		for(int i=0;i<validFieldSetList.size();i++)
		{
			FieldSet fieldset=validFieldSetList.get(i);
			
			if(fieldset.validate!=null)
			{
				Object value;
				try {
					value = fieldset.field.get(obj);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ExcelReadException("无法获取到实例中属性:"+fieldset.name+"的值");
				} 
				
				if(!fieldset.validate.validate(obj,value))
					throw new ValueFormatException(rowindex,fieldset.name,"格式错误");
				
			}
		}
		
		
		return obj;
	}
	
	public List<T> readRows() throws ExcelReadException, ValueFormatException
	{
		List<T> resultList=new ArrayList<T>(lastRowIndex-currentRowIndex+1);
		for(;currentRowIndex<=lastRowIndex;)
		{
			T obj=null;
			
			
			try {
				obj = readRow();
			}  catch (ValueFormatException e) {				
				if(!continueIfDataError)
					throw e;
				else
				{
					e.printStackTrace();
					continue;
				}
			}
				
			if(obj!=null)
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
	
	class FieldSet{
		//对应excel上的列名
		String name;
		//对应实例中的属性
		Field field;
		//数据类型
		PropertyType type;
		//是否可为空
		boolean nullable=true;
		//对应excel的列序号
		int cellIndex=-1;
		//正则校验值
		Pattern pattern;
		//转化值方法
		PropertyConvert<?> convert;
		//校验值方法
		PropertyValidate validate;
		
		//如果为字符串的话，字符串最小长度，null不检查
		Integer minLength;
		//如果为字符串的话，字符串最大长度，null不检查
		Integer maxLength;
	}
	
}
