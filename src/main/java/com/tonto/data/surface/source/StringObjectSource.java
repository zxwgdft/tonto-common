package com.tonto.data.surface.source;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonto.common.util.XmlUtil;
import com.tonto.data.core.source.ObjectDataSource;

/**
 * 以字符串形式表现的固定资源
 * <ul>
 * 	支持数据格式有：
 * 	<li>JSON</li>
 *  <li>XML</li>
 * </ul>
 * @author TontoZhou
 *
 */
public class StringObjectSource extends SourceSurface<ObjectDataSource>{

	private final Logger logger = Logger.getLogger(StringObjectSource.class);
	
	/**
	 * json 格式
	 */
	public static final int DATA_TYPE_JSON = 1;
	
	/**
	 * xml 格式
	 */
	public static final int DATA_TYPE_XML = 2;
	
	
	/**
	 * 以字符串形式保存的数据
	 */
	private String data;
	
	/**
	 * 数据保存格式类型，例如json，xml等
	 */
	private int type;
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectDataSource createDataSource() {
		
		if(type == DATA_TYPE_JSON)
		{
			ObjectMapper mapper = new ObjectMapper();
			
			try {				
				Map<String,Object> dataMap = mapper.readValue(data, Map.class);
				return new ObjectDataSource(dataMap);			
			} catch (IOException e) {				
				logger.error("转化JSON格式数据错误",e);
			}
		}
		else if(type == DATA_TYPE_XML)
		{
			
			Map<String,Object> dataMap = XmlUtil.convert2map(data);
			return new ObjectDataSource(dataMap);					
		}
		else
		{
			logger.error("没有找到对应数据类型，type："+type);
		}
		
		return null;
	}

	@Override
	public Class<ObjectDataSource> dataSourceType() {
		return ObjectDataSource.class;
	}
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
