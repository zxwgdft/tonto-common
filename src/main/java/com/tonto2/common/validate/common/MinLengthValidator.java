package com.tonto2.common.validate.common;

import com.tonto2.common.utils.validate.ValidateUtil;
import com.tonto2.common.validate.ValidateType;

/**
 * 
 * 长度范围验证
 * 
 * @author TontoZhou
 * 
 */
public class MinLengthValidator extends CommonValidate {

	public int minLength;
	public boolean inclusive = true;

	public MinLengthValidator(int minLength, boolean inclusive) {
		this.minLength = minLength;
		this.inclusive = inclusive;
		this.setValidateMessage(inclusive ? "值的长度必须不小于" + minLength : "值的长度必须大于" + minLength);
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Length;
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validLessLength(minLength, inclusive, value);
	}

	public int getMinLength() {
		return minLength;
	}

	public boolean isInclusive() {
		return inclusive;
	}



}
