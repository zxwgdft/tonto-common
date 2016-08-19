package com.tonto.common.excel.base;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.tonto.common.excel.ValueFormatException;

public class DefaultCell implements ICell {

	private Cell cell;

	public DefaultCell(Cell cell) {
		this.cell = cell;
	}

	private static final SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateformat4 = new SimpleDateFormat("yyyy.MM.dd");

	@Override
	public Date getDate() throws ValueFormatException {
		if (cell == null)
			throw new NullPointerException();

		int type = cell.getCellType();
		if (type == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell))
			return cell.getDateCellValue();
		if (type == Cell.CELL_TYPE_STRING) {
			String str = cell.getStringCellValue();

			if (str == null)
				return null;
			str = str.trim();

			if ("".equals(str))
				return null;

			try {
				return dateformat1.parse(str);
			} catch (ParseException e) {
				try {
					return dateformat2.parse(str);
				} catch (ParseException e1) {
					try {
						return dateformat3.parse(str);
					} catch (ParseException e2) {
						try {
							return dateformat4.parse(str);
						} catch (ParseException e3) {
							throw new ValueFormatException();
						}
					}
				}
			}
		}

		throw new ValueFormatException();
	}

	@Override
	public String getString() {
		if (cell == null)
			throw new NullPointerException();

		String result = null;
		int type = cell.getCellType();
		switch (type) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "true" : "false";
		case Cell.CELL_TYPE_STRING: {
			result = cell.getStringCellValue();
			break;
		}
		case Cell.CELL_TYPE_NUMERIC: {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					result = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					result = null;
				}
			} else {
				result = new BigDecimal(cell.getNumericCellValue()).toString();
			}
			break;
		}
		case Cell.CELL_TYPE_FORMULA: {
			result = cell.getStringCellValue();
			if (result == null || "".equals(result))
				result = new BigDecimal(cell.getNumericCellValue()).toString();
			break;
		}
		default: {
			result = null;
		}
		}
		if (result == null)
			return null;
		result = result.trim();
		return result.equals("") ? null : result;
	}

	@Override
	public Integer getInteger() throws ValueFormatException {
		if (cell == null)
			throw new NullPointerException();

		int type = cell.getCellType();
		if (type == Cell.CELL_TYPE_NUMERIC)
			return (int) Math.round(cell.getNumericCellValue());
		else if (type == Cell.CELL_TYPE_STRING) {
			String str = cell.getStringCellValue();
			try {
				return Integer.valueOf(str);
			} catch (Exception e) {
				throw new ValueFormatException();
			}
		}

		throw new ValueFormatException();
	}

	@Override
	public Boolean getBoolean() throws ValueFormatException {
		if (cell == null)
			throw new NullPointerException();

		int type = cell.getCellType();
		if (type == Cell.CELL_TYPE_BOOLEAN)
			return cell.getBooleanCellValue();
		else if (type == Cell.CELL_TYPE_STRING) {
			String str = cell.getStringCellValue();
			if ("Y".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str))
				return true;
			if ("N".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str))
				return false;

		}

		throw new ValueFormatException();
	}

	@Override
	public Double getDouble() throws ValueFormatException {
		if (cell == null)
			throw new NullPointerException();

		int type = cell.getCellType();
		if (type == Cell.CELL_TYPE_NUMERIC)
			return cell.getNumericCellValue();
		else if (type == Cell.CELL_TYPE_STRING) {
			String str = cell.getStringCellValue();
			try {
				return Double.valueOf(str);
			} catch (Exception e) {
				throw new ValueFormatException();
			}
		}

		throw new ValueFormatException();
	}

	@Override
	public Long getLong() throws ValueFormatException {
		if (cell == null)
			throw new NullPointerException();

		int type = cell.getCellType();
		if (type == Cell.CELL_TYPE_NUMERIC)
			return Math.round(cell.getNumericCellValue());
		else if (type == Cell.CELL_TYPE_STRING) {
			String str = cell.getStringCellValue();
			try {
				return Long.valueOf(str);
			} catch (Exception e) {
				throw new ValueFormatException();
			}
		}

		throw new ValueFormatException();
	}

}
