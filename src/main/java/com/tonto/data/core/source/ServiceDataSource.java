package com.tonto.data.core.source;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.tonto.data.core.DataSource;
import com.tonto.data.core.util.DataGetter;
import com.tonto.data.core.util.ReflectUtil;

/**
 * 服务数据
 * <p>通过注入的服务类和对应的方法名，去获取具体数据</p>
 * 
 * @author TontoZhou
 * 
 */
public class ServiceDataSource implements DataSource {

	/**
	 * 服务类
	 */
	private Object service;

	/**
	 * 服务方法名称
	 */
	private String methodName;

	/**
	 * 服务方法，用于缓存，如果有重载则会重新从类中查找替换
	 */
	private Method method;
	
	/**
	 * 获取数据后属性寻路地址
	 */
	private String path;

	public ServiceDataSource(Object service, String method) {

		this(service,method,null);
	}
	
	public ServiceDataSource(Object service, String method, String path) {

		if (service == null || method == null)
			throw new NullPointerException();

		this.service = service;

		this.methodName = method;
		
		this.path = path;
	}

	@Override
	public Object getData(Object... id) {

		Class<?> clazz = service.getClass();

		boolean isGot = false;

		if (method == null) {
			try {
				
				method = ReflectUtil.getMethodByParam(clazz, methodName, id);
				
				isGot = true;
				
			} catch (NoSuchMethodException | SecurityException e) {
				
				e.printStackTrace();
				return null;
			}

		}

		try {

			Object data = method.invoke(service, id);

			if(path!=null)
				return DataGetter.get(data, path);
			
			return data;
			
		} catch (IllegalArgumentException e) {

			if (!isGot) {
				try {

					method = ReflectUtil
							.getMethodByParam(clazz, methodName, id);
					
					Object data = method.invoke(service, id);
					
					if(path!=null)
						return DataGetter.get(data, path);
					
					return data;

				} catch (NoSuchMethodException | SecurityException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}

		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

}
