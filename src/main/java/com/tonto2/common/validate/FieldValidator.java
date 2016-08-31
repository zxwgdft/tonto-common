package com.tonto2.common.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tonto2.common.template.ObjectMessageTemplate;
import com.tonto2.common.utils.reflect.NameUtil;
import com.tonto2.common.validate.annotation.NotNull;

/**
 * 
 * 属性验证器
 * @author TontoZhou
 *
 */
public class FieldValidator extends Validator{

	private final static Logger logger = Logger.getLogger(FieldValidator.class);


	/**
	 * 
	 * @param field
	 * @param value
	 * @param path
	 *            字段路径
	 * @param currentObj
	 *            当前验证的类
	 * @param validateObj
	 *            完整的需要验证的实例
	 * @return 如果有错误则返回{@link ValidateError},没有则返回null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ValidateError validate(Field field, Object value, String path, Object currentObj, Object validateObj) {

		String fieldName = field.getName();
		if (path != null)
			fieldName = path + "." + fieldName;

		if (privateValidates != null) {
			String str = fieldName.replaceAll("\\[\\d+\\]", "");
			Validate validate = privateValidates.get(str);
			if (validate != null) {
				String errorMsg = validate.validate(value, currentObj, validateObj);
				if (errorMsg != null)
					return new ValidateError(fieldName, errorMsg);
			}
		}

		if (value == null) {
			NotNull notnull = field.getAnnotation(NotNull.class);
			if (notnull == null)
				return null;
			else
				return new ValidateError(fieldName, getErrorMessage(notnull));
		}

		Annotation[] anns = field.getDeclaredAnnotations();
		for (Annotation ann : anns) {
			AnnotationValidate annValidate = null;
			if (privateAnnValidates != null)
				annValidate = privateAnnValidates.get(ann.annotationType());
			if (annValidate == null)
				annValidate = AnnotationValidatorContainer.getAnnotationValidateByType(ann.annotationType());
			if (annValidate != null) {
				boolean result = annValidate.validate(ann, value, validateObj);
				if (!result)
					return new ValidateError(fieldName, getErrorMessage(ann));
			}
		}
		
		
		

		return null;
	}

	public ValidateError validate(Object obj) {
		return validate(obj, null, obj);
	}

	@SuppressWarnings("rawtypes")
	public ValidateError validate(Object obj, String path, Object validateObj) {

		Class<?> clazz = obj.getClass();

		List<ValidateError> errors = new ArrayList<ValidateError>();

		boolean isArray = false;

		if (clazz.isArray()) {
			int length = Array.getLength(obj);

			for (int i = 0; i < length; i++) {
				String subPath = path == null ? "[" + i + "]" : path + "[" + i + "]";
				Object subObj = Array.get(obj, i);
				ValidateError error = validate(subObj, subPath, validateObj);
				if (error != null)
					errors.add(error);
			}

			isArray = true;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			Collection coll = (Collection) obj;
			int i = 0;
			for (Object subObj : coll) {
				String subPath = path == null ? "[" + (i++) + "]" : path + "[" + (i++) + "]";
				ValidateError error = validate(subObj, subPath, validateObj);
				if (error != null)
					errors.add(error);
			}

			isArray = true;
		} else {
			if (isBaseClass(clazz))
				return null;

			List<Class<?>> classes = new ArrayList<Class<?>>();

			Class<?> superClass = clazz;

			do {
				classes.add(superClass);
				superClass = superClass.getSuperclass();
			} while (superClass != Object.class && superClass != null);

			for (Class<?> cla : classes) {
				Field[] fields = cla.getDeclaredFields();

				for (Field field : fields) {

					Class<?> fieldClass = field.getType();

					if (Modifier.isStatic(field.getModifiers()))
						continue;

					String name = field.getName();

					try {
						Method getMethod = clazz.getMethod(NameUtil.addGet(name));
						Object val = getMethod.invoke(obj);

						ValidateError error = validate(field, val, path, obj, validateObj);
						if (error != null)
							errors.add(error);

						if (!isBaseClass(fieldClass)) {
							String subPath;
							if (path == null)
								subPath = name;
							else
								subPath = path + "." + name;

							error = validate(val, subPath, validateObj);
							if (error != null)
								errors.add(error);
						}

					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {

					}
				}

			}
		}

		if (errors.size() > 0)
			return new ValidateError(path, "验证失败", errors, isArray);
		return null;
	}

	/**
	 * 是否基础类型
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isBaseClass(Class<?> clazz) {
		return clazz == Object.class || clazz.isEnum() || Number.class.isAssignableFrom(clazz) || clazz == Date.class
				|| clazz == String.class;

	}

	//注释模板缓存
	private final static Map<Class<? extends Annotation>, ObjectMessageTemplate> error_message_cache = new HashMap<>();

	/**
	 * 根据{@link Annotation}获取错误消息模板并得到具体消息
	 * 
	 * @param annotation
	 * @return
	 */
	public String getErrorMessage(Annotation annotation) {

		Class<? extends Annotation> clazz = annotation.annotationType();

		ObjectMessageTemplate template = error_message_cache.get(clazz);
		if (template == null) {
			Method method;
			try {
				method = clazz.getMethod("message");
				Object message = method.invoke(annotation);
				if (message instanceof String) {
					template = new ObjectMessageTemplate((String) message);
					error_message_cache.put(clazz, template);
				} else {
					if (logger.isDebugEnabled())
						logger.debug("This annotation has not function of message() for validate");
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {

			}
		}

		return template == null ? "验证失败" : template.createMessage(annotation);

	}

	
}
