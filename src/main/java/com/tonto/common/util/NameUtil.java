package com.tonto.common.util;

public class NameUtil {
	/**
	 * 驼峰式转变为下划线式
	 * 
	 * @param name
	 * @return
	 */
	public static String hump2underline(String name) {

		char[] cs = name.toCharArray();

		char[] ncs = new char[cs.length + cs.length];

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

		return new String(ncs, 0, j);

	}

	/**
	 * 去除get方法后得到小写开头的名称
	 * 
	 * @param name
	 * @return
	 */
	public static String removeGetOrSet(String name) {

		char[] cs = name.toCharArray();

		if (cs[3] <= 90)
			cs[3] += 32;

		return new String(cs, 3, cs.length - 3);
	}

	/**
	 * 添加get后的驼峰式名称
	 * @param name
	 * @return
	 */
	public static String addGet(String name) {

		char[] cs = name.toCharArray();

		char c = cs[0];
		if (c >= 97 && c <= 122) {
			cs[0] -= 32;

			return "get" + new String(cs);
		} else {
			return "get" + name;
		}

	}
	
}
