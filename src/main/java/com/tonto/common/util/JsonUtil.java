package com.tonto.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	private final static ObjectMapper objectMap = new ObjectMapper();
	
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> json2map(String jsonStr)
	{
		try {
			return objectMap.readValue(jsonStr, Map.class);
		} catch (IOException e) {
			return new HashMap<String,Object>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> json2map(InputStream input)
	{
		try {
			return objectMap.readValue(input, Map.class);
		} catch (IOException e) {
			return new HashMap<String,Object>();
		}
	}
	
	
	public static <T> T json2object(String jsonStr, Class<T> type)
	{
		try {
			return objectMap.readValue(jsonStr, type);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static <T> T json2object(InputStream input, Class<T> type)
	{
		try {
			return objectMap.readValue(input, type);
		} catch (IOException e) {
			return null;
		}
	}
	
}
