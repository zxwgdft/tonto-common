package com.tonto.data.surface.source.service;

import org.apache.log4j.Logger;

/**
 * 默认服务资源
 * <p>通过反射生成服务类实例</p>
 * @author TontoZhou
 *
 */
public class DefaultServiceSource extends ServiceSource{

	private final Logger logger = Logger.getLogger(DefaultServiceSource.class);
	
	private BeanAcquire beanAcquire;
	
	@Override
	public BeanAcquire getBeanAcquire() {
		
		if(beanAcquire == null)
		{
			beanAcquire = new BeanAcquire(){

				@Override
				public Object getBean(String name) {
					try {
						Class<?> serviceClass = Class.forName(name);	
						
						return serviceClass.newInstance();								
						
					} catch (ClassNotFoundException e) {
						logger.error("无法找到对应的类：" + name, e);
					} catch (InstantiationException e) {
						logger.error("无法反射生成对应的类：" + name + "的实例", e);
					} catch (IllegalAccessException e) {
						logger.error("无法反射生成对应的类：" + name + "的实例", e);
					}
					
					return null;
				}
				
			};
			
		}
		
		return beanAcquire;
	}

}
