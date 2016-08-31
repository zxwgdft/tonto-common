package com.tonto2.common.validate.common;

import com.tonto2.common.template.ObjectMessageTemplate;
import com.tonto2.common.utils.StringParser;
import com.tonto2.common.validate.Validate;
import com.tonto2.common.validate.ValidateType;

public abstract class CommonValidate implements Validate {

	/**
	 * 获取验证类型
	 * 
	 * @return
	 */
	public abstract ValidateType getValidateType();

	private ValidateMessageTemplate validateMessageTemplate;
	
	/**
	 * 
	 * 设置验证错误消息，可以为模板格式，参照{@link ObjectMessageTemplate}，并且{value}代表验证参数值
	 * 
	 * @param message
	 */
	public void setValidateMessage(String message) {
		validateMessageTemplate = new ValidateMessageTemplate(message);
	}

	@Override
	public String validate(Object value, Object currentObj, Object validateObj) {
		return validateValue(value) ? null : validateMessageTemplate.createMessage(value,this);
	}

	/**
	 * 验证值，不需要判断是否为NULL
	 * 
	 * @param value
	 * @return
	 */
	public abstract boolean validateValue(Object value);

	class ValidateMessageTemplate extends ObjectMessageTemplate {

		public ValidateMessageTemplate(String template) {
			super(template);
		}

		public String createMessage(Object... args) {

			String msg = getTemplate();

			Object value = args[0];
			Object data = args[1];

			msg = msg.replaceAll("{value}", StringParser.parseString(value));
			if(mapper != null && mapper.size()>0)
				msg = create(msg, data);

			return msg;
		}

	}
}
