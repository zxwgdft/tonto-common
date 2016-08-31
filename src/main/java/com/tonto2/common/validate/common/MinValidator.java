package com.tonto2.common.validate.common;

import java.math.BigDecimal;

import com.tonto2.common.utils.validate.ValidateUtil;
import com.tonto2.common.validate.ValidateType;

/**
 * 
 * 最大于值判断
 * 
 * @author TontoZhou
 * 
 */
public class MinValidator extends CommonValidate {

	private BigDecimal min;
	public boolean inclusive = true;

	public MinValidator(BigDecimal min, boolean inclusive) {
		this.min = min;
		this.inclusive = inclusive;
		this.setValidateMessage(inclusive ? "值必须不小于" + min : "值必须大于" + min);
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Max;
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validLessNumber(min, inclusive, value);
	}

}
