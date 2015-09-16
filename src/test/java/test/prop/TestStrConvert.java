package test.prop;

import com.tonto.common.base.annotation.PropertyConvert;

public class TestStrConvert implements PropertyConvert<Integer>{

	@Override
	public Integer convert(Object obj) {			
		return Integer.parseInt((String) obj)+1;
	}
	
}
