package com.tonto2.common.validate;

/**
 * 
 * 常用验证类型
 * 
 * @author TontoZhou
 *
 */
public enum ValidateType {
	
	Cellphone,
	Digit,
	Email,
	IntegerEnum,
	Length,
	Max,
	Min,
	MinLength,
	MaxLength,
	NotNull,
	Pattern;
	
	public static ValidateType getTypeByName(String name){
		return Enum.valueOf(ValidateType.class, name);		
	}
	
}
