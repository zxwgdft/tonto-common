package com.tonto2.common.excel.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tonto2.common.excel.write.exception.ExcelWriteException;

public class ExcelWriter<T> {

	private static final Logger logger = Logger.getLogger(ExcelWriter.class);

	/* 通用的样式 */
	protected CellStyle commonCellStyle;
	/* 标题列样式 */
	protected CellStyle titleCellStyle;

	protected Workbook workbook;

	/* 工作簿最大行数，-1则没有限制 */
	protected int sheetMaxSize = -1;
	/* 当前输入行 */
	protected int currentRowIndex = -1;
	/* 当前工作簿序号 */
	protected int currentSheetIndex = -1;

	/* 当前工作簿 */
	protected Sheet currentSheet;
	/* 写行描述 */
	protected WriteRow writeRow;

	public ExcelWriter(Workbook workbook) throws ExcelWriteException {
		this(workbook, null, 0, 1, null, null);
	}

	/**
	 * 
	 * @param workbook
	 *            工作空间
	 * @param startIndex
	 *            起始写入行号
	 * @param sheetIndex
	 *            工作簿序号
	 * @param commonStyle
	 *            通用cell的样式
	 * @param titleStyle
	 *            标题cell的样式
	 * @throws ExcelWriteException
	 */
	public ExcelWriter(Workbook workbook, WriteRow writeRow, int startIndex, int sheetIndex, CellStyle commonStyle,
			CellStyle titleStyle) throws ExcelWriteException {

		this.workbook = workbook;

		commonCellStyle = commonStyle == null ? workbook.createCellStyle() : commonStyle;

		if (titleStyle == null) {
			titleCellStyle = workbook.createCellStyle();

			if (commonStyle != null)
				titleCellStyle.cloneStyleFrom(commonStyle);

			titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else {
			titleCellStyle = titleStyle;
		}

		setCurrentSheetIndex(sheetIndex);
		setCurrentRowIndex(startIndex);
		setWriteRow(writeRow);
	}

	/**
	 * 写标题行
	 */
	public void writeTitle() {
		writeRowTitle(currentSheet.createRow(currentRowIndex++), writeRow);
	}

	/**
	 * 写标题
	 * 
	 * @param row
	 * @param wr
	 */
	protected void writeRowTitle(Row row, WriteRow wr) {

		List<WriteColumn> columns = wr.getColumns();

		if (columns != null && columns.size() > 0) {
			for (WriteColumn column : columns) {
				Cell cell = row.createCell(column.cellIndex);
				cell.setCellValue(column.name);

				// 固定宽度
				if (!column.autoWidth && column.width > 0) {
					currentSheet.setColumnWidth(column.cellIndex, column.width * 256);
				}

				cell.setCellStyle(titleCellStyle);
			}
		}

		List<WriteRow> wrows = wr.getSubRows();

		if (wrows != null && wrows.size() > 0) {
			for (WriteRow wrow : wrows)
				writeRowTitle(row, wrow);
		}

	}

	public void write(Collection<T> datas) {

		currentRowIndex += writeRow.write(datas, currentSheet, workbook, currentRowIndex, 1, commonCellStyle);
		if (sheetMaxSize > 0 && currentRowIndex > sheetMaxSize) {
			currentSheetIndex++;
			openCurrentSheet();
		}

	}

	/**
	 * 增加一个工作簿，并且跳转到该工作簿下开始写操作
	 */
	protected void openCurrentSheet() {

		try {
			currentSheet = workbook.getSheetAt(currentSheetIndex);
		} catch (IllegalArgumentException e) {
			// 没有则创建一个新的
			currentSheet = workbook.createSheet("sheet&" + currentSheetIndex);
			currentSheetIndex = workbook.getSheetIndex(currentSheet);
		}

		currentRowIndex = 0;
	}

	/**
	 * 
	 * 输出
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void output(OutputStream output) throws IOException {
		workbook.write(output);
	}

	/** 当前行号，0开始 */
	public int getCurrentRowIndex() {
		return currentRowIndex;
	}

	/** 当前行号，0开始 */
	public void setCurrentRowIndex(int currentRowIndex) {

		if (currentRowIndex >= 0)
			this.currentRowIndex = currentRowIndex;
		else
			logger.warn("row index:" + currentRowIndex + " is invalid");

	}

	/** 当前工作簿序号 */
	public int getCurrentSheetIndex() {
		return currentSheetIndex;
	}

	/** 当前工作簿序号 */
	public void setCurrentSheetIndex(int currentSheetIndex) {

		if (currentSheetIndex > 0) {
			if (this.currentSheetIndex != currentSheetIndex) {
				this.currentSheetIndex = currentSheetIndex;
				openCurrentSheet();
			}
		} else
			logger.warn("sheet index:" + currentSheetIndex + " is invalid");
	}

	public WriteRow getWriteRow() {
		return writeRow;
	}

	public void setWriteRow(WriteRow writeRow) {
		this.writeRow = writeRow;
	}

	/** 通用样式 */
	public CellStyle getCommonCellStyle() {
		return commonCellStyle;
	}

	/** 通用样式 */
	public void setCommonCellStyle(CellStyle commonCellStyle) {
		if (commonCellStyle != null)
			this.commonCellStyle = commonCellStyle;
		else
			logger.warn("common cell style can't be null");
	}

	/** 标题样式 */
	public CellStyle getTitleCellStyle() {
		return titleCellStyle;
	}

	/** 标题样式 */
	public void setTitleCellStyle(CellStyle titleCellStyle) {
		if (titleCellStyle != null)
			this.titleCellStyle = titleCellStyle;
		else
			logger.warn("title cell style can't be null");
	}

	/** 每个工作簿最大行数 */
	public int getSheetMaxSize() {
		return sheetMaxSize;
	}

	/** 每个工作簿最大行数 */
	public void setSheetMaxSize(int sheetMaxSize) {
		if (sheetMaxSize > 1)
			this.sheetMaxSize = sheetMaxSize;
		else
			logger.warn("sheet max size:" + currentSheetIndex + " is invalid");
	}
}
