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
public class MaxLengthValidator extends CommonValidate {

	public int maxLength;
	public boolean inclusive = true;

	public MaxLengthValidator(int maxLength, boolean inclusive) {
		this.maxLength = maxLength;
		this.inclusive = inclusive;
		this.setValidateMessage(inclusive ? "值的长度必须不大于" + maxLength : "值的长度必须小于" + maxLength);
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Length;
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validGreatLength(maxLength, inclusive, value);
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setInclusive(boolean inclusive) {
		this.inclusive = inclusive;
	}

}
