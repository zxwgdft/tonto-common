package com.tonto.common.template.annotation;

/**
 * 模板类型
 * @author TontoZhou
 *
 */
public enum TemplateType {
	/**模板即为内容，见{@link com.tonto.common.template.DefaultMessageTemplate}*/
	DEFAULT,
	/**简单模板，见{@link com.tonto.common.template.SimpleMessageTemplate}*/
	SIMPLE_FORMAT,
	/**动态模板，见{@link com.tonto.common.template.ObjectMessageTemplate}*/
	OBJECT_MAPPER,
}
