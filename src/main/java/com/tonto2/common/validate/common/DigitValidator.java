package com.tonto2.common.validate.common;

import java.math.BigDecimal;

import com.tonto2.common.utils.validate.ValidateUtil;
import com.tonto2.common.validate.ValidateType;

/**
 * 
 * 数字范围验证
 * 
 * @author TontoZhou
 * 
 */
public class DigitValidator extends CommonValidate {

	private BigDecimal min;
	private BigDecimal max;
	public boolean inclusive = true;

	public DigitValidator(BigDecimal min, BigDecimal max, boolean inclusive) {
		
		this.min = min;
		this.max = max;
		this.inclusive = inclusive;
		
		this.setValidateMessage("值的必须在" + min + "和" + max + "之间");
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.Digit;
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validDigitRange(max, min, inclusive, value);
	}

	public BigDecimal getMin() {
		return min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public boolean isInclusive() {
		return inclusive;
	}

}
