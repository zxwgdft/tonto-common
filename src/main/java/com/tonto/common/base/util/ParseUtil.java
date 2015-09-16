package com.tonto.common.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.tonto.common.base.annotation.PropertyType;

public class ParseUtil {
	
	private static final SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateformat4 = new SimpleDateFormat("yyyy.MM.dd");
	/**
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static Object parseString(String str,PropertyType type)
	{
		if(str==null)
			return null;
		
		if(type==PropertyType.STRING)
		{
			return str;
		}
		else if(type==PropertyType.DATE)
		{
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
							return null;
						}
					}
				}
			}
		}		
		else if(type==PropertyType.DOUBLE)
		{
			return Double.parseDouble(str);
		}
		else if(type==PropertyType.INTEGER)
		{
			return Integer.parseInt(str);
		}
		else if(type==PropertyType.LONG)
		{
			return Long.parseLong(str);
		}
		else if(type==PropertyType.BOOLEAN)
		{
			if("Y".equalsIgnoreCase(str)||"true".equalsIgnoreCase(str))
				return true;
			if("N".equalsIgnoreCase(str)||"false".equalsIgnoreCase(str))
				return false;
			return null;
		}
		
		return null;
	}
}
