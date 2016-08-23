package com.tonto2.common.utils.reflect;

public class NameUtil {

	/**
	 * 驼峰式转变为下划线式
	 * 
	 * <p>
	 * 例如userName ==> user_name
	 * </p>
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
	 * <p>
	 * 例如getName ==> name
	 * </p>
	 * 
	 * @param name
	 * @return
	 */
	public static String removeGetOrSet(String name) {
		return removeGetOrSet(name, true);
	}

	/**
	 * 去除get方法后得到小写开头的名称
	 * 
	 * <p>
	 * 例如getName ==> name 或者 Name
	 * </p>
	 * 
	 * @param name
	 * @param firstLowerCase
	 *            是否手指拇小写
	 * @return
	 */
	public static String removeGetOrSet(String name, boolean firstLowerCase) {

		char[] cs = name.toCharArray();

		if (firstLowerCase && cs[3] <= 90)
			cs[3] += 32;

		return new String(cs, 3, cs.length - 3);
	}

	/**
	 * 添加get后的驼峰式名称
	 * <p>
	 * 例如name ==> getName
	 * </p>
	 * @param name
	 * @return
	 */
	public static String addGet(String name) {
		return addPrefix("get",name,true);
	}

	/**
	 * 添加set后的驼峰式名称
	 * <p>
	 * 例如name ==> setName
	 * </p>
	 * @param name
	 * @return
	 */
	public static String addSet(String name) {
		return addPrefix("set",name,true);
	}

	/**
	 * 
	 * 添加前缀后的驼峰式名称
	 * 
	 * <p>
	 * 例如name ==> getName
	 * </p>
	 * 
	 * @param prefix
	 * @param firstUpperCase
	 * @return
	 */
	public static String addPrefix(String prefix, String name, boolean firstUpperCase) {

		if (firstUpperCase) {
			char[] cs = name.toCharArray();

			char c = cs[0];
			if (c >= 97 && c <= 122) {
				cs[0] -= 32;

				return prefix + new String(cs);
			}
		}

		return prefix + name;
	}

}
