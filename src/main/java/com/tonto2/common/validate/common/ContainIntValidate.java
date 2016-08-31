package com.tonto2.common.validate.common;

import com.tonto2.common.utils.StringParser;
import com.tonto2.common.utils.validate.ValidateUtil;
import com.tonto2.common.validate.ValidateType;

/**
 * 
 * 指定数组验证
 * 
 * @author TontoZhou
 * 
 */
public class ContainIntValidate extends CommonValidate {

	private int[] intArray = {};
	
	public ContainIntValidate(int intArray[]) {
		this.intArray = intArray;		
		this.setValidateMessage("值必须在"+StringParser.parseString(intArray)+"之中");
	}

	@Override
	public boolean validateValue(Object value) {
		return ValidateUtil.validContainInt(value, intArray);
	}

	@Override
	public ValidateType getValidateType() {
		return ValidateType.IntegerEnum;
	}

	public int[] getIntArray() {
		return intArray;
	}

}
