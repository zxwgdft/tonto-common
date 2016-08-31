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
public class LengthValidator extends CommonValidate {

	public int minLength;
	public int maxLength;
	public boolean inclusive = true;

	public LengthValidator(int minLength, int maxLength, boolean inclusive) {
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.inclusive = inclusive;
		
		this.setValidateMessage("值的长度必须在" + minLength + "和" + maxLength + "之间");
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Length;
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validLengthRange(maxLength, minLength, inclusive, value);
	}

	public int getMinLength() {
		return minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public boolean isInclusive() {
		return inclusive;
	}

	
}
