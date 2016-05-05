package com.tonto.data.surface.source.service;

import com.tonto.data.core.source.ServiceDataSource;
import com.tonto.data.surface.source.SourceSurface;

/**
 * 服务资源
 * @author TontoZhou
 *
 */
public abstract class ServiceSource extends SourceSurface<ServiceDataSource>{
	
	
	/**
	 * 实例获取方式 
	 */
	protected int acquireType;
	
	/**
	 * 获取实例类名称
	 */
	protected String className;
	
	/**
	 * 服务方法
	 */
	protected String serviceMethod;

	
	/**
	 * 获取相应获取Bean的方式
	 * @return
	 */
	public abstract BeanAcquire getBeanAcquire();
	
	@Override
	public ServiceDataSource createDataSource() {
			
		BeanAcquire beanAcquire = getBeanAcquire();
		
		if(beanAcquire != null)
		{
			Object service = beanAcquire.getBean(className);
			return new ServiceDataSource(service, serviceMethod);					
		}	
		
		return null;
	}

	@Override
	public Class<ServiceDataSource> dataSourceType() {
		return ServiceDataSource.class;
	}

	public int getAcquireType() {
		return acquireType;
	}

	public void setAcquireType(int acquireType) {
		this.acquireType = acquireType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}


}
