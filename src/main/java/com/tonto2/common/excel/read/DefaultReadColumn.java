package com.tonto2.common.excel.read;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.tonto2.common.base.property.ConvertException;
import com.tonto2.common.base.property.PropertyValidate;
import com.tonto2.common.base.property.annotation.Validate;
import com.tonto2.common.excel.base.ICell;
import com.tonto2.common.excel.read.annotation.CellConvert;
import com.tonto2.common.excel.read.annotation.ReadBean;
import com.tonto2.common.excel.read.annotation.ReadProperty;
import com.tonto2.common.excel.read.annotation.ReadPropertyConvert;
import com.tonto2.common.excel.read.exception.ExcelReadException;
import com.tonto2.common.utils.StringParser;
import com.tonto2.common.utils.reflect.PathSetter;
import com.tonto2.common.utils.validate.ValidateUtil;

public class DefaultReadColumn extends ReadColumn {

	// 是否可为空
	boolean nullable = true;
	// 正则校验值
	Pattern pattern;
	// 如果为字符串的话，字符串最小长度，null不检查
	Integer minLength;
	// 如果为字符串的话，字符串最大长度，null不检查
	Integer maxLength;
	// 整数枚举
	int[] intEnum;
	// 最大值
	BigDecimal max;
	// 最小值
	BigDecimal min;

	Class<?> type = String.class;

	@Override
	public String validateValue(Object value) {

		if (value == null && !nullable)
			return "不能为空";

		String str = value.toString();

		if (pattern != null && !pattern.matcher(str).matches())
			return "格式不正确";

		if (minLength != null && str.length() < minLength)
			return "值长度不能小于" + minLength;

		if (maxLength != null && str.length() > maxLength)
			return "值长度不能大于" + maxLength;

		if (min != null && !ValidateUtil.validLessNumber(min, true, value))
			return "值不能小于" + value;

		if (max != null && !ValidateUtil.validGreatNumber(max, true, value))
			return "值不能大于" + value;

		if (intEnum != null && intEnum.length > 0 && !ValidateUtil.validContainInt(value, intEnum))
			return "值必须在" + StringParser.parseString(intEnum) + "之中";

		return null;
	}

	@Override
	public Object convertValue(ICell cell) throws ExcelReadException {
		if(cell == null)
			throw new ExcelReadException("值为空");
		
		Class<?> type = getType();
		try {
			if (type == String.class)
				return cell.getString();
			if (type == Integer.class)
				return cell.getInteger();
			if (type == Long.class)
				return cell.getLong();
			if (type == Float.class) {
				Double d = cell.getDouble();
				return d == null ? null : d.floatValue();
			}
			if (type == Double.class)
				return cell.getDouble();
			if (type == Date.class)
				return cell.getDate();
			if (type == Boolean.class)
				return cell.getBoolean();

			return cell.getString();
		} catch (ConvertException e) {
			throw new ExcelReadException(e.getMessage());
		}

	}

	@Override
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	@Override
	public void fillValue(Object object, Object value) {
		PathSetter.set(object, id, value);
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public int[] getIntEnum() {
		return intEnum;
	}

	public void setIntEnum(int[] intEnum) {
		this.intEnum = intEnum;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	private final static Map<Class<? extends ReadPropertyConvert<?>>, ReadPropertyConvert<?>> convert_cache = new HashMap<>();
	private final static Map<Class<? extends PropertyValidate>, PropertyValidate> validate_cache = new HashMap<>();

	public static List<ReadColumn> createReadColumn(Class<?> clazz, String path) {
		if (clazz == Object.class)
			return null;

		List<ReadColumn> columns = new ArrayList<>();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			
			ReadBean readBean = field.getAnnotation(ReadBean.class);
			
			if(readBean != null)
			{
				List<ReadColumn> cs = createReadColumn(field.getType(),path == null ? field.getName() : path + "." + field.getName());
				if(cs != null)
					columns.addAll(cs);
				continue;
			}
			
			ReadProperty readProp = field.getAnnotation(ReadProperty.class);

			if (readProp != null) {
				
				Class<?> type = field.getType();

				
				
				DefaultReadColumn column = new DefaultReadColumn();

				String fieldname = field.getName();

				column.setId(path == null ? fieldname : path + "." + fieldname);
				column.setType(type);
				column.setCellIndex(readProp.cellIndex());

				CellConvert convertAnno = field.getAnnotation(CellConvert.class);

				if (convertAnno != null) {

					Class<? extends ReadPropertyConvert<?>> convertType = convertAnno.convert();
					ReadPropertyConvert<?> convert = convert_cache.get(convertType);
					if (convert == null) {
						try {
							convert = convertType.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
						convert_cache.put(convertType, convert);
					}

					column.setConvert(convert);

				}

				Validate validateAnno = field.getAnnotation(Validate.class);

				if (validateAnno != null) {

					Class<? extends PropertyValidate> validateType = validateAnno.validate();
					PropertyValidate validator = validate_cache.get(validateType);
					if (validator == null) {
						try {
							validator = validateType.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
						validate_cache.put(validateType, validator);
					}

					column.setValidate(validator);

				}

				// 简易验证

				column.setNullable(readProp.nullable());

				if (readProp.maxLength() > 0)
					column.setMaxLength(readProp.maxLength());
				if (readProp.minLength() > 0)
					column.setMinLength(readProp.minLength());
				if (!"".equals(readProp.regex()))
					column.setPattern(Pattern.compile(readProp.regex()));
				if (!"".equals(readProp.min()))
					column.setMin(new BigDecimal(readProp.min()));
				if (!"".equals(readProp.max()))
					column.setMax(new BigDecimal(readProp.max()));

				column.setIntEnum(readProp.intEnum());

				columns.add(column);
			}

		}

		return columns;
	}

}
