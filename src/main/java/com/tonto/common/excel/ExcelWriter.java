package com.tonto.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.ExcelColumn;
import com.tonto.common.base.annotation.PropertyConvert;

public class ExcelWriter<T> {
	
	private Map<Class<?>,PropertyConvert<?>> convertCacheMap;
	
	/*通用的样式*/
	private CellStyle commonCellStyle;
	/*标题列样式*/
	private CellStyle titleCellStyle;
	
	private Workbook workbook;
	
	/*工作簿最大行数，-1则没有限制*/
	private int sheetMaxSize=-1;
	/*当前输入行*/
	private int currentRowIndex=0;
	/*当前工作簿序号*/
	private int currentSheetIndex=1;
	/*当前工作簿*/
	private Sheet currentSheet;
	
	Map<Integer,ExcelColumnSet> columnSetMap=new HashMap<Integer,ExcelColumnSet>();
	
	public ExcelWriter(Class<T> clazz,Workbook workbook) throws ExcelWriteException
	{
		this(clazz,workbook,0,1,null,null);
	}
	
	public ExcelWriter(Class<T> clazz,Workbook workbook,int startIndex,int sheetIndex,CellStyle commonStyle,CellStyle titleStyle) throws ExcelWriteException
	{
		
		if(clazz==null||workbook==null)
			throw new NullPointerException();
		
		this.commonCellStyle=commonStyle;
		this.titleCellStyle=titleStyle;
		this.workbook=workbook;
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			ExcelColumn excelColumn=field.getAnnotation(ExcelColumn.class);
			if(excelColumn!=null)
			{
				ExcelColumnSet columnSet=new ExcelColumnSet();
				
				String name=excelColumn.name();
				columnSet.name="".equals(name)?field.getName():name;
				
				Integer index=excelColumn.index();
				columnSet.index=index;
				columnSet.field=field;
				columnSet.width=excelColumn.width();
				
				CellStyle style=workbook.createCellStyle();
				
				//copy通用样式
				if(commonCellStyle!=null)
					style.cloneStyleFrom(commonCellStyle);
				
				columnSet.style=style;
				
				String format=excelColumn.format();
				if(!"".equals(format))
				{
					//是否为excel自带格式化定义
					short fmt=(short) BuiltinFormats.getBuiltinFormat(format);
					fmt=fmt==-1?workbook.createDataFormat().getFormat(format):fmt;
					style.setDataFormat(fmt);					
				}
				
				if(excelColumn.wrapText())
					style.setWrapText(true);
				
				int alignment=excelColumn.alignment();
				if(alignment!=-1)
					style.setAlignment((short) alignment);
				
				String defaultValue=excelColumn.defaultValue();
				if(!"".equals(defaultValue))
					columnSet.defaultValue=defaultValue;
				
				columnSet.autoWidth=excelColumn.autoWidth();
				
				
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
							throw new ExcelWriteException("不能创建"+cla.getName()+"的实例");
						}
						
						convertCacheMap.put(cla, propertyconvert);
					}	
					
