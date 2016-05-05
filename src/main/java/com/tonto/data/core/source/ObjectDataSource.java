package com.tonto.data.core.source;

import com.tonto.data.core.DataSource;
import com.tonto.data.core.util.DataGetter;

/**
 * 对象实例数据源
 * <p>
 * 		数据源为Map，List，Array或JavaBean，
 *      根据传入一个寻路的字符串Path去匹配到相应的值
 * </p>
 * @author TontoZhou
 *
 */
public class ObjectDataSource implements DataSource{

	private Object data;
	
	public ObjectDataSource(Object data){
		if(data == null)
			throw new NullPointerException();
		this.data = data;
	}
	
	@Override
	public Object getData(Object... id) {
		if(id.length == 0)
			return data;
		
		String path=id[0].toString();
		
		return DataGetter.get(data, path);		
	}
	
	
}
