package com.tonto2.common.validate;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Validator {
	
	protected Map<Class<? extends Annotation>, AnnotationValidate<?>> privateAnnValidates;

	/**
	 * 添加针对某个{@link Annotation}的验证规则
	 * 
	 * @param ann
	 * @param validate
	 */
	public <T extends Annotation> void addAnnotationValidate(Class<T> ann, AnnotationValidate<T> validate) {
		if (privateAnnValidates == null)
			privateAnnValidates = new HashMap<Class<? extends Annotation>, AnnotationValidate<?>>();
		privateAnnValidates.put(ann, validate);
	}

	protected Map<String, Validate> privateValidates;

	/**
	 * <p>
	 * 针对fieldName添加验证规则
	 * </p>
	 * <p>
	 * 对于子类中的字段的验证添加{@code fieldName}时，使用点路径表示从第一个节点类到某个子类中字段，
	 * 例如address.city表示验证字段address下的city子字段
	 * </p>
	 * 
	 * @param fieldName
	 * @param validate
	 */
	public void addValidate(String fieldName, Validate validate) {
		if (privateValidates == null)
			privateValidates = new HashMap<String, Validate>();

		privateValidates.put(fieldName, validate);
	}

	
	/**
	 * 
	 * 验证接口，如果验证成功则返回NULL，失败则返回消息队列
	 * 
	 * @param obj
	 * @return
	 */
	public abstract ValidateError validate(Object obj) ;
	

	public static class ValidateError {

		private String name; // 验证的字段名称
		private String message; // 验证信息
		private List<ValidateError> subValidateResult; // 如果验证的字段有子字段需要验证，这些子字段的验证结果
		private boolean isArray = false; // 是否为数组，集合

		public ValidateError(String name, String message) {
			this.name = name;
			this.message = message;
		}

		public ValidateError(String name, String message, List<ValidateError> subValidateResult) {
			this.name = name;
			this.message = message;
			this.subValidateResult = subValidateResult;
		}

		public ValidateError(String name, String message, List<ValidateError> subValidateResult, boolean isArray) {
			this.name = name;
			this.message = message;
			this.subValidateResult = subValidateResult;
			this.isArray = isArray;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<ValidateError> getSubValidateResult() {
			return subValidateResult;
		}

		public void setSubValidateResult(List<ValidateError> subValidateResult) {
			this.subValidateResult = subValidateResult;
		}

		public boolean isArray() {
			return isArray;
		}

		public void setArray(boolean isArray) {
			this.isArray = isArray;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (name != null)
				sb.append(name).append(":").append(message).append("\r\n");
			if (subValidateResult != null) {
				for (ValidateError error : subValidateResult)
					sb.append(error.toString());
			}
			return sb.toString();
		}
	}
}
