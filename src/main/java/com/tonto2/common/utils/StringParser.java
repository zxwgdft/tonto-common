package com.tonto2.common.utils;

import java.lang.reflect.Array;

public class StringParser {

	public static String parseString(Object obj) {
		if (obj == null)
			return "null";

		if (obj.getClass().isArray()) {
			int size = Array.getLength(obj);
			StringBuilder sb = new StringBuilder("[");
			for (int i = 0; i < size; i++)
				sb.append(parseString(Array.get(obj, i))).append(",");
			return sb.append("]").toString();
		}
		
		return obj.toString();
		
	}

}
