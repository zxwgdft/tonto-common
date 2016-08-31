package com.tonto2.common.validate;

import java.lang.annotation.Annotation;

/**
 * 针对{@link Annotation}的验证规则
 * @author TontoZhou
 *
 */
public interface AnnotationValidate<T extends Annotation> {
	/**
	 * 
	 * @param annotation
	 * @param value
	 * @param validateObj
	 * @return
	 */
	public boolean validate(T annotation,Object value,Object validateObj);
}
