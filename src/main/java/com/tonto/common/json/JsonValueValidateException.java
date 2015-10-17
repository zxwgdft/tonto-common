package com.tonto.common.json;

import com.tonto.common.validate.FieldValidator.ValidateResult;

public class JsonValueValidateException extends Exception{

	private static final long serialVersionUID = -1917840133738502694L;
	
	private ValidateResult errorResult;
	
	public JsonValueValidateException(ValidateResult errorResult)
	{
		super();
		this.errorResult=errorResult;
	}

	public ValidateResult getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(ValidateResult errorResult) {
		this.errorResult = errorResult;
	}

	
}
