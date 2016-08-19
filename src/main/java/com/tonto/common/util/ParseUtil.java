package com.tonto.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * 转化值工具类
 * 
 * @author TontoZhou
 * 
 */
public class ParseUtil {

	private static final Logger logger = Logger.getLogger(ParseUtil.class);

	private static final SimpleDateFormat default_date_format = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat default_date_time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final Map<String, Class<?>> primitives = new HashMap<>(8);

	static {
		primitives.put("byte", Byte.class);
		primitives.put("char", Character.class);
		primitives.put("double", Double.class);
		primitives.put("float", Float.class);
		primitives.put("int", Integer.class);
		primitives.put("long", Long.class);
		primitives.put("short", Short.class);
		primitives.put("boolean", Boolean.class);
	}

	/**
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static Object parseString(String str, Class<?> type) {
		
		if (str == null || type == null)
			return null;
		
		if (type == String.class) {
			return str;
		}
		
		if("".equals(str))
			return null;
	
		if (type == Date.class) {

			try {
				long time = Long.parseLong(str);
				return new Date(time);
			} catch (Exception e) {
			}

			try {
				return default_date_time_format.parse(str);
			} catch (ParseException e) {
				try {
					return default_date_format.parse(str);
				} catch (ParseException e1) {
					return null;
				}
			}
		}

		
		try {
			
			Class<?> newType = primitives.get(type.getSimpleName());
			
			if (newType != null) {
				type = newType;
			}

			if (type == Double.class) {
				return Double.parseDouble(str);
			} else if (type == Integer.class) {
				return Integer.parseInt(str);
			} else if (type == Long.class) {
				return Long.parseLong(str);
			} else if (type == Boolean.class) {
				return Boolean.parseBoolean(str);
			} else if (type == Short.class) {
				return Short.parseShort(str);
			} else if (type == Float.class) {
				return Float.parseFloat(str);
			} else if (type == Character.class) {
				return str.charAt(0);
			} else if (type == BigDecimal.class) {
				return new BigDecimal(str);
			}

		} catch (Exception e) {

			if (logger.isDebugEnabled()) {
				logger.error(str + "==>" + type + "----转化失败", e);
			}

			return null;
		}

		return null;
	}
	
	public static String parse2string(Object value) {
		
		if(value == null)
			return null;
		
		if(value instanceof Date)
		{
			return default_date_time_format.format((Date)value);
		}
		
		return value.toString();
		
	}
	

	public static String parseBase64(InputStream input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
		byte[] cache = new byte[2048];
		int nRead = 0;
		while ((nRead = input.read(cache)) != -1) {
			out.write(cache, 0, nRead);
			out.flush();
		}
		out.close();

		return Base64.encodeBase64String(out.toByteArray());
	}
}
