package com.tonto2.common.excel.write;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tonto2.common.excel.write.annotation.CellFormat;
import com.tonto2.common.excel.write.annotation.ValueFormator;
import com.tonto2.common.excel.write.annotation.WriteBean;
import com.tonto2.common.excel.write.annotation.WriteProperty;
import com.tonto2.common.utils.reflect.ReflectUtil;

/**
 * 
 * 
 * @author TontZhou
 * 
 */
public class DefaultWriteRow extends WriteRow {

	private final static Logger logger = Logger.getLogger(DefaultWriteRow.class);

	Field field;

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

	@SuppressWarnings("rawtypes")
	@Override
	public int write(Object data, Sheet sheet, Workbook workbook, int rowNum, int span, CellStyle commonCellStyle) {

		if (data == null)
			return 0;

		Object[] array = null;

		if (ReflectUtil.isCollection(data.getClass())) {
			array = ((Collection) data).toArray();
		}

		if (array == null)
			return 0;

		int length = Array.getLength(array);
		int start = rowNum;

		for (int i = 0; i < length; i++) {

			Object item = Array.get(array, i);
			rowNum += writeOne(item, sheet, workbook, rowNum, span, commonCellStyle);
		}

		return rowNum - start;
	}

	protected int writeOne(Object data, Sheet sheet, Workbook workbook, int rowNum, int span, CellStyle commonCellStyle) {
		int maxSubSpan = 0;

		if (subRows != null && subRows.size() > 0) {
			for (WriteRow writeRow : subRows) {
				Object subData = writeRow.peelData(data);
				int s = writeRow.write(subData, sheet, workbook, rowNum, span, commonCellStyle);
				maxSubSpan = Math.max(s, maxSubSpan);
			}

		}

		if (columns != null && columns.size() > 0) {
			span = Math.max(span, maxSubSpan);
			for (WriteColumn writeColumn : columns) {
				Object columnData = writeColumn.peelData(data);
				writeColumn.write(columnData, sheet, workbook, rowNum, span, commonCellStyle);
			}

		} else {
			span = maxSubSpan;
		}

		return span;

	}

	public static WriteRow createWriteRow(Class<?> clazz, String path, String dataPath) {

		WriteRow row = new DefaultWriteRow();

		List<WriteColumn> columns = new ArrayList<>();
		List<WriteRow> subRows = new ArrayList<>();

		if (clazz == Object.class)
			return null;

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			Class<?> fieldClass = field.getType();
			String fieldPath = path == null ? field.getName() : path + "." + field.getName();

			WriteBean writeBean = field.getAnnotation(WriteBean.class);

			if (writeBean != null) {

				// 不考虑集合和数组中嵌套集合和数组的情况

				if (ReflectUtil.isCollection(fieldClass)) {
					Class<?> collType = ReflectUtil.getCollectionType(field);
					WriteRow subRow = createWriteRow(collType, fieldPath, null);
					if (subRow != null) {
						((DefaultWriteRow) subRow).field = field;
						subRows.add(subRow);
					}
				} else if (ReflectUtil.isMap(fieldClass)) {

					logger.warn("can't support <Map> type");

				} else if (fieldClass.isArray()) {
					Class<?> arrayType = fieldClass.getComponentType();
					WriteRow subRow = createWriteRow(arrayType, fieldPath, null);
					if (subRow != null) {
						((DefaultWriteRow) subRow).field = field;
						subRows.add(subRow);
					}
				} else {

					String fieldDataPath = dataPath == null ? field.getName() : dataPath + "."
							+ fieldClass.getSimpleName();

					WriteRow subRow = createWriteRow(fieldClass, fieldPath, fieldDataPath);
					if (subRow != null) {

						// 非数组的PROJ对象看作同一行

						List<WriteRow> rows = subRow.getSubRows();

						if (rows != null) {
							for (WriteRow r : rows)
								subRows.add(r);
						}

						List<WriteColumn> cs = subRow.getColumns();

						if (cs != null) {
							for (WriteColumn c : cs)
								columns.add(c);
						}

					}
				}

				continue;
			}

			WriteProperty writeProp = field.getAnnotation(WriteProperty.class);
			if (writeProp != null) {
				DefaultWriteColumn defaultColumn = new DefaultWriteColumn();

				defaultColumn.setId(fieldPath);
				defaultColumn.setCellIndex(writeProp.cellIndex());
				defaultColumn.setName("".equals(writeProp.name()) ? field.getName() : writeProp.name());
				defaultColumn.setWidth(writeProp.width());
				defaultColumn.setAlignment(writeProp.alignment());
				defaultColumn.setAutoWidth(writeProp.autoWidth());
				defaultColumn.setDefaultEmptyValue(writeProp.defaultValue());
				defaultColumn.setFormat(writeProp.format());
				defaultColumn.setWrapText(writeProp.wrapText());
				defaultColumn.setDataPath(dataPath);

				defaultColumn.field = field;

				CellFormat cellFormat = field.getAnnotation(CellFormat.class);
				if (cellFormat != null) {
					Class<? extends ValueFormator> formatClass = cellFormat.format();

					ValueFormator formator = formators.get(formatClass);
					if (formator == null) {
						try {
							formator = formatClass.newInstance();
							formators.put(formatClass, formator);
						} catch (InstantiationException | IllegalAccessException e) {
							logger.warn("无法发射生成类：" + formatClass + "的实例对象，错误信息：" + e.getMessage(), e);
						}
					}

					defaultColumn.setValueFormator(formator);
				}

				columns.add(defaultColumn);
			}

		}

		row.setColumns(columns);
		row.setSubRows(subRows);
		row.setDataPath(dataPath);

		return row;

	}

	private static Map<Class<? extends ValueFormator>, ValueFormator> formators = new HashMap<>();

}
