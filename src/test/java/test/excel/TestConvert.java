package test.excel;

import com.tonto.common.base.annotation.PropertyConvert;

public class TestConvert implements PropertyConvert<Integer>{

	@Override
	public Integer convert(Object obj) {			
		return 6666;
	}
	
}
