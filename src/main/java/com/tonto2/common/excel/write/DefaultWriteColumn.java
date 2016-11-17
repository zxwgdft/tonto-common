package com.tonto2.common.excel.write;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 默认写Excel的列
 * 
 * @author TontZhou
 * 
 */
public class DefaultWriteColumn extends WriteColumn {

	private final static Logger logger = Logger.getLogger(DefaultWriteColumn.class);

	Field field;

	CellStyle style;

	@Override
	public Object peelData(Object data) {
		try {

			if (data != null) {
				String dataPath = getDataPath();
				if (dataPath != null)
					data = getData(data);
				field.setAccessible(true);
				return field.get(data);
			}

		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.warn("无法通过Filed：" + field + "反射获取到值，错误信息：" + e.getMessage(),e);
		}

		return data;
	}

	@Override
	public int write(Object data, Sheet sheet, Workbook workbook, int rowNum, int span, CellStyle commonCellStyle) {

		Row row = sheet.getRow(rowNum);
		if (row == null)
			row = sheet.createRow(rowNum);

		Cell cell = row.createCell(cellIndex);

		setCellValue(cell, data);

		// 固定宽度
		if (!autoWidth && width > 0) {
			sheet.setColumnWidth(cellIndex, width * 256);
		}

		if (span > 1) {
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + span - 1, cellIndex, cellIndex));
		}
		
		cell.setCellStyle(getCellStyle(workbook, commonCellStyle));

		return span;
	}

	@Override
	public void setCellValue(Cell cell, Object value) {
		
		String show = ""; 
		
		if(valueFormator != null)
		{
			show = valueFormator.format(value);
		}
		else if(value == null)
		{
			show = defaultEmptyValue;
		}
		else
		{		
			if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			} else if (value instanceof BigDecimal) {
				cell.setCellValue(((BigDecimal) value).doubleValue());
			} else if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof Long) {
				cell.setCellValue((Long) value);
			} else if (value instanceof Float) {
				cell.setCellValue((Float) value);
			} else if (value instanceof Date) {
				cell.setCellValue((Date) value);
			} else if (value instanceof Calendar) {
				cell.setCellValue((Calendar) value);
			} else {
				cell.setCellValue(value.toString());
			}			
		}
		
		cell.setCellValue(show);

	}

	@Override
	public CellStyle getCellStyle(Workbook workbook, CellStyle commonCellStyle) {

		if (style == null)
			style = createCellStyle(workbook, commonCellStyle);
		return style;
	}

	private CellStyle createCellStyle(Workbook workbook, CellStyle commonCellStyle) {

		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// copy通用样式
		if (commonCellStyle != null)
			style.cloneStyleFrom(commonCellStyle);

		if (!"".equals(format)) {
			// 是否为excel自带格式化定义
			short fmt = (short) BuiltinFormats.getBuiltinFormat(format);
			fmt = fmt == -1 ? workbook.createDataFormat().getFormat(format) : fmt;
			style.setDataFormat(fmt);
		}

		style.setWrapText(wrapText);

		if (alignment > -1)
			style.setAlignment((short) alignment);

		return style;
	}

}