					columnSet.convert=propertyconvert;
				}
				
				
				columnSetMap.put(index, columnSet);
			}
			
			
		}
		
		if(columnSetMap.size()==0)
		{
			throw new ExcelWriteException("没有任何列需要输出到excel！");
		}
		
		
		currentSheetIndex=sheetIndex>0?sheetIndex:1;		
		startNewSheet();
		currentRowIndex=startIndex>0?startIndex:0;
		writeTitle();
	}
	
	private void writeTitle()
	{
		Row row=currentSheet.createRow(currentRowIndex++);
		for(ExcelColumnSet set:columnSetMap.values())
		{
			Cell cell=row.createCell(set.index);
			cell.setCellValue(set.name);
			
			if(titleCellStyle==null)
			{
				if(commonCellStyle==null)
					titleCellStyle=workbook.createCellStyle();
				else
					titleCellStyle=commonCellStyle;
				titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			}
			if(!set.autoWidth&&set.width>0)
			{
				currentSheet.setColumnWidth(set.index, set.width*256);
			}
			
			cell.setCellStyle(titleCellStyle);
		}
	}
	
	private void startNewSheet()
	{
		try{
			currentSheet=workbook.getSheetAt(currentSheetIndex);
			currentSheetIndex++;
		}
		catch(IllegalArgumentException e)
		{
			currentSheet=workbook.createSheet("sheet"+currentSheetIndex);
			currentSheetIndex=workbook.getSheetIndex(currentSheet)+1;
		}
		
		currentRowIndex=0;
	}
	
	public void writeAll(Collection<T> datas) throws ExcelWriteException{
		for(T data:datas)
		{
			writeOne(data);
		}
	}
	
	public void writeOne(T data) throws ExcelWriteException
	{
		if(data==null)
			throw new NullPointerException();
		
		if(sheetMaxSize>0&&currentRowIndex>sheetMaxSize)
		{
			startNewSheet();
			writeTitle();
		}
		
		int rowindex=currentRowIndex++;
		Row row=currentSheet.createRow(rowindex);
		
		for(ExcelColumnSet set:columnSetMap.values())
		{
			Object value;
			try {
				value = set.field.get(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExcelWriteException("无法获取实例对象属性值");
			} 
			
			if(set.convert!=null)
			{
				try {
					value=set.convert.convert(value);
				} catch (ValueFormatException e) {
					e.printStackTrace();
					throw new ExcelWriteException(set.name+"数据字段转化错误");
				}
				
			}
			
			value=value==null?set.defaultValue:value;
			
			Cell cell=row.createCell(set.index);
			cell.setCellStyle(set.style);
			
			
			if(value!=null)
			{
				if(set.autoWidth)
				{
					String st= value.toString();
					set.maxCharSize=st.length();
				}
				
				if(value instanceof String)
				{
					cell.setCellValue((String) value);
				}
				else if(value instanceof Integer)
				{
					cell.setCellValue((Integer)value);
				}
				else if(value instanceof Double)
				{
					cell.setCellValue((Double)value);
				}
				else if(value instanceof Long)
				{
					cell.setCellValue((Long)value);
				}
				else if(value instanceof Float)
				{
					cell.setCellValue((Float)value);
				}
				else if(value instanceof Date)
				{
					cell.setCellValue((Date)value);
				}
				else if(value instanceof Calendar)
				{
					cell.setCellValue((Calendar)value);
				}
				else
				{
					cell.setCellValue(value.toString());
				}
				
			}
		}
	}
	
	public void output(OutputStream output) throws IOException
	{
		workbook.write(output);
	}
	
	/**
	 * 获取一个新的列{@link CellStyle}
	 * @return
	 */
	public CellStyle getNewColumnStyle()
	{
		return workbook.createCellStyle();
	}
	
	/**
	 * 获取某列{@link CellStyle}的拷贝
	 * @param columnIndex
	 * @return
	 */
	public CellStyle getColumnStyleCopy(int columnIndex)
	{
		ExcelColumnSet columnSet=columnSetMap.get(columnIndex);		
		return columnSet==null?null:columnSet.style;
	}
	
	/**
	 * 设置某列的{@link CellStyle}
	 * @param columnIndex
	 * @param style
	 */
	public void setColumnStyle(int columnIndex,CellStyle style)
	{
		if(style==null)
			return;
		ExcelColumnSet columnSet=columnSetMap.get(columnIndex);	
		if(columnSet!=null)
			columnSet.style=style;
	}
	
	
	
	public static class ExcelColumnSet{
		Field field;
		//列序号
		int index;
		//列标题
		String name;
		//列宽度
		int width;
		//是否自动适应宽度
		boolean autoWidth;
		//默认值
		String defaultValue;
		//列样式
		CellStyle style;
		//值转换类
		PropertyConvert<?> convert;
		
		int maxCharSize;
	}



	public CellStyle getCommonCellStyle() {
		return commonCellStyle;
	}

	public void setCommonCellStyle(CellStyle commonCellStyle) {
		this.commonCellStyle = commonCellStyle;
	}

	public CellStyle getTitleCellStyle() {
		return titleCellStyle;
	}

	public void setTitleCellStyle(CellStyle titleCellStyle) {
		this.titleCellStyle = titleCellStyle;
	}

	public int getSheetMaxSize() {
		return sheetMaxSize;
	}

	public void setSheetMaxSize(int sheetMaxSize) {
		this.sheetMaxSize = sheetMaxSize;
	}
	
}
