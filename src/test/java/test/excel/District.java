package test.excel;

import com.tonto.common.base.annotation.ExcelColumn;

public class District {
	
	@ExcelColumn(index=4,name="区域",width=20)
	String district;
}
