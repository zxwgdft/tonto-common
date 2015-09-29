package com.tonto.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.tonto.common.excel.RowSet.CellSet;

public class ExcelWriter<T> {
	
	
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
	
	
	private RowSet rowSet;
	
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
		
	 	this.rowSet=RowSet.getRowSet(clazz, workbook, commonCellStyle);
		
		if(rowSet.cellsets==null||rowSet.cellsets.size()==0)
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
		writeRowTitle(currentSheet.createRow(currentRowIndex++),rowSet);		
	}
	
	private void writeRowTitle(Row row,RowSet rowset){
		for(CellSet set:rowset.cellsets)		
		{
			if(set.rowset!=null)
				writeRowTitle(row,set.rowset);
			else
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
			currentRowIndex+=writeRow(currentRowIndex,rowSet,data);
		}
	}
	
	public void writeOne(T data) throws ExcelWriteException{
		currentRowIndex+=writeRow(currentRowIndex,rowSet,data);
	}
	
	private int writeRow(int rowNum,RowSet rowset,Object data) throws ExcelWriteException{
		rowset.span=1;
		Row row=currentSheet.getRow(rowNum);
		row=row==null?currentSheet.createRow(rowNum):row;
		
		List<CellSet> csets=rowset.cellsets;
		for(CellSet cset:csets)
		{
			if(cset.rowset!=null)
			{
				Object value=null;
				try {
					value=cset.field.get(data);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ExcelWriteException("无法获取实例对象属性值");
				} 
				
				if(value instanceof Collection)
				{
					Collection<?> coll=(Collection<?>) value;
					int rowsize=0;
					for(Object obj:coll)
					{	
						rowsize+=writeRow(rowNum+rowsize,cset.rowset,obj);
					}
					rowset.span=Math.max(rowset.span, rowsize);
				}
				else
				{
					throw new ExcelWriteException("子行必须是Collection接口实现类");
				}
			}	
		}
				
		for(CellSet cset:csets)
		{
			if(cset.rowset==null)
			{
				Object value=null;
				try {
					value=cset.field.get(data);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ExcelWriteException("无法获取实例对象属性值");
				} 
				
				
				if(cset.convert!=null)
				{
					try {
						value=cset.convert.convert(value);
					} catch (ValueFormatException e) {
						e.printStackTrace();
						throw new ExcelWriteException(cset.name+"数据字段转化错误");
					}
					
				}
				
				value=value==null?cset.defaultValue:value;

				
				Cell cell=row.createCell(cset.index);
				
				
				if(rowset.span>1)
				{					
					currentSheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum+rowset.span-1,cset.index,cset.index));
				}	
			
				cell.setCellStyle(cset.style);	
											
				
				if(value!=null)
				{
					if(cset.autoWidth)
					{
						String st= value.toString();
						cset.maxCharSize=st.length();
					}
					
					if(value instanceof String)
					{
						cell.setCellValue((String) value);
					}
					else if(value instanceof Integer)
					{
						cell.setCellValue((Integer)value);
					}
					else if(value instanceof BigDecimal)
					{
						cell.setCellValue(((BigDecimal)value).doubleValue());
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
		return rowset.span;
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
		//ExcelColumnSet columnSet=columnSetMap.get(columnIndex);		
		//return columnSet==null?null:columnSet.style;
		return null;
	}
	
	/**
	 * 设置某列的{@link CellStyle}
	 * @param columnIndex
	 * @param style
	 */
	public void setColumnStyle(int columnIndex,CellStyle style)
	{
		//if(style==null)
			//return;
		//ExcelColumnSet columnSet=columnSetMap.get(columnIndex);	
		//if(columnSet!=null)
			//columnSet.style=style;
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
