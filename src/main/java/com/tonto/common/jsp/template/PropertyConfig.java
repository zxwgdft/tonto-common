package com.tonto.common.jsp.template;

public class PropertyConfig {
	private String filedName;
	private String showName;
	
	private boolean isPrimary=false;
	
	private String placeholder;
	private boolean isRequired=false;
	
	
	private String idPrefix="";
	private String idSuffix="";
	
	private boolean jstlValue=false;
	private String defaultValue="";
	
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public boolean isPrimary() {
		return isPrimary;
	}
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public String getPlaceholder() {		
		return placeholder==null?"请输入"+showName:placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public String getIdPrefix() {
		return idPrefix;
	}
	public void setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
	}
	public String getIdSuffix() {
		return idSuffix;
	}
	public void setIdSuffix(String idSuffix) {
		this.idSuffix = idSuffix;
	}
	public boolean isJstlValue() {
		return jstlValue;
	}
	public void setJstlValue(boolean jstlValue) {
		this.jstlValue = jstlValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
