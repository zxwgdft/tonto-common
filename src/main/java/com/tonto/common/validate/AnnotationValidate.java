package com.tonto.common.validate;

import java.lang.annotation.Annotation;

public interface AnnotationValidate<T extends Annotation> {
	public boolean validate(T annotation,Object value);
}
