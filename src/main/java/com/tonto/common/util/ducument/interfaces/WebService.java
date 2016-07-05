package com.tonto.common.util.ducument.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * web接口描述
 * 
 * @author TontoZhou
 * 
 */
public class WebService {

	// 接口名称
	private String name;

	// 需要授权
	private Boolean needAuthorize = true;

	// 授权方式
	private String authorizeType = "头部带token";

	// 接口签名
	private String interfaceSign = "";

	// 请求URL
	private String url;

	// 请求方法
	private String method;

	// 请求类型(GET/POST...)
	private String requestType;

	// 请求属性
	private List<Attribution> systemRequestAttributions;

	// 请求属性
	private List<Attribution> serviceRequestAttributions;

	// 正常响应属性
	private List<Attribution> normalResponseAttributions;

	// 异常响应属性
	private List<Attribution> errorResponseAttributions;

	// 响应对象属性
	private List<Attribution> objectAttributions;

	public WebService() {

		systemRequestAttributions = default_system_request_attr;
		normalResponseAttributions = new ArrayList<>(default_normal_response_attr);
		errorResponseAttributions = default_error_response_attr;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNeedAuthorize() {
		return needAuthorize;
	}

	public void setNeedAuthorize(Boolean needAuthorize) {
		this.needAuthorize = needAuthorize;
	}

	public String getAuthorizeType() {
		return authorizeType;
	}

	public void setAuthorizeType(String authorizeType) {
		this.authorizeType = authorizeType;
	}

	public String getInterfaceSign() {
		return interfaceSign;
	}

	public void setInterfaceSign(String interfaceSign) {
		this.interfaceSign = interfaceSign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public List<Attribution> getSystemRequestAttributions() {
		return systemRequestAttributions;
	}

	public void setSystemRequestAttributions(List<Attribution> systemRequestAttributions) {
		this.systemRequestAttributions = systemRequestAttributions;
	}

	public List<Attribution> getServiceRequestAttributions() {
		return serviceRequestAttributions;
	}

	public void setServiceRequestAttributions(List<Attribution> serviceRequestAttributions) {
		this.serviceRequestAttributions = serviceRequestAttributions;
	}

	public List<Attribution> getNormalResponseAttributions() {
		return normalResponseAttributions;
	}

	public void setNormalResponseAttributions(List<Attribution> normalResponseAttributions) {
		this.normalResponseAttributions = normalResponseAttributions;
	}

	public List<Attribution> getErrorResponseAttributions() {
		return errorResponseAttributions;
	}

	public void setErrorResponseAttributions(List<Attribution> errorResponseAttributions) {
		this.errorResponseAttributions = errorResponseAttributions;
	}

	public List<Attribution> getObjectAttributions() {
		return objectAttributions;
	}

	public void setObjectAttributions(List<Attribution> objectAttributions) {
		this.objectAttributions = objectAttributions;
	}

	/**
	 * 设置响应对象清单对应的类
	 * 
	 * @param clazz
	 */
	public void setObjectListClass(Class<?> clazz, boolean isList) {

		Attribution object = DefaultAttribution.createObject(clazz);
		List<Attribution> objects = new ArrayList<>();

		// 获取对象列表
		toList(object, objects);

		setObjectAttributions(objects);

		if (normalResponseAttributions != null) {
			Iterator<Attribution> iter = normalResponseAttributions.iterator();
			while (iter.hasNext()) {
				Attribution attr = iter.next();
				if ("result".equals(attr.getName()) || "list".equals(attr.getName())) {
					iter.remove();
				}
			}
		} else {
			normalResponseAttributions = new ArrayList<>();
		}

		DefaultAttribution attr = new DefaultAttribution(isList?"list":"result", clazz, true, "", "");
		attr.setIsList(isList);
		normalResponseAttributions.add(attr);
	}

	/**
	 * 获取到所有对象，组成列表
	 * 
	 * @param attribution
	 * @param attributions
	 */
	private void toList(Attribution attribution, List<Attribution> attributions) {

		if (attribution == null)
			return;

		List<Attribution> as = attribution.getAttributions();
		if (as != null) {
			String type = attribution.getType();
			boolean has = false;
			for (Attribution aa : attributions) {
				if (type.equals(aa.getType())) {
					has = true;
					break;
				}
			}

			if (!has)
				attributions.add(attribution);

			for (Attribution attr : as)
				toList(attr, attributions);
		}
	}

	private final static List<Attribution> default_system_request_attr;

	private final static List<Attribution> default_normal_response_attr;

	private final static List<Attribution> default_error_response_attr;

	static {

		default_system_request_attr = new ArrayList<>();
		default_system_request_attr.add(new DefaultAttribution("method", String.class, true, "API接口名称", ""));
		default_system_request_attr.add(new DefaultAttribution("v", String.class, true, "API协议版本", ""));
		default_system_request_attr.add(new DefaultAttribution("session", String.class, true, "系统分配的sessionKey，通过登录授权获取", "xml/json"));
		default_system_request_attr.add(new DefaultAttribution("format", String.class, true, "响应格式", ""));
		default_system_request_attr.add(new DefaultAttribution("appKey", String.class, true, "系统指定的appkey", ""));

		default_normal_response_attr = new ArrayList<>();
		default_normal_response_attr.add(new DefaultAttribution("status", String.class, true, "系统状态码", "S-Success"));
		default_normal_response_attr.add(new DefaultAttribution("message", String.class, true, "返回消息", ""));

		default_error_response_attr = new ArrayList<>();
		default_error_response_attr.add(new DefaultAttribution("status", String.class, true, "系统状态码", "F-Failure"));
		default_error_response_attr.add(new DefaultAttribution("code", String.class, true, "主错误代码", ""));
		default_error_response_attr.add(new DefaultAttribution("solution", String.class, true, "解决方案", ""));
		default_error_response_attr.add(new DefaultAttribution("message", String.class, true, "主错误消息", ""));

	}
}
