package com.tonto.common.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.ExcelColumn;
import com.tonto.common.base.annotation.PropertyConvert;
import com.tonto.common.base.annotation.SubExcelRow;


public class RowSet {
	
	private static Map<Class<?>,PropertyConvert<?>> convertCacheMap;
	
	List<CellSet> cellsets;
	int span=1;
	
	public static class CellSet{
		RowSet rowset;
		
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
	
	public static RowSet getRowSet(Class<?> clazz,Workbook workbook,CellStyle commonCellStyle) throws ExcelWriteException
	{
		List<CellSet> cellsets=new ArrayList<CellSet>();
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
						
			field.setAccessible(true);
						
			ExcelColumn excelColumn=field.getAnnotation(ExcelColumn.class);
			if(excelColumn!=null)
			{	
				CellSet cellset=new CellSet();
				cellset.field=field;
				String name=excelColumn.name();
				cellset.name="".equals(name)?field.getName():name;
				
				Integer index=excelColumn.index();
				cellset.index=index;
				cellset.field=field;
				cellset.width=excelColumn.width();
				
				CellStyle style=workbook.createCellStyle();
				style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				//copy通用样式
				if(commonCellStyle!=null)
					style.cloneStyleFrom(commonCellStyle);
				
				cellset.style=style;
				
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
					cellset.defaultValue=defaultValue;
				
				cellset.autoWidth=excelColumn.autoWidth();
				
				
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
					
					cellset.convert=propertyconvert;
				}
				cellsets.add(cellset);
			}
			else
			{
				SubExcelRow subExcelRow=field.getAnnotation(SubExcelRow.class);
				if(subExcelRow!=null)
				{
					CellSet cellset=new CellSet();
					cellset.field=field;
					Class<?> subClass=subExcelRow.value();
					cellset.rowset=getRowSet(subClass,workbook,commonCellStyle);
					cellsets.add(cellset);
				}
			}
			
		}
		RowSet rowset=new RowSet();
		rowset.cellsets=cellsets;
		
		return rowset;
	}
	
}
