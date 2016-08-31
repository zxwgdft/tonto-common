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
public class MaxValidator extends CommonValidate {

	private BigDecimal max;
	public boolean inclusive = true;

	public MaxValidator(BigDecimal max, boolean inclusive) {
		this.max = max;
		this.inclusive = inclusive;
		this.setValidateMessage(inclusive ? "值必须不大于" + max : "值必须小于" + max);
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Max;
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validGreatNumber(max, inclusive, value);
	}

	public BigDecimal getMax() {
		return max;
	}

	public boolean isInclusive() {
		return inclusive;
	}

}
