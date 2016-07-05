package com.tonto.common.util;

import java.lang.reflect.Field;

public class SQLUtil {

	public String getSelectSQL(Class<?> clazz, String head) {
		
		if (head != null && !"".equals(head) && !head.endsWith("."))
			head += ".";

		Field[] fields = clazz.getDeclaredFields();

		StringBuilder sb = new StringBuilder();

		for (Field field : fields) {
			String name = field.getName();

			char[] cs = name.toCharArray();

			char[] ncs = new char[cs.length + 10];

			int j = 0;
			for (int i = 0; i < cs.length; i++) {
				char c = cs[i];
				if (c >= 65 && c <= 90) {
					ncs[j++] = 95;
					ncs[j++] = (char) (c + 32);
				} else {
					ncs[j++] = c;
				}
			}

			String toName = new String(ncs, 0, j);

			if (j > cs.length)
				sb.append(head).append(toName).append(" AS ").append(name).append(",\n");
			else
				sb.append(head).append(name).append(",\n");

		}

		return sb.toString();

	}

}
