package test.excel;

import java.util.List;

import com.tonto.common.base.annotation.ExcelColumn;
import com.tonto.common.base.annotation.SubExcelRow;

public class Address {
	@ExcelColumn(index=3,name="地址",width=20)
	String address;
	
	@SubExcelRow(District.class)
	List<District> districts;
}
