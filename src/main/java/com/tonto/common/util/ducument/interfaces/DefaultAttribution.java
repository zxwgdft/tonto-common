package com.tonto.common.util.ducument.interfaces;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tonto.common.util.NameUtil;

/**
 * 默认属性类
 * 
 * @author TontoZhou
 * 
 */
public class DefaultAttribution implements Attribution {

	private static Set<Class<?>> baseClass;
	private static Map<Class<?>, Class<?>> primary2baseMap;

	static {
		baseClass = new HashSet<>();

		baseClass.add(Integer.class);
		baseClass.add(Float.class);
		baseClass.add(Double.class);
		baseClass.add(Long.class);
		baseClass.add(Short.class);
		baseClass.add(Character.class);
		baseClass.add(Boolean.class);
		baseClass.add(String.class);
		baseClass.add(Date.class);
		baseClass.add(Byte.class);
		baseClass.add(BigDecimal.class);

		primary2baseMap = new HashMap<>();

		primary2baseMap.put(int.class, Integer.class);
		primary2baseMap.put(float.class, Float.class);
		primary2baseMap.put(double.class, Double.class);
		primary2baseMap.put(long.class, Long.class);
		primary2baseMap.put(char.class, Character.class);
		primary2baseMap.put(byte.class, Byte.class);
		primary2baseMap.put(boolean.class, Boolean.class);
		primary2baseMap.put(short.class, Integer.class);
	}

	// 姓名
	private String name;
	// 类型
	private Class<?> type;
	// 是否列表
	private boolean isList = false;
	// 是否必填
	private boolean isMandatory = true;
	// 描述
	private String description;
	// 定义
	private String definition;
	// 如果是Object的话，对应的属性列表
	private List<Attribution> attributions;

	public DefaultAttribution() {

	}

	public DefaultAttribution(String name, Class<?> type, boolean isMandatory, String description, String definition) {
		setName(name);
		setType(type);
		setDescription(description);
		setDefinition(definition);
		setIsMandatory(isMandatory);
	}

	@Override
	public Boolean getIsObject() {
		return !(type == null || type.isPrimitive() || baseClass.contains(type));
	}

	@Override
	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type != null ? type.getSimpleName() : "";
	}

	@Override
	public String getIsMandatory() {
		return isMandatory ? "Y" : "N";
	}

	@Override
	public String getDescription() {
		return description == null ? "" : description;
	}

	@Override
	public String getDefinition() {
		return definition == null ? "" : definition;
	}

	@Override
	public List<Attribution> getAttributions() {
		return attributions;
	}

	@Override
	public String getId() {
		String id = type.getName();

		if (id.length() >= 39) {
			id = id.substring(id.length() - 39);
		}

		return "_" + id;
	}

	public void setAttributions(List<Attribution> attributions) {
		this.attributions = attributions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Class<?> type) {
		if (type.isPrimitive()) {
			type = primary2baseMap.get(type);
		}

		this.type = type;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * 创建一个对象清单
	 * @param clazz can't be collection
	 * @return
	 */
	public static Attribution createObject(Class<?> clazz) {

		DefaultAttribution attribution = new DefaultAttribution();

		String name = clazz.getSimpleName();

		attribution.setName(name);
		attribution.setType(clazz);

		attribution.setAttributions(createAttributions(clazz, null));

		return attribution;
	}

	/**
	 * 创建一个对象清单
	 * @param clazz can't be collection
	 * @param descriptionAndDefinition 说明和描述的Map
	 * @return
	 */
	public static Attribution createObject(Class<?> clazz, Map<Class<?>, Map<String, String[]>> descriptionAndDefinition) {

		DefaultAttribution attribution = new DefaultAttribution();

		String name = clazz.getSimpleName();

		attribution.setName(name);
		attribution.setType(clazz);

		attribution.setAttributions(createAttributions(clazz, descriptionAndDefinition));

		return attribution;
	}

	/**
	 * 
	 * @param clazz
	 * @param descriptionAndDefinition
	 * @return
	 */
	private static List<Attribution> createAttributions(Class<?> clazz, Map<Class<?>, Map<String, String[]>> descriptionAndDefinition) {

		Method[] methods = clazz.getMethods();

		List<Attribution> attributions = new ArrayList<>();

		for (Method method : methods) {
			String mName = method.getName();

			if (mName.startsWith("get") && !"getClass".equals(mName) && mName.length() > 3 && method.getParameterTypes().length == 0
					&& method.getReturnType() != void.class) {

				DefaultAttribution attribution = new DefaultAttribution();
				attributions.add(attribution);

				String name = NameUtil.removeGetOrSet(mName);
				Class<?> type = method.getReturnType();
				boolean isList = Collection.class.isAssignableFrom(type);
				if (isList)
					type = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

				attribution.setName(name);
				attribution.setIsList(isList);
				attribution.setType(type);

				if (!type.isInterface() && !type.isPrimitive() && !baseClass.contains(type)) {
					attribution.setAttributions(createAttributions(type, descriptionAndDefinition));
				}

				if (descriptionAndDefinition != null) {
					Map<String, String[]> infos = descriptionAndDefinition.get(clazz);
					if (infos != null) {
						String[] descs = infos.get(type.getName());
						if (descs != null) {
							if (descs.length > 1) {
								attribution.setDescription(descs[0]);
								attribution.setDescription(descs[1]);
							}
						}
					}
				}

			}
		}

		return attributions;
	}

}
