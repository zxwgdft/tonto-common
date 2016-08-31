package com.tonto2.common.validate.common;

import java.util.regex.Pattern;

import com.tonto2.common.validate.ValidateType;

/**
 * 正则验证
 * 
 * @author TontoZhou
 *
 */
public class PatternValidator extends CommonValidate {

	Pattern pattern;

	public PatternValidator(Pattern pattern) {
		this.pattern = pattern;
		this.setValidateMessage("格式不正确");

	}

	public PatternValidator(String regex) {
		this(Pattern.compile(regex));
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Pattern;
	}

	@Override
	public boolean validateValue(Object value) {
		return pattern.matcher(value.toString()).matches();
	}

}
