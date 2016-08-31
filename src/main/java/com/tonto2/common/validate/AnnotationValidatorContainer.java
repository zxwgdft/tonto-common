package com.tonto2.common.validate;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.tonto2.common.utils.validate.ValidateUtil;
import com.tonto2.common.validate.annotation.Cellphone;
import com.tonto2.common.validate.annotation.Digit;
import com.tonto2.common.validate.annotation.Email;
import com.tonto2.common.validate.annotation.IntegerEnum;
import com.tonto2.common.validate.annotation.Length;
import com.tonto2.common.validate.annotation.Max;
import com.tonto2.common.validate.annotation.MaxLength;
import com.tonto2.common.validate.annotation.Min;
import com.tonto2.common.validate.annotation.MinLength;
import com.tonto2.common.validate.annotation.NotNull;
import com.tonto2.common.validate.annotation.Pattern;

public class AnnotationValidatorContainer {

	// 常用注解验证
	private final static Map<Class<? extends Annotation>, AnnotationValidate<?>> annotationValidators;

	static {

		annotationValidators = new HashMap<>();

		/*
		 * 创建默认验证规则
		 */

		annotationValidators.put(Cellphone.class, new AnnotationValidate<Cellphone>() {

			@Override
			public boolean validate(Cellphone annotation, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validCellphone(value.toString());
			}

		});

		annotationValidators.put(Email.class, new AnnotationValidate<Email>() {

			@Override
			public boolean validate(Email annotation, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validEmail(value.toString());
			}

		});

		annotationValidators.put(IntegerEnum.class, new AnnotationValidate<IntegerEnum>() {

			@Override
			public boolean validate(IntegerEnum annotation, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validContainInt(value, annotation.value());
			}

		});

		annotationValidators.put(Max.class, new AnnotationValidate<Max>() {

			@Override
			public boolean validate(Max annotation, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validGreatNumber(new BigDecimal(annotation.value()), annotation
						.inclusive(), value);
			}

		});

		annotationValidators.put(Min.class, new AnnotationValidate<Min>() {

			@Override
			public boolean validate(Min t, Object value, Object validateObj) {

				return value == null ? false : ValidateUtil.validLessNumber(new BigDecimal(t.value()), t.inclusive(), value);
			}

		});

		annotationValidators.put(Digit.class, new AnnotationValidate<Digit>() {

			@Override
			public boolean validate(Digit t, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validDigitRange(new BigDecimal(t.max()), new BigDecimal(t.min()), t
						.inclusive(), value);
			}

		});

		annotationValidators.put(Length.class, new AnnotationValidate<Length>() {
			@Override
			public boolean validate(Length t, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validLengthRange(t.maxLength(), t.minLength(), t.inclusive(), value);
			}
		});

		annotationValidators.put(MaxLength.class, new AnnotationValidate<MaxLength>() {

			@Override
			public boolean validate(MaxLength t, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validGreatLength(t.value(), t.inclusive(), value);
			}

		});

		annotationValidators.put(MinLength.class, new AnnotationValidate<MinLength>() {

			@Override
			public boolean validate(MinLength t, Object value, Object validateObj) {
				return value == null ? false : ValidateUtil.validLessLength(t.value(), t.inclusive(), value);
			}

		});

		annotationValidators.put(NotNull.class, new AnnotationValidate<NotNull>() {

			@Override
			public boolean validate(NotNull t, Object value, Object validateObj) {
				return value != null;
			}

		});

		annotationValidators.put(Pattern.class, new AnnotationValidate<Pattern>() {

			@Override
			public boolean validate(Pattern t, Object value, Object validateObj) {
				if (value == null)
					return false;
				String valStr = value.toString();
				return valStr == null ? false : valStr.matches(t.value());
			}
		});
			
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> AnnotationValidate<T> getAnnotationValidateByType(Class<T> clazz) {
		return (AnnotationValidate<T>) annotationValidators.get(clazz);
	}
	
	public static <T extends Annotation> void addValidate(Class<T> clazz, AnnotationValidate<T> validator) {
		annotationValidators.put(clazz, validator);
	}
}
